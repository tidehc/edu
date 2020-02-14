package com.lyb.edu.controller.admin;

import com.lyb.common.vo.R;
import com.lyb.edu.entity.Video;
import com.lyb.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyoubin
 * @since 2020/1/14 - 23:33
 */
@Api(description = "视频课时管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/video")
public class VideoAdminController {

    private final VideoService videoService;

    public VideoAdminController(VideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation(value = "保存课时信息")
    @PostMapping
    public R saveVideo(
            @ApiParam(name = "video",value = "视频课时信息",required = true)
            @RequestBody Video video){

        videoService.saveVideo(video);

        return R.ok();

    }

    @ApiOperation(value = "根据视频课时Id获取课时信息")
    @GetMapping("/{id}")
    public R getVideoById(
            @ApiParam(name = "id",value = "视频课时ID",required = true)
            @PathVariable(value = "id") String id){

       Video video =  videoService.getVideoById(id);

       return R.ok().data("item",video);
    }

    @ApiOperation("根据Id修改视频课时信息")
    @PutMapping("/{id}")
    public R updateVideoById(
            @ApiParam(name = "video",value = "视频课时信息",required = true)
            @PathVariable(value = "id") String id,
            @ApiParam(name = "video",value = "视频课时信息",required = true)
            @RequestBody Video video){
        video.setId(id);
        videoService.updateVideo(video);
        return R.ok();
    }

    @ApiOperation("根据Id删除视频课时信息")
    @DeleteMapping("/{id}")
    public R deleteVideoById(
            @ApiParam(name = "video",value = "视频课时信息",required = true)
            @PathVariable(value = "id") String id){
        videoService.deleteVideoById(id);
        return R.ok();
    }

}
