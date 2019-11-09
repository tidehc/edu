package com.lyb.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.edu.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
public interface TeacherService extends IService<Teacher> {

    void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);

}
