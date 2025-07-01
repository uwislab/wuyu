package com.fiveup.core.fuScore.model;

import java.util.Date;

public class StudentScore {
    private static final long serialVersionUID = 1L;

    private Integer id;             // 主键ID
    private Integer studentId;      // 学生ID
    private String studentName;     // 学生姓名
    private Integer classId;        // 班级ID
    private Double moralityScore;   // 德育成绩
    private Double intelligenceScore; // 智育成绩
    private Double physicalScore;   // 体育成绩
    private Double aestheticScore;  // 美育成绩
    private Double labourScore;     // 劳育成绩
    private Double totalScore;      // 总分
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间
}
