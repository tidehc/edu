package com.lyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.edu.entity.*;
import com.lyb.edu.mapper.CourseMapper;
import com.lyb.edu.query.CourseQuery;
import com.lyb.edu.service.ChapterService;
import com.lyb.edu.service.CourseDescriptionService;
import com.lyb.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.edu.service.VideoService;
import com.lyb.edu.utils.PageUtil;
import com.lyb.edu.vo.CourseDetailedVo;
import com.lyb.edu.vo.CoursePublishVo;
import com.lyb.edu.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private  CourseDescriptionService courseDescriptionService;

    @Autowired
    private  VideoService videoService;

    @Autowired
    private  ChapterService chapterService;



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


    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {

        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoById(id);

        if(coursePublishVo==null){
            throw new CustomizeException(ResultCodeEnum.COURSE_NOT_EXIST);
        }

        return coursePublishVo;
    }


    @Override
    public void publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        int i = baseMapper.updateById(course);
        if(i!=1){
            throw new CustomizeException(ResultCodeEnum.COURSE_NOT_EXIST);
        }
    }

    @Override
    public List<Course> getCourseListByTeacherId(String id) {

        //构造查询对象
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        queryWrapper.orderByDesc("gmt_modified");

        return  baseMapper.selectList(queryWrapper);

    }

    @Override
    public Map<String, Object> pageQueryWeb(Long page, Long limit) {

        //分页参数判断
        if(page<=0||limit<=0){
            throw new CustomizeException(ResultCodeEnum.PARAM_ERROR);
        }

        //构造分页查询对象
        Page<Course> pageParam = new Page<>(page,limit);

        //构造条件查询对象
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("gmt_modified");

        baseMapper.selectPage(pageParam, queryWrapper);

        return PageUtil.getPageMapOnPageParam(pageParam);

    }


    @Override
    public CourseDetailedVo getCourseDetailedVoById(String courseId) {

        //更新课程浏览数 TODO通过redis实现


        //查询获取
        return baseMapper.selectCourseDetailedVoById(courseId);
    }
}
