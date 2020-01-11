package com.lyb.aliyunoss.controller;

import com.lyb.aliyunoss.service.FileService;
import com.lyb.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liuyoubin
 * @since 2019/11/20 - 12:00
 */
@CrossOrigin
@RestController
@Api(description = "阿里云文件管理")
@RequestMapping("/admin/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;


    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public R uploadFile(
            @ApiParam(name = "file",value = "文件",required = true)
            @RequestParam(value = "file")  MultipartFile file,
            @ApiParam(name = "host",value = "文件上传路劲",required = false)
            @RequestParam(value = "host",required = false)String host){

        //上传文件，获取url
        String uploadUrl = fileService.uploadFile(file,host);

        return R.ok().message("文件上传成功").data("url",uploadUrl);
    }
}
