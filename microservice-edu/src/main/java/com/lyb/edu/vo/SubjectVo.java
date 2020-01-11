package com.lyb.edu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyoubin
 * @since 2020/1/7 - 23:40
 * 一级课程
 */
@ApiModel(value = "一级课程分类",description = "一级课程分类的Vo对象")
@Data
public class SubjectVo {

    private String id;

    private String title;

    private List<SubjectSecondLevelVo> children = new ArrayList<>();

}
