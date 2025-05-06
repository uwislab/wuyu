package com.fiveup.core.commentgeneration.bean;

/**
 * 科目
 */
public class Subject {
    // ID
    private Integer id;
    // name
    private String name;

    public Subject() {
    }

    public Subject(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
