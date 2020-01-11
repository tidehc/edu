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

    @ApiOperation(value = "添加课程，保存基本信息")
    @PostMapping("/info")
    public R saveCourse(
            @ApiParam(name = "courseVo", value = "课程基本信息", required = true)
            @RequestBody CourseVo courseVo){
        //保存课程基本信息,获取课程的ID
        String courseId = courseService.saveCourseVo(courseVo);
        return R.ok().data("id", courseId);
    }

}
