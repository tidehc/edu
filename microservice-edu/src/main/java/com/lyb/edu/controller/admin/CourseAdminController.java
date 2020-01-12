package com.lyb.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.common.vo.R;
import com.lyb.edu.entity.Course;
import com.lyb.edu.query.CourseQuery;
import com.lyb.edu.service.CourseService;
import com.lyb.edu.vo.CourseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "可以带查询条件分页查询课程列表")
    @GetMapping("/{page}/{limit}")
    public R pageQueryCourseList(
            @ApiParam(name="page",value = "当前页码",required = true)
            @PathVariable(value = "page") Long page,
            @ApiParam(name="limit",value = "每页记录数",required = true)
            @PathVariable(value = "limit") Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象",required = false) CourseQuery courseQuery){

        if(page<=0||limit<=0){
            throw new CustomizeException(ResultCodeEnum.PARAM_ERROR);
        }
        //构造分类参数对象
        Page<Course> pageParam = new Page<>(page, limit);
        //查询
        courseService.pageQuery(pageParam,courseQuery);
        //获取查询结果列表
        List<Course> records = pageParam.getRecords();
        //获取结果总数据数
        long total = pageParam.getTotal();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "根据课程ID删除课程")
    @DeleteMapping("/{id}")
    public R deleteCourseByID(
            @ApiParam(name = "id",value = "课程ID",required = true)
            @PathVariable(value = "id")String id){
        courseService.removeCourseById(id);
        return R.ok();
    }
}
