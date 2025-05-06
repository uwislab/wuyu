package com.fiveup.core.management.common.enums;

import com.fiveup.core.management.common.exception.ErrorCode;

/**
 * @author 尔宣赫
 * @date 2021/8/31
 */
public enum BackendErrorCodeEnum implements ErrorCode {


    /**
     * 成功
     */
    SUCCESS(0, "服务运行成功"),
    /**
     * 未指明的异常
     */
    UNSPECIFIED(500, "网络异常，请稍后再试"),
    PARAMS_VALIDATION_ERRNO(50001, "参数校验异常"),
    SYSTEM_ERRNO(50002, "系统内部错误"),
    DATA_EMPTY_ERRNO(50017, "数据为空"),
    DATA_EXIST(50009, "数据已存在"),


    LOGIN_FAIL(80000,"用户名或密码错误"),
    CAPTCHA_ERROR(80001,"验证码错误"),
    USER_EXISTS(80002,"用户名已存在"),
    USER_NOT_EXISTS(80003,"用户名不存在"),

    SIGN_UP_FAIL(90000,"注册失败"),


    EMAIL_NOT_MATCHED(100000,"用户输入邮箱不匹配");
    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 构造器
     *
     * @param code        码值
     * @param description 描述
     */
    BackendErrorCodeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
