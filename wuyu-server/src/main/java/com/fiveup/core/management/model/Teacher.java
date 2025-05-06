package com.fiveup.core.management.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Teacher {
    private Long id;
    private String teacherName;
    private Integer gender;
    private String phoneNum;
    private String position;
    private String title;
    private String role;
    private Long schoolId;

    static List<String> positions;

    static List<String> titles;

    static List<String> roles;


    /**
     * 检查数据
     * @return
     */
    public boolean checkData(){
        // 校验中文姓名和英文姓名
        if(!teacherName.matches("(^([\\u4e00-\\u9fa5]{2,4}$)|(^[A-Za-z]*(\\s[A-Za-z]*)*)$)"))
            return false;
        // 校验性别
        if(gender != 0 && gender != 1)
            return false;
        // 校验手机号
        if(!phoneNum.matches("^1[3-9]\\d{9}$"))
            return false;
        // 校验职位
        if(!position.matches("(^([\\u4e00-\\u9fa5]{2})老师$)|(^校长$)"))
            return false;
        // 校验职称
        if(!title.matches("^([初中高特究])级教师$"))
            return false;
        // 校验角色
        if(!role.matches("^(任课老师|班主任|管理员)$"))
            return false;
        return true;
    }




}
