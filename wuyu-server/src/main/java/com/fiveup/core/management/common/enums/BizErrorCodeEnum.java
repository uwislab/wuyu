package com.fiveup.core.management.common.enums;

import com.fiveup.core.management.common.exception.ErrorCode;

/**
 * @author 尔宣赫
 * @date 29/11/2021
 */
public enum BizErrorCodeEnum implements ErrorCode {


    /**
     * 成功
     */
    SUCCESS(200, "服务运行成功"),
    /**
     * 未指明的异常
     */
    UNSPECIFIED(500, "网络异常，请稍后再试"),
    DATA_EXIST(50000, "数据已存在"),
    PARAMS_VALIDATION_ERRNO(50001, "参数校验异常"),
    SYSTEM_ERRNO(50002, "系统内部错误"),
    PLATFORM_PLAN_DETAIL_ERRNO(50003, "平台预案详情不存在"),
    CREATE_PLAN_ERRNO(50004, "创建或者更新预案失败"),
    PUBLISH_PLAN_ERRNO(50005, "发布预案失败"),
    PLAN_TEMPLATE_ERRNO(50006, "预案模板不存在"),
    PLAN_TEMPLATE_PUBLISH_ERRNO(50007, "预案模板发布失败"),


    NOT_CONFIG_PLAN_TEMPLATE_ERRNO(50008, "未配置告警ID对应的模板信息"),
    NOT_PLAN_DETAL_CONFIG_ERRNO(50009, "未配置告警ID对应的预案信息"),
    PLATFORM_PLAN_DETAIL_CONFIG_ERRNO(50010, "平台预案详情配置错误"),
    NO_ABILITY_ERRNO(50011, "暂无此能力"),
    COMMAND_FORMAT_ERRNO(50013, "命令格式错误"),
    NOT_PLAN_ERRNO(50015, "预案不存在"),
    NOT_PRO_PLAN_ERRNO(50016, "正式环境中没有预案"),
    DATA_EMPTY_ERRNO(50017, "数据为空"),
    NOT_PUBLISHED_PLAN_ERRNO(50018, "没有发布预案"),
    ROLLBACK_PLAN_ERRNO(50019, "回滚预案失败"),

    ALARM_RECORD_NOT_EXIST(50020, "报警记录不存在");
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
    BizErrorCodeEnum(Integer code, String description) {
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
