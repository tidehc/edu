package com.lyb.edu.controller.admin;

import com.lyb.common.vo.R;
import com.lyb.edu.service.CourseService;
import com.lyb.edu.vo.CourseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyoubin
 * @since 2020/1/10 - 18:28
 */
@Api(description = "课程管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/course")
public class CourseAdminController {

    @Autowired
    CourseService courseService;

    @ApiOperation(value = "保存课程基本信息和描述,返回课程信息的ID")
    @PostMapping("/info")
    public R saveCourseInfo(
            @ApiParam(name = "courseVo", value = "课程基本信息", required = true)
            @RequestBody CourseVo courseVo){
        //保存课程基本信息,获取课程的ID
        String courseId = courseService.saveCourseVo(courseVo);
        return R.ok().data("id", courseId);
    }

    @ApiOperation(value = "通过课程ID获取课程基本信息和描述")
    @GetMapping("/info/{id}")
    public R getCourseInfoById(
            @ApiParam(name = "id",value = "课程ID",required = true)
            @PathVariable(value = "id") String id){

        CourseVo courseVo = courseService.getCourseVoById(id);
        return R.ok().data("courseInfo",courseVo);
    }

    @ApiOperation(value = "保存课程基本信息的修改")
    @PutMapping("/info")
    public R updateCourseInfo(
            @ApiParam(name = "courseVo", value = "课程基本信息", required = true)
            @RequestBody CourseVo courseVo){
        //保存课程基本信息的修改
        boolean flag = courseService.updateCourseInfo(courseVo);
        return flag?R.ok():R.error();
    }
}
