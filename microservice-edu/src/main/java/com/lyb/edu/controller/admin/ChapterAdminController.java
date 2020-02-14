package com.lyb.edu.controller.admin;

import com.lyb.common.vo.R;
import com.lyb.edu.entity.Chapter;
import com.lyb.edu.service.ChapterService;
import com.lyb.edu.vo.ChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author liuyoubin
 * @since 2020/1/12 - 17:22
 */
@Api(description = "课程章节管理")
@CrossOrigin
@RestController
@Validated
@RequestMapping("/admin/edu/chapter")
public class ChapterAdminController {

    private final ChapterService chapterService;

    public ChapterAdminController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @ApiOperation(value = "添加课程章节")
    @PostMapping
    public R saveChapter(
            @ApiParam(name = "chapter",value = "课程章节信息",required = true)
            @RequestBody @Valid  Chapter chapter){

        //保存章节
        chapterService.saveChapter(chapter);

        return R.ok();

    }

    @ApiOperation(value = "修改课程章节信息")
    @PutMapping("/{id}")
    public R updateChapterById(
            @ApiParam(name = "id",value = "章节ID",required = true)
            @PathVariable(value = "id") String id,
            @ApiParam(name = "chapter",value = "课程章节",required = true)
            @RequestBody Chapter chapter){

        chapter.setId(id);
        chapterService.updateById(chapter);
        return R.ok();

    }

    @ApiOperation(value = "通过ID查找课程章节")
    @GetMapping("/{id}")
    public R getChapterById(
            @ApiParam(name = "id",value = "课程章节id",required = true)
            @PathVariable(value = "id") String id){
        Chapter chapter = chapterService.getChapterById(id);
        return R.ok().data("item",chapter);
    }

    @ApiOperation(value = "通过ID删除课程章节")
    @DeleteMapping("/{id}")
    public R deleteChapterById(
            @ApiParam(name = "id",value = "课程章节id",required = true)
            @PathVariable(value = "id") String id){
        chapterService.removeChapterById(id);
        return R.ok();
    }

    @ApiOperation(value = "通过课程Id获取嵌套章节列表")
    @GetMapping("/list/{courseId}")
    public R getNestedChapterList(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        List<ChapterVo> chapterVos = chapterService.getNestedChapterListByCourseId(courseId);
        return R.ok().data("items",chapterVos);
    }
}
