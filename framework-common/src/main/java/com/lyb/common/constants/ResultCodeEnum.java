package com.lyb.common.constants;

import lombok.Getter;

/**
 * @author liuyoubin
 * @since 2019/11/9 - 11:43
 * 定义统一返回结果枚举
 */
@Getter
public enum ResultCodeEnum {


    SUCCESS(true, 20000,"成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),
    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAM_ERROR(false, 21003, "参数不正确"),
    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    EXCEL_DATA_IMPORT_ERROR(false, 21005, "Excel数据导入错误"),
    DATA_NOT_EXIST(false,21006,"数据不存在"),
    DATA_NOT_COMPLETE(false,21007,"数据不完整");


    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
