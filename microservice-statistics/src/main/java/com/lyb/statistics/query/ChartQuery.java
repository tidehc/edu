package com.lyb.statistics.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liuyoubin
 * @since 2020/1/17 - 18:17
 */
@Data
@ApiModel("统计图表查询对象")
public class ChartQuery {

    @ApiModelProperty(value = "查询数据类型")
    @NotNull
    private String type;

    @ApiModelProperty(value = "查询起始时间")
    @NotNull
    private String begin;

    @ApiModelProperty(value = "查询终止时间")
    @NotNull
    private String end;

}
