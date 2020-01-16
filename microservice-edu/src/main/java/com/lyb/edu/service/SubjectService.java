package com.lyb.edu.service;

import com.lyb.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.edu.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 读取Excel文件记录并导入数据库
     * @param file Excel文件
     * @return 导入出错提示
     * @throws Exception 异常
     */
    List<String> batchImport(MultipartFile file) throws Exception;

    /**
     * 获取嵌套的课程列表
     * @return 嵌套的课程列表
     */
    List<SubjectVo> getNestedSubjectList();

    /**
     * 根据ID删除课程分类
     * @param id 课程分类ID
     * @return 是否删除成功
     */
    boolean deleteById(String id);

    /**
     * 添加一级课程分类
     * @param subject 需要添加课程分类对象
     * @return 是否添加成功
     */
    Boolean saveLevelOneSubject(Subject subject);

    /**
     * 添加二级课程分类
     * @param subject 需要添加课程分类对象
     * @return 是否添加成功
     */
    Boolean saveLevelTwoSubject(Subject subject);
}
