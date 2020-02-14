package com.lyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.edu.entity.Chapter;
import com.lyb.edu.entity.Course;
import com.lyb.edu.entity.Video;
import com.lyb.edu.mapper.ChapterMapper;
import com.lyb.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.edu.service.CourseService;
import com.lyb.edu.service.VideoService;
import com.lyb.edu.vo.ChapterVo;
import com.lyb.edu.vo.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private  CourseService courseService;

    @Autowired
    private  VideoService videoService;


    @Override
    public Chapter getChapterById(String id) {

        Chapter chapter = baseMapper.selectById(id);

        if(chapter==null){
            throw new CustomizeException(ResultCodeEnum.DATA_NOT_EXIST);
        }

        return chapter;
    }

    @Transactional
    @Override
    public void removeChapterById(String id) {

        //删除章节
        int i = baseMapper.deleteById(id);
        if(i!=1){
            throw new CustomizeException(ResultCodeEnum.DATA_NOT_EXIST);
        }

        //根据章节Id删除所有课时以及视频
        videoService.removeVideoByChapterId(id);

    }


    @Override
    public List<ChapterVo> getNestedChapterListByCourseId(String courseId) {

        //最终要返回的嵌套列表对象
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        //获取章节信息
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterQueryWrapper.orderByAsc("sort", "id");
        List<Chapter> chapters = baseMapper.selectList(chapterQueryWrapper);

        if(chapters==null){
            throw new CustomizeException(ResultCodeEnum.DATA_NOT_EXIST);
        }

        //获取视频课时信息
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.orderByAsc("sort","id");
        List<Video> videos = videoService.list(videoQueryWrapper);

        //构建章节Vo对象
        for (Chapter chapter : chapters) {

            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVos.add(chapterVo);

            //构建视频课时Vo对象
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (Video video : videos) {
                if(chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        }

        return chapterVos;
    }


    @Override
    public void saveChapter(Chapter chapter) {

        //获取章节对应的课程Id
        String courseId = chapter.getCourseId();
        //判断课程是否存在
        Course course = courseService.getById(courseId);
        if(course==null){
            throw new CustomizeException(ResultCodeEnum.COURSE_NOT_EXIST);
        }

        //保存章节信息
        baseMapper.insert(chapter);
    }
}
