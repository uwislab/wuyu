package com.fiveup.core.remark.common;

public class Result<T> {
    private Integer code;
    private String msg;
    private T data;



    public Result() {

    }

    public Result(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //    @Override
//    public String toString() {
//        return "Result{" +
//                "code=" + code +
//                ", msg='" + msg + '\'' +
//                ", data=" + data +
//                '}';
//    }
    public static Result success(){
        Result result = new Result<>();
        result.setCode(0);
        result.setMsg("success!");
        return result;
    }
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>(data);
        result.setCode(0);
        result.setMsg("success!");
        return result;
    }
    public static Result error(int code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}