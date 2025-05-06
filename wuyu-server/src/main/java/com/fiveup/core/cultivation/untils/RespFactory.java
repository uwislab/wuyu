package com.fiveup.core.cultivation.untils;

import com.fiveup.core.cultivation.entity.vo.Result;
import com.fiveup.core.cultivation.enums.Resp;

/**
 * @author Harvi
 */
public class RespFactory {
    public static Result<Void> success() {
        return new Result<>(Resp.SUCCESS.getCode(), Resp.SUCCESS.getMsg());
    }

    public static Result<Void> success(String msg) {
        return new Result<>(Resp.SUCCESS.getCode(), msg);
    }

    public static Result<Void> success(int code, String msg) {
        return new Result<>(code, msg);
    }

    public static Result<Void> fail() {
        return new Result<>(Resp.FAIL.getCode(), Resp.FAIL.getMsg());
    }

    public static Result<Void> fail(String msg) {
        return new Result<>(Resp.FAIL.getCode(), msg);
    }

    public static Result<Void> fail(int code, String msg) {
        return new Result<>(code, msg);
    }
}
