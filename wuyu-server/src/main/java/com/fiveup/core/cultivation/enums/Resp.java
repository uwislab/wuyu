package com.fiveup.core.cultivation.enums;

/**
 * @author Harvi
 */
public enum Resp {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAIL(50, "操作失败");

    private final int code;
    private final String msg;

    Resp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
