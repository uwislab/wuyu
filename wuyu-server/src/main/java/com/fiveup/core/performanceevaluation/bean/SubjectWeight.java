package com.fiveup.core.performanceevaluation.bean;

import java.math.BigDecimal;

public class SubjectWeight {
    private int id;
    private String name;
    private int parentId;
    private BigDecimal weight;
    private String content;
    private int tid;

    public SubjectWeight() {
    }

    public SubjectWeight(int id, String name, int parentId, BigDecimal weight, String content, int tid) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.weight = weight;
        this.tid = tid;
        this.content = content;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
