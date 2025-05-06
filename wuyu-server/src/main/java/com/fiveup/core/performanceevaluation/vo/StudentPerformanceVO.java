package com.fiveup.core.performanceevaluation.vo;

import com.fiveup.core.performanceevaluation.bean.Teacher;

import java.math.BigDecimal;

/**
 * 学生表现
 */
public class StudentPerformanceVO {
    // 主键ID
    private Integer id;
    // 姓名
    private String name;
    // 德育分数
    private Integer virtue;
    // 智育分数
    private Integer intelligence;
    // 体育分数
    private Integer sports;
    // 美育分数
    private Integer art;
    // 劳育分数
    private Integer labor;
    // 评语
    private String remark;
    // 教师ID对应的教师信息
    private Teacher teacher;

    private Integer tid;
    // 学生ID
    private Integer sid;
    //综合评分
    private BigDecimal totalScore;

    public StudentPerformanceVO() {
    }

    public StudentPerformanceVO(Integer id, String name, Integer virtue, Integer intelligence, Integer sports, Integer art, Integer labor, String remark, Teacher teacher, Integer sid, BigDecimal totalScore) {
        this.id = id;
        this.name = name;
        this.virtue = virtue;
        this.intelligence = intelligence;
        this.sports = sports;
        this.art = art;
        this.labor = labor;
        this.remark = remark;
        this.teacher = teacher;
        this.sid = sid;
        this.totalScore = totalScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVirtue() {
        return virtue;
    }

    public void setVirtue(Integer virtue) {
        this.virtue = virtue;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getSports() {
        return sports;
    }

    public void setSports(Integer sports) {
        this.sports = sports;
    }

    public Integer getArt() {
        return art;
    }

    public void setArt(Integer art) {
        this.art = art;
    }

    public Integer getLabor() {
        return labor;
    }

    public void setLabor(Integer labor) {
        this.labor = labor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }
}
