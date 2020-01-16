package com.lyb.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.edu.query.CourseQuery;
import com.lyb.edu.vo.CoursePublishVo;
import com.lyb.edu.vo.CourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程基本信息
     * @param courseVo 课程信息Vo对象
     * @return 保存课程信息后数据库生成的课程ID
     */
    String saveCourseVo(CourseVo courseVo);

    /**
     * 通过课程ID获取课程基本信息和描述
     * @param id 课程ID
     * @return 课程基本信息Vo对象
     */
    CourseVo getCourseVoById(String id);

    /**
     * 保存课程基本信息的修改
     * @param courseVo 课程基本信息Vo对象
     * @return 是否修改成功
     */
    boolean updateCourseInfo(CourseVo courseVo);

    /**
     * 分页查询符合查询条件的课程信息
     * @param pageParam 分页对象
     * @param courseQuery 查询条件
     */
    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    /**
     * 根据课程ID删除课程
     * @param id 课程ID
     */
    void removeCourseById(String id);

    /**
     * 根据课程Id获取课程发布信息对象
     * @param id 课程Id
     * @return 课程发布信息Vo对象
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据课程Id发布课程
     * @param id 课程Id
     */
    void publishCourseById(String id);
}
