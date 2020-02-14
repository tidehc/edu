package com.lyb.edu.client;

import com.lyb.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author liuyoubin
 * @since 2020/2/5 - 22:00
 * 远程调用Vod微服务模块接口
 */
@Component
@FeignClient("vod")
public interface VodClient {

    /**
     *
     * 根据VideoId删除阿里云视频
     * @param videoId 视频Id
     */
    @DeleteMapping(value = "/admin/vod/video/{videoId}")
    R removeVideoById(@PathVariable(value = "videoId") String videoId);

    /**
     * 根据VideoId批量删除阿里云视频
     */
    @DeleteMapping(value = "/admin/vod/video/batch")
    R batchRemoveVideoByIds(@RequestParam List<String> videoIdList);
}
