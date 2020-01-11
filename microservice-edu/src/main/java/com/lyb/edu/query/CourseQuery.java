package com.lyb.edu.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyoubin
 * @since 2020/1/11 - 22:04
 * 课程查询条件封装对象
 */
@Data
@ApiModel(value = "Course查询对象",description = "封装了课程查询条件")
public class CourseQuery implements Serializable {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "一级分类ID")
    private String subjectParentId;

    @ApiModelProperty(value = "二级分类ID")
    private String subjectId;

}
