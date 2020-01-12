package com.lyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.edu.entity.Chapter;
import com.lyb.edu.entity.Course;
import com.lyb.edu.entity.CourseDescription;
import com.lyb.edu.entity.Video;
import com.lyb.edu.mapper.CourseMapper;
import com.lyb.edu.query.CourseQuery;
import com.lyb.edu.service.ChapterService;
import com.lyb.edu.service.CourseDescriptionService;
import com.lyb.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.edu.service.VideoService;
import com.lyb.edu.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    /**
     * 保存课程基本信息
     * @param courseVo 课程信息Vo对象
     * @return 保存课程信息后数据库生成的课程ID
     */
    @Override
    @Transactional
    public String saveCourseVo(CourseVo courseVo) {

        //添加课程
        baseMapper.insert(courseVo.getCourse());
        //获取课程ID
        String id = courseVo.getCourse().getId();
        //设置课程ID、添加课程描述
        courseVo.getDescription().setId(id);
        courseDescriptionService.save(courseVo.getDescription());

        return id;
    }

    /**
     * 通过课程ID获取课程基本信息和描述
     * @param id 课程ID
     * @return 课程基本信息Vo对象
     */
    @Override
    public CourseVo getCourseVoById(String id) {

        //根据ID从Course表中查询课程基本数据
        Course course = baseMapper.selectById(id);
        if(course==null){
            throw new CustomizeException(ResultCodeEnum.DATA_NOT_EXIST);
        }

        //根据ID查询对应的课程描述信息
        CourseDescription description = courseDescriptionService.getById(id);
        if(description==null){
            description = new CourseDescription().setId(course.getId());
            courseDescriptionService.save(description);
        }

        //构造Vo对象并返回
        return new CourseVo().setCourse(course).setDescription(description);


    }

    /**
     * 保存课程基本信息的修改
     * @param courseVo 课程基本信息Vo对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateCourseInfo(CourseVo courseVo) {

        if(courseVo==null||StringUtils.isEmpty(courseVo.getCourse().getId())){
            return false;
        }

        //保存Course信息
       baseMapper.updateById(courseVo.getCourse());

        //保存描述信息
        courseDescriptionService.updateById(courseVo.getDescription());

        return true;
    }

    /**
     * 分页查询符合查询条件的课程信息
     * @param pageParam 分页对象
     * @param courseQuery 查询条件
     */
    @Override
    public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {

        //构造查询对象
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        //不存在查询条件
        if(courseQuery==null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        String status = courseQuery.getStatus();

        if(!StringUtils.isEmpty(title)){
            queryWrapper.like("title", title);
        }
        if(!StringUtils.isEmpty(teacherId)){
            queryWrapper.eq("teacher_id", teacherId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_id", subjectId);
        }

        if(!StringUtils.isEmpty(status)){
            queryWrapper.eq("status", status);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 根据课程ID删除课程
     * @param id 课程ID
     */
    @Override
    @Transactional
    public void removeCourseById(String id) {

        //根据ID删除所有视频
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoService.remove(videoQueryWrapper);

        //根据ID删除所有章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterService.remove(chapterQueryWrapper);

        //删除课程详情
        courseDescriptionService.removeById(id);

        //删除课程基本信息
        baseMapper.deleteById(id);

    }
}
