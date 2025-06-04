package com.fiveup.core.courseScore.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseScoreStatisticsVO implements Serializable {
    private String courseName; // 课程名称
    private int bucket_0_59;   // 0-59 分数段人数
    private int bucket_60_69;  // 60-69 分数段人数
    private int bucket_70_79;  // 70-79 分数段人数
    private int bucket_80_89;  // 80-89 分数段人数
    private int bucket_90_100; // 90-100 分数段人数
}
