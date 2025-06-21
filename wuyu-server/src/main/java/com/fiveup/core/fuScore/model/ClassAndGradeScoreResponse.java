package com.fiveup.core.fuScore.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassAndGradeScoreResponse {
    // 班级信息
    private String className;
    private Integer classDeYu;
    private Integer classZhiYu;
    private Integer classTiYu;
    private Integer classMeiYu;
    private Integer classLaoYu;

    // 年级信息
    private String gradeName;
    private Integer gradeDeYu;
    private Integer gradeZhiYu;
    private Integer gradeTiYu;
    private Integer gradeMeiYu;
    private Integer gradeLaoYu;

}