package com.lyb.edu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author liuyoubin
 * @since 2020/1/9 - 10:54
 * 二级课程
 */
@ApiModel(value = "二级课程分类",description = "二级课程分类的Vo对象")
@Data
public class SubjectSecondLevelVo {

    private String id;

    private String title;
}
