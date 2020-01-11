package com.lyb.edu.service.impl;

import com.lyb.edu.entity.Course;
import com.lyb.edu.mapper.CourseMapper;
import com.lyb.edu.service.CourseDescriptionService;
import com.lyb.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.edu.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
