package com.lyb.edu.mapper;

import com.lyb.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyb.edu.vo.CourseDetailedVo;
import com.lyb.edu.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据课程Id获取课程发布信息对象
     * @param id 课程Id
     * @return 课程发布信息Vo对象
     */
    CoursePublishVo selectCoursePublishVoById(String id);

    /**
     * 根据课程ID查询课程详情信息,包括课程信息和对应的讲师信息
     * @param courseId 课程ID
     * @return 课程详情Vo对象
     */
    CourseDetailedVo selectCourseDetailedVoById(String courseId);

}
