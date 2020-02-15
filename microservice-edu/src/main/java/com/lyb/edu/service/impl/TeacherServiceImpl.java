package com.lyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.edu.entity.Teacher;
import com.lyb.edu.mapper.TeacherMapper;
import com.lyb.edu.query.TeacherQuery;
import com.lyb.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.edu.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Override
    public Page<Teacher> pageQuery(Long page, Long limit, TeacherQuery teacherQuery) {

        //分页参数判断
        if(page<=0||limit<=0){
            throw new CustomizeException(ResultCodeEnum.PARAM_ERROR);
        }
        //构造分页查询对象
        Page<Teacher> pageParam = new Page<>(page,limit);

        //构造条件查询对象
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if(teacherQuery==null){//如果没有查询条件则直接查询返回
            baseMapper.selectPage(pageParam, queryWrapper);
            return pageParam;
        }

        //获取查询条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();


        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level) ) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);

        return pageParam;
    }


    @Override
    public Map<String, Object> pageQueryWeb(Long page, Long limit) {

        //分页参数判断
        if(page<=0||limit<=0){
            throw new CustomizeException(ResultCodeEnum.PARAM_ERROR);
        }

        //构造分页查询对象
        Page<Teacher> pageParam = new Page<>(page,limit);

        //构造条件查询对象
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("gmt_modified");

        //查询
        baseMapper.selectPage(pageParam, queryWrapper);

        return PageUtil.getPageMapOnPageParam(pageParam);
    }
}
