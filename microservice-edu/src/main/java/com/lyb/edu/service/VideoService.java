package com.lyb.edu.service;

import com.lyb.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
public interface VideoService extends IService<Video> {

    void saveVideo(Video video);

    Video getVideoById(String id);

    void updateVideo(Video video);

    void deleteVideoById(String id);
}
