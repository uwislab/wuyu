package com.fiveup.core.classManage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassResp {

    private Integer code; // 1成功 0失败
    private String message; // 返回信息

    public static ClassResp success(){
        return new ClassResp(1, "success");
    }

    public static ClassResp success(String message){
        return new ClassResp(1, message);
    }

    public static ClassResp fail(){
        return new ClassResp(0, "fail");
    }

    public static ClassResp fail(String message){
        return new ClassResp(0, message);
    }
}
