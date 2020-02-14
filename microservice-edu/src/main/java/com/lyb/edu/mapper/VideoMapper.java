package com.lyb.edu.mapper;

import com.lyb.edu.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
public interface VideoMapper extends BaseMapper<Video> {

    /**
     * 根据ChapterId查询Video数据的VideoSourceId
     * @param chapter_id  ChapterId
     * @return VideoSourceId集合
     */
    List<String> selectVideoSourceIdByChapterId(String chapter_id);
}
