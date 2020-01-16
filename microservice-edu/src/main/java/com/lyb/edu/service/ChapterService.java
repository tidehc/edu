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

    /**
     *通过ID查找课程章节
     * @param id 课程章节ID
     * @return 课程章节对象
     */
    Chapter getChapterById(String id);

    /**
     * 通过ID删除课程章节
     * @param id  课程章节ID
     */
    void removeChapterById(String id);

    /**
     * 通过课程Id获取嵌套章节列表
     * @param courseId 课程Id
     * @return 嵌套章节列表
     */
    List<ChapterVo> getNestedChapterListByCourseId(String courseId);

    /**
     * 保存章节信息
     * @param chapter 章节信息
     */
    void saveChapter(Chapter chapter);
}
