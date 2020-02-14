package com.lyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.edu.client.VodClient;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
    private  VodClient vodClient;

    @Autowired
    private  CourseService courseService;

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

    @Transactional
    @Override
    public void deleteVideoById(String id) {

        //根据课时Id查询对应视频的VideoId
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();

        //调用VodClient的接口删除阿里云视频
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeVideoById(videoSourceId);
        }

        //删除课时信息
        int i = baseMapper.deleteById(id);
        if(i!=1){
            throw new CustomizeException(ResultCodeEnum.VIDEO_NOT_EXIST);
        }
    }

    @Override
    public void removeVideoByChapterId(String chapter_id) {

        //查找符合ChapterId的所有课时的Video_source_id
        List<String> videoSourceIdList =  baseMapper.selectVideoSourceIdByChapterId(chapter_id);

        //删除课时信息
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapter_id);
        baseMapper.delete(queryWrapper);

        //远程调用vod微服务接口批量删除云端视频
        if(videoSourceIdList.size()>0){
            vodClient.batchRemoveVideoByIds(videoSourceIdList);
        }
    }
}
