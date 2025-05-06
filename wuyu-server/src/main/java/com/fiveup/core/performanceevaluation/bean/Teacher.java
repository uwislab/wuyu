package com.fiveup.core.performanceevaluation.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    // 主键ID
    private Integer teacherID;
    // 教师姓名
    private String username;
    // 账号密码
    private String password;


}
