package com.lyb.edu.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyoubin
 * @since 2019/11/9 - 13:33
 */
@Data
@ApiModel(value = "Teacher查询对象",description = "讲师查询对象的封装")
public class TeacherQuery implements Serializable {

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "讲师头衔级别 1-高级讲师，2-首席讲师")
    private  Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2019-10-10 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2019-12-12 10:10:10")
    private  String end;


}
