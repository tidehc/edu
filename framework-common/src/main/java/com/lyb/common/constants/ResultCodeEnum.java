package com.lyb.common.constants;

import lombok.Getter;

/**
 * @author liuyoubin
 * @since 2019/11/9 - 11:43
 * 定义统一返回结果枚举
 */
@Getter
public enum ResultCodeEnum {

    /**
     * 20000 成功
     * 20001 未知错误
     * 21*** 通用错误
     * 31*** 课程相关错误
     * 41*** 章节相关错误
     * 51*** 课时相关错误
     */
    SUCCESS(true, 20000,"成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),
    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAM_ERROR(false, 21003, "参数不正确"),
    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    EXCEL_DATA_IMPORT_ERROR(false, 21005, "Excel数据导入错误"),
    DATA_NOT_EXIST(false,21006,"数据不存在"),
    DATA_NOT_COMPLETE(false,21007,"数据不完整"),
    URL_ENCODE_ERROR(false,21008,"url编码错误"),
    ILLEGAL_CALLBACK_REQUEST_ERROR(false,21009,"非法回调请求错误"),
    FETCH_ACCESSTOKEN_FAILD(false,21010,"获取AccessToken失败"),
    FETCH_USER_INFO_FAILD(false,21010,"获取用户信息失败失败"),
    COURSE_NOT_EXIST(false,31001,"课程不存在"),
    CHAPTER_NOT_EXIST(false,41001,"章节不存在"),
    VIDEO_NOT_EXIST(false,51001,"课时不存在");


    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
