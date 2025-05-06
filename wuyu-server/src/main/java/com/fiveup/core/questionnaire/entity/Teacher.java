package com.fiveup.core.questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: Teacher
 * @Package: com.elk.fiveup.core.controller
 * @Description: 教师实体类
 * @Datetime: 2021/9/3   10:21
 * @Author: Min
 */

@Data
@AllArgsConstructor
public class Teacher {

    private Integer teacherId;
    private String username;
    private String password;

}
