package com.lyb.edu.controller.admin;

import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.common.vo.R;
import com.lyb.edu.service.SubjectService;
import com.lyb.edu.vo.SubjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author liuyoubin
 * @since 2019/12/5 - 23:14
 */
@Api(value = "课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/subject")
@Slf4j
public class SubjectAdminController {

    @Autowired
    SubjectService subjectService;

    @ApiOperation(value = "Excel批量导入课程")
    @PostMapping("/import")
    public R batchImport(
            @ApiParam(name = "file",value = "Excel文件",required = true)
            @RequestParam("file") MultipartFile file){

        try {

            List<String> errorMgs = subjectService.batchImport(file);
            if(errorMgs.size()==0){
                return R.ok().message("批量导入成功");
            }else{
                return R.error().message("部分数据导入失败").data("errorMsgList",errorMgs);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomizeException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }

    }

    @ApiOperation(value = "获取嵌套的课程列表")
    @GetMapping("/list")
    public R getNestedSubjectList(){
        //获取嵌套的课程列表
        List<SubjectVo> subjectVoList = subjectService.getNestedSubjectList();
        return R.ok().data("items",subjectVoList);
    }

    @ApiOperation(value = "通过课程分类id删除课程分类")
    @DeleteMapping("/{id}")
    public R deleteSubjectById(
            @ApiParam(name = "id",value = "课程分类id",required = true)
            @PathVariable("id") String id){
        boolean b = subjectService.deleteById(id);
        return b?R.ok():R.error();
    }
}
