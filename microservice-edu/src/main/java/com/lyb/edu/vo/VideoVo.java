package com.lyb.edu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyoubin
 * @since 2020/1/12 - 19:36
 */
@ApiModel(value = "视频课时Vo对象")
@Data
public class VideoVo implements Serializable {

    @ApiModelProperty(value = "视频课时Id")
    private String id;

    @ApiModelProperty(value = "视频课时标题")
    private String title;

    @ApiModelProperty(value = "视频文件名称")
    private String videoOriginalName;

    @ApiModelProperty(value = "是否免费")
    private Boolean free;
}
