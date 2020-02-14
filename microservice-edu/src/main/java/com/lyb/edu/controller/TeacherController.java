package com.lyb.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.common.vo.R;
import com.lyb.edu.entity.Teacher;
import com.lyb.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
@Api(description = "前台讲师模块")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/{page}/{limit}")
    @ApiOperation(value = "分页查询讲师列表")
    public R pageQueryTeacherList(
            @ApiParam(name="page",value = "当前页码",required = true)
            @PathVariable(value = "page") Long page,
            @ApiParam(name="limit",value = "每页记录数",required = true)
            @PathVariable(value = "limit") Long limit){

        Map<String, Object> resultMap = teacherService.pageQueryWeb(page,limit);

        return R.ok().data(resultMap);
    }

}

