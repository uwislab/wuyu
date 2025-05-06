package com.fiveup.core.performanceevaluation.vo;

import java.math.BigDecimal;

/**
 * 5个科目的平均分
 */
public class Average {
    // 教师ID
    private Integer tid;
    // 德育平均分
    private BigDecimal virtue;
    // 智育权重平均分
    private BigDecimal intelligence;
    // 体育权重平均分
    private BigDecimal sports;
    // 美育分数平均分
    private BigDecimal art;
    // 劳育分数平均分
    private BigDecimal labor;

    public Average() {
    }

    public Average(Integer tid, BigDecimal virtue, BigDecimal intelligence, BigDecimal sports, BigDecimal art, BigDecimal labor) {
        this.tid = tid;
        this.virtue = virtue;
        this.intelligence = intelligence;
        this.sports = sports;
        this.art = art;
        this.labor = labor;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public BigDecimal getVirtue() {
        return virtue;
    }

    public void setVirtue(BigDecimal virtue) {
        this.virtue = virtue;
    }

    public BigDecimal getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(BigDecimal intelligence) {
        this.intelligence = intelligence;
    }

    public BigDecimal getSports() {
        return sports;
    }

    public void setSports(BigDecimal sports) {
        this.sports = sports;
    }

    public BigDecimal getArt() {
        return art;
    }

    public void setArt(BigDecimal art) {
        this.art = art;
    }

    public BigDecimal getLabor() {
        return labor;
    }

    public void setLabor(BigDecimal labor) {
        this.labor = labor;
    }
}
