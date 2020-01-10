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

    List<String> batchImport(MultipartFile file) throws Exception;

    List<SubjectVo> getNestedSubjectList();

    boolean deleteById(String id);

    Boolean saveLevelOneSubject(Subject subject);

    Boolean saveLevelTwoSubject(Subject subject);
}
