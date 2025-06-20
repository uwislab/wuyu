package com.fiveup.core.teacher.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    /**
     * code 状态码
     * msg 提示信息
     * data 结果数据
     */
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {//请求成功信息
        return new Result(200,"success",data);
    }

    public static Result fail(int code,String msg) {//请求失败信息
        return new Result(code,msg,null);
    }
}