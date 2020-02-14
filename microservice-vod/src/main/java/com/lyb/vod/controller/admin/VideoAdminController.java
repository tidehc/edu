package com.lyb.vod.controller.admin;

import com.lyb.common.vo.R;
import com.lyb.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author liuyoubin
 * @since 2020/1/28 - 17:09
 */
@Api(description = "阿里云视频点播接口")
@CrossOrigin
@RestController
@RequestMapping("/admin/vod/video")
public class VideoAdminController {

    private final VideoService videoService;

    public VideoAdminController(VideoService videoService) {
        this.videoService = videoService;
    }


    @PostMapping
    @ApiOperation(value = "上传视频到阿里云")
    public R uploadVideo(
            @ApiParam(name = "file",value = "上传视频文件",required = true)
            @RequestParam(value = "file") MultipartFile file){

        String videoId = videoService.uploadVideo(file);

        return R.ok().message("视频上传成功").data("videoId",videoId);
    }


    @ApiOperation(value = "根据VideoId删除阿里云视频")
    @DeleteMapping("/{videoId}")
    public R removeVideoById(
            @ApiParam(value = "videoId",name = "视频Id" ,required = true)
            @PathVariable(value = "videoId") String videoId){

        videoService.removeVideoByVideoId(videoId);

        return R.ok().message("视频删除成功");

    }

    @ApiOperation(value = "根据VideoId批量删除阿里云视频")
    @DeleteMapping("/batch")
    public R batchRemoveVideoByIds(
            @ApiParam(value = "videoIds",name = "视频ID集合",required = true)
            @RequestParam List<String> videoIdList){

        videoService.batchRemoveVideoByVideoIds(videoIdList);

        return R.ok().message("视频批量删除成功");
    }

}
