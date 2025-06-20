package com.fiveup.core.noticeBooklet.domain;

import lombok.Data;

/**
 * @author huangxingcan
 * @date 2025/6/14 14:10
 */
@Data
public class Student {

    /**
     * 学生学号
     */
    private Integer studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生班级
     */
    private Integer studentClassNumber;

    /**
     * 学生年级
     */
    private Integer studentGrade;
}
