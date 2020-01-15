package com.lyb.edu.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyoubin
 * @since 2020/1/15 - 15:17
 */
@Api("课程发布信息Vo对象")
@Data
public class CoursePublishVo implements Serializable {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面")
    private String cover;

    @ApiModelProperty(value = "课程总课时")
    private String lessonNum;

    @ApiModelProperty(value = "课程一级分类")
    private String subjectLevelOne;

    @ApiModelProperty(value = "课程二级分类")
    private String subjectLevelTwo;

    @ApiModelProperty(value = "课程讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "课程价格")
    private String price;

}
