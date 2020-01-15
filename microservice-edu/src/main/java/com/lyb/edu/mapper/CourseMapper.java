package com.lyb.edu.mapper;

import com.lyb.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
