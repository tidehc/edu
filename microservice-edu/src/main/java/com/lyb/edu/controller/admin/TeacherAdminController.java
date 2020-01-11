package com.lyb.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.edu.entity.Teacher;
import com.lyb.edu.query.TeacherQuery;
import com.lyb.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.lyb.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuyoubin
 * @since 2019/11/8 - 21:51
 */
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
@Api(description = "讲师管理")
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有教师
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public R list(){
        List<Teacher> list = teacherService.list(null);
        return  R.ok().data("items",list);
    }


    @ApiOperation(value = "通过id删除讲师")
    @DeleteMapping("/{id}")
    public R removeById(
            @ApiParam(name="id",value = "讲师id",required = true)
            @PathVariable(value = "id") String id){
        teacherService.removeById(id);
        return R.ok();
    }


    @ApiOperation(value = "可以带条件查询的分页讲师列表")
    @GetMapping("/{page}/{limit}")
    public R pageQueryTeacherList(
            @ApiParam(name="page",value = "当前页码",required = true)
            @PathVariable(value = "page") Long page,
            @ApiParam(name="limit",value = "每页记录数",required = true)
            @PathVariable(value = "limit") Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象",required = false) TeacherQuery teacherQuery){

        if(page<=0||limit<=0){
            throw new CustomizeException(ResultCodeEnum.PARAM_ERROR);
        }

        Page<Teacher> pageParam = new Page<>(page,limit);
        teacherService.pageQuery(pageParam, teacherQuery);
        //分页列表
        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(
            @ApiParam(name="teacher",value = "讲师信息",required = true)
            @RequestBody Teacher teacher){

        teacherService.save(teacher);
        return R.ok();

    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("/{id}")
    public R getById(
            @ApiParam(name="id",value = "讲师id",required = true)
            @PathVariable(value = "id") String id){

        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);

    }

    @ApiOperation(value = "修改讲师信息")
    @PutMapping("/{id}")
    public R updateById(
        @ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id,
        @ApiParam(name = "teacher", value = "讲师对象", required = true)
        @RequestBody Teacher teacher){

            teacher.setId(id);
            teacherService.updateById(teacher);
            return R.ok();
    }
}
