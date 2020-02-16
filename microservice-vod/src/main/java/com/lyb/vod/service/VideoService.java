package com.lyb.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author liuyoubin
 * @since 2020/1/28 - 17:17
 */
public interface VideoService {
    /**
     * 上传视频到阿里云,获得视频的vid
     * @param file 视频文件
     * @return 视频文件的vid
     */
    String uploadVideo(MultipartFile file);

    /**
     * 根据视频Id删除阿里云视频
     * @param videoId 视频Id
     */
    void removeVideoByVideoId(String videoId);

    /**
     *  根据视频Id集合批量删除阿里云视频
     * @param videoIds 视频Id集合
     */
    void batchRemoveVideoByVideoIds(List<String> videoIdList);

    /**
     *  根据视频Id获取播放凭证
     * @param videoId 视频Id
     * @return 播放凭证
     */
    String getVideoPlayAuth(String videoId);

}
