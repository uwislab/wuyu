package com.fiveup.core.common.result;


/**
 * @Author 
 * @Date 
 * @Version 1.0
 * 返回代码
 **/
public enum BaseResultStatusEnum {
    SUCCESS(200, "成功"),
    FAIL(01, "失败"),
    SYSTEM_ERROR(500, "系统异常，请稍后再试。"),
    DB_ERROR(501, "db error!"),
    SCREEN_USER_NOT_AUTHORIZATION(401, "user not login!"),
    LOGIN_ERROR(401, "账号或者密码错误"),
    SCREEN_PARAM_NULL(400, "参数缺失"),
    PARAM_ERROR(402, "参数错误"),
    UPLOAD_ATTACH_FAIL(601, "上传附件失败"),
    RESPONSE_IS_NULL(227, "response is null"),
    EXPORT_DATA_IS_EMPTY(455, "导出数据为空"),
    USER_NOT_EXISTED(457, "用户信息不存在"),
    INSERT_ERROR(510, "插入信息失败"),
    DELETE_ERROR(511, "删除信息失败"),
    UPDATE_ERROR(512, "更新信息失败"),
    APPLY_ERROR(513, "申请失败");
    private static final String DEFAULT_MESSAGE = "--";
    private Integer code;
    private String message;

    BaseResultStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getDefaultMessage() {
		return DEFAULT_MESSAGE;
	}
    
}
