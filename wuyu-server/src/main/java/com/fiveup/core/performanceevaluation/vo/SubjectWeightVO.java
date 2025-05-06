package com.fiveup.core.performanceevaluation.vo;

import java.math.BigDecimal;

public class SubjectWeightVO {
    private int id;

    private String name;

    private String secondName;

    private BigDecimal weight;

    private BigDecimal secondWeight;

    private String content;

    private int tid;

    public SubjectWeightVO() {
    }

    public SubjectWeightVO(int id, String name, String secondName, BigDecimal weight, BigDecimal secondWeight, String content, int tid) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.weight = weight;
        this.content = content;
        this.tid = tid;
        this.secondWeight = secondWeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public BigDecimal getSecondWeight() {
        return secondWeight;
    }

    public void setSecondWeight(BigDecimal secondWeight) {
        this.secondWeight = secondWeight;
    }
}
