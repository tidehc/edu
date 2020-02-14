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

    /**
     * 保存视频课时信息
     * @param video 视频课时信息对象
     */
    void saveVideo(Video video);

    /**
     * 根据视频课时Id获取课时信息
     * @param id 视频课时Id
     * @return 课时信息
     */
    Video getVideoById(String id);

    /**
     * 修改视频课信息
     * @param video 视频课信息
     */
    void updateVideo(Video video);

    /**
     * 根据Id删除视频课时信息
     * @param id 课时Id
     */
    void deleteVideoById(String id);

    /**
     * 根据chapter_id删除课时信息、云端视频
     * @param chapter_id 课时的chapter_id
     */
    void removeVideoByChapterId(String chapter_id);
}
