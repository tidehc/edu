package com.lyb.common.handler;

import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.common.vo.R;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liuyoubin
 * @since 2019/11/9 - 14:44
 * 全局统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error();
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(CustomizeException.class)
    @ResponseBody
    public R error(CustomizeException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }

    /**
     * 特定异常处理
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){
        e.printStackTrace();
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(JsonParseException e){
        e.printStackTrace();
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);

    }

}
