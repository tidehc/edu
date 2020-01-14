package com.lyb.common.util;

import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.vo.R;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyoubin
 * @since 2020/1/14 - 21:52
 * 参数校验处理类
 */
public class ValidUtil {

    /**
     * 处理验证错误信息
     * @param bindingResult 错误绑定结果对象
     * @param resultCodeEnum 结果枚举
     * @return 通用消息返回对象
     */
    public static R handleValidateErrorMessage(BindingResult bindingResult, ResultCodeEnum resultCodeEnum){
        //错误消息集合
        Map<String,Object> errorMessages = new HashMap<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMessages.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.setResult(resultCodeEnum).data("errorMessages",errorMessages);
    }

    public static R handleValidateErrorMessage(BindingResult bindingResult){
        return handleValidateErrorMessage(bindingResult, ResultCodeEnum.UNKNOWN_REASON);
    }
}
