package com.lyb.edu.controller;


import com.lyb.common.vo.R;
import com.lyb.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
@Api(description = "前台课程模块")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "分页查询课程列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageQueryCourseList(
            @ApiParam(name="page",value = "当前页码",required = true)
            @PathVariable(value = "page") Long page,
            @ApiParam(name="limit",value = "每页记录数",required = true)
            @PathVariable(value = "limit") Long limit){

        Map<String, Object> resultMap = courseService.pageQueryWeb(page,limit);

        return R.ok().data(resultMap);

    }

}

