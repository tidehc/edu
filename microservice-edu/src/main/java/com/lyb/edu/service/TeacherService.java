package com.lyb.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.edu.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 分页查询符合条件的讲师列表
     * @param page 第几页
     * @param limit 每页几条数据
     * @param teacherQuery 查询条件对象
     * @return 查询结构封装在Page对象中
     */
    Page<Teacher> pageQuery(Long page, Long limit, TeacherQuery teacherQuery);

    /**
     * 分页查询前台讲师列表
     * @param page 第几页
     * @param limit 每页几条数据
     * @return 封装的查询数据
     */
    Map<String, Object> pageQueryWeb(Long page, Long limit);
}
