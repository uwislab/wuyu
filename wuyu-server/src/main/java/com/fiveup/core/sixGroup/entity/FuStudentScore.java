package com.fiveup.core.sixGroup.entity;

import lombok.Data;

@Data
public class FuStudentScore {
    private Integer id;
    private String studentNum;
    private String studentName;
    private Integer classId;
    // 德育
    private Integer moralityScore;
    // 智育
    private Integer intelligenceScore;
    // 体育
    private Integer physicalScore;
    // 美育
    private Integer aestheticScore;
    // 劳育得分
    private Integer labourScore;
    private Integer gradeId;
    private Integer gender;
    private Integer score;
}
