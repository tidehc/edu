package com.lyb.edu.controller;


import com.lyb.common.vo.R;
import com.lyb.edu.service.ChapterService;
import com.lyb.edu.service.CourseService;
import com.lyb.edu.vo.ChapterVo;
import com.lyb.edu.vo.CourseDetailedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "分页查询课程列表")
    @GetMapping(value = "/{page}/{limit}")
    public R pageQueryCourseList(
            @ApiParam(name="page",value = "当前页码",required = true)
            @PathVariable(value = "page") Long page,
            @ApiParam(name="limit",value = "每页记录数",required = true)
            @PathVariable(value = "limit") Long limit){

        Map<String, Object> resultMap = courseService.pageQueryWeb(page,limit);

        return R.ok().data(resultMap);

    }

    @ApiOperation(value = "通过课程Id查询课程详情信息")
    @GetMapping(value = "/{courseId}")
    public R getCourseDetailedById(
            @ApiParam(name = "courseId",value = "课程Id",required = true)
            @PathVariable String courseId){

        //查询课程详情信息,包括课程信息和对应的讲师信息
        CourseDetailedVo courseDetailedVo =  courseService.getCourseDetailedVoById(courseId);

        //获取课程的章节列表(包括章节下的课时信息)
        List<ChapterVo> chapterVoList = chapterService.getNestedChapterListByCourseId(courseId);

        return R.ok()
                .data("courseDetailed", courseDetailedVo)
                .data("chapterVoList", chapterVoList);
    }
}

