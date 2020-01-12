package com.lyb.edu.service;

import com.lyb.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.edu.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
public interface ChapterService extends IService<Chapter> {

    Chapter getChapterById(String id);

    void removeChapterById(String id);

    List<ChapterVo> getNestedChapterListByCourseId(String courseId);
}
