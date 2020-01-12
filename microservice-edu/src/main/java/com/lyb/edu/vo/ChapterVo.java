package com.lyb.edu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyoubin
 * @since 2020/1/12 - 19:39
 */
@ApiModel(value = "课程章节Vo对象")
@Data
public class ChapterVo implements Serializable {

    @ApiModelProperty(value = "课程章节Id")
    private String id;

    @ApiModelProperty(value = "课程章节标题")
    private String title;

    @ApiModelProperty(value = "课程章节下级视频章节")
    private List<VideoVo> children = new ArrayList<>();

}
