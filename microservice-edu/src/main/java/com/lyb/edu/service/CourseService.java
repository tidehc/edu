package com.lyb.edu.service;

import com.lyb.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
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

}
