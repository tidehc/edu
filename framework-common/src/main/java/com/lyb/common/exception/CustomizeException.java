package com.lyb.common.exception;

import com.lyb.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuyoubin
 * @since 2019/11/9 - 16:35
 * 自定义异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "自定义异常")
public class CustomizeException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;

    public CustomizeException(Integer code,String message){
        super(message);
        this.code = code;
    }

    public CustomizeException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "CustomizeException{" +
                "code=" + code +
                "message=" + this.getMessage() +
                '}';
    }
}
