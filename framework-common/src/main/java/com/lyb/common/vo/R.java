package com.lyb.common.vo;

/**
 * @author liuyoubin
 * @since 2019/11/9 - 12:16
 * 全局统一返回结果
 */

import com.lyb.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "全局统一返回结果")
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    /**
     * 私有化构造器
     */
    private R(){

    }

    public static R ok(){
        R result = new R();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }

    public static R error(){
        R result = new R();
        result.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        result.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        result.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return result;
    }

    public static R setResult(ResultCodeEnum resultCodeEnum){
        R result = new R();
        result.setCode(resultCodeEnum.getCode());
        result.setSuccess(resultCodeEnum.getSuccess());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R success(boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

}
