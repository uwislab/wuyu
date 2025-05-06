package com.fiveup.core.fuScore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuClassScore {
    private int id;
    private int grade_ID;
    private int class_ID;
    private String class_name;
    private int morality_score;
    private int intelligence_score;
    private int physical_score;
    private int aesthetic_score;
    private int labour_score;
    private String data;
    private int isenter;
    private int isreview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade_ID() {
        return grade_ID;
    }

    public void setGrade_ID(int grade_ID) {
        this.grade_ID = grade_ID;
    }

    public int getClass_ID() {
        return class_ID;
    }

    public void setClass_ID(int class_ID) {
        this.class_ID = class_ID;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getMorality_score() {
        return morality_score;
    }

    public void setMorality_score(int morality_score) {
        this.morality_score = morality_score;
    }

    public int getIntelligence_score() {
        return intelligence_score;
    }

    public void setIntelligence_score(int intelligence_score) {
        this.intelligence_score = intelligence_score;
    }

    public int getPhysical_score() {
        return physical_score;
    }

    public void setPhysical_score(int physical_score) {
        this.physical_score = physical_score;
    }

    public int getAesthetic_score() {
        return aesthetic_score;
    }

    public void setAesthetic_score(int aesthetic_score) {
        this.aesthetic_score = aesthetic_score;
    }

    public int getLabour_score() {
        return labour_score;
    }

    public void setLabour_score(int labour_score) {
        this.labour_score = labour_score;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIsenter() {
        return isenter;
    }

    public void setIsenter(int isenter) {
        this.isenter = isenter;
    }

    public int getIsreview() {
        return isreview;
    }

    public void setIsreview(int isreview) {
        this.isreview = isreview;
    }


}
