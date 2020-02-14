package com.lyb.common.handler;

import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.common.util.ExceptionUtil;
import com.lyb.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyoubin
 * @since 2019/11/9 - 14:44
 * 全局统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        //e.printStackTrace();
        //log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return R.error();
    }

    /**
     * 参数绑定校验异常处理
     * @param e 异常对象
     * @return 统一返回信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R error(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        //错误消息集合
        //Map<String,Object> errorMessages = new HashMap<>();
        List<String> errorMessages = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMessages.add(fieldError.getDefaultMessage());
        });
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.PARAM_ERROR).data("errorMessages",errorMessages);
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(CustomizeException.class)
    @ResponseBody
    public R error(CustomizeException e){

        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }

    /**
     * 特定异常处理
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){

        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(JsonParseException e){

        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);

    }

}
