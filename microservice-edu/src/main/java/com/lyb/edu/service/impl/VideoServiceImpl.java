package com.lyb.edu.service.impl;

import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.edu.entity.Chapter;
import com.lyb.edu.entity.Course;
import com.lyb.edu.entity.Video;
import com.lyb.edu.mapper.VideoMapper;
import com.lyb.edu.service.ChapterService;
import com.lyb.edu.service.CourseService;
import com.lyb.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;


    @Override
    public void saveVideo(Video video) {
        //获取课程ID
        String courseId = video.getCourseId();
        //判断课程是否存在
        Course course = courseService.getById(courseId);
        if(course==null){
            throw new CustomizeException(ResultCodeEnum.COURSE_NOT_EXIST);
        }

        //获取章节ID
        String chapterId = video.getChapterId();
        //判断章节是否存在
        Chapter chapter = chapterService.getChapterById(chapterId);
        if(chapter==null){
            throw new CustomizeException(ResultCodeEnum.CHAPTER_NOT_EXIST);
        }
        //保存
        baseMapper.insert(video);

    }


    @Override
    public Video getVideoById(String id) {

        Video video = baseMapper.selectById(id);
        if(video==null){
            throw new CustomizeException(ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        return video;
    }


    @Override
    public void updateVideo(Video video) {
        int i = baseMapper.updateById(video);
        if(i!=1){
            throw new CustomizeException(ResultCodeEnum.UNKNOWN_REASON);
        }
    }


    @Override
    public void deleteVideoById(String id) {
        //删除视频资源 TODO

        //删除课时信息
        int i = baseMapper.deleteById(id);
        if(i!=1){
            throw new CustomizeException(ResultCodeEnum.VIDEO_NOT_EXIST);
        }
    }
}
