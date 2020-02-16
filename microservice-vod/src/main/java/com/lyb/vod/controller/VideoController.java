package com.lyb.vod.controller;

import com.lyb.common.vo.R;
import com.lyb.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyoubin
 * @since 2020/1/29 - 19:40
 */
@Api(description = "阿里云视频点播前台管理接口")
@CrossOrigin
@RestController
@RequestMapping("/vod/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "根据视频Id获取播放凭证")
    @GetMapping("/playAuth/{videoId}")
    public R getVideoPlayAuth(
            @ApiParam(name = "videoId",value = "视频Id",required = true)
            @PathVariable(value = "videoId") String videoId){

        //获取播放凭证
        String playAuth = videoService.getVideoPlayAuth(videoId);

        return R.ok().data("playAuth", playAuth);
    }

}
