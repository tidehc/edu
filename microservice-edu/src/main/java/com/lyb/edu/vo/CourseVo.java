package com.lyb.edu.vo;

import com.lyb.edu.entity.Course;
import com.lyb.edu.entity.CourseDescription;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuyoubin
 * @since 2020/1/10 - 18:23
 * 课程基本信息Vo
 */
@ApiModel(value = "课程基本信息",description = "编辑课程基本信息的Vo对象")
@Data
@Accessors(chain = true)
public class CourseVo {

    @ApiModelProperty(value = "课程信息")
    private Course course;

    @ApiModelProperty(value = "课程描述")
    private CourseDescription description;
}
