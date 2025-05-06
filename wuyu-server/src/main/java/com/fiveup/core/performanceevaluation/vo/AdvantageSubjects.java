package com.fiveup.core.performanceevaluation.vo;

/**
 * 优势科目优秀人数（分数在80及其以上的）
 */
public class AdvantageSubjects {
    // 德育
    private Integer virtue;
    // 智育
    private Integer intelligence;
    // 体育
    private Integer sports;
    // 美育
    private Integer art;
    // 劳育
    private Integer labor;
    // 总人数
    private Integer totalNum;

    public AdvantageSubjects() {
    }

    public AdvantageSubjects(Integer virtue, Integer intelligence, Integer sports, Integer art, Integer labor, Integer totalNum) {
        this.virtue = virtue;
        this.intelligence = intelligence;
        this.sports = sports;
        this.art = art;
        this.labor = labor;
        this.totalNum = totalNum;
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

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
