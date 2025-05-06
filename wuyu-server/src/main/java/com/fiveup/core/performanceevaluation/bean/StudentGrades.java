package com.fiveup.core.performanceevaluation.bean;

import lombok.Data;

@Data
public class StudentGrades {
    // id
    private int id;
    // 学号
    private String studentNum;
    // 姓名
    private String studentName;
    // 性别
    private int gender;
    // 年级
    private int grade;
    // 班级
    private String className;

    // 总分
    private int Score;
    // 德育
    private int moralityScore;
    // 智育
    private int intelligenceScore;
    // 体育
    private int physicalScore;
    // 美育
    private int aestheticScore;
    // 劳育
    private int labourScore;

    // 评价
    private String remark;
}
