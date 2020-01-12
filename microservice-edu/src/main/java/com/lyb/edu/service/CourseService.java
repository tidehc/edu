package com.lyb.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.edu.query.CourseQuery;
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

    String saveCourseVo(CourseVo courseVo);

    CourseVo getCourseVoById(String id);

    boolean updateCourseInfo(CourseVo courseVo);

    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    void removeCourseById(String id);
}
