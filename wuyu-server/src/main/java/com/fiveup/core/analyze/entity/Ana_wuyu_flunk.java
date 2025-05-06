package com.fiveup.core.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //绑定数据库的表
@Data
public class Ana_wuyu_flunk {
    @Id //绑定数据库的表的元素
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id自增
    private Integer id;
    private String grade;
    private float De_rate;
    private float Zhi_rate;
    private float Ti_rate;
    private float Mei_rate;
    private float Lao_rate;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public float getDe_rate() {
        return De_rate;
    }

    public void setDe_rate(float de_rate) {
        De_rate = de_rate;
    }

    public float getZhi_rate() {
        return Zhi_rate;
    }

    public void setZhi_rate(float zhi_rate) {
        Zhi_rate = zhi_rate;
    }

    public float getTi_rate() {
        return Ti_rate;
    }

    public void setTi_rate(float ti_rate) {
        Ti_rate = ti_rate;
    }

    public float getMei_rate() {
        return Mei_rate;
    }

    public void setMei_rate(float mei_rate) {
        Mei_rate = mei_rate;
    }

    public float getLao_rate() {
        return Lao_rate;
    }

    public void setLao_rate(float lao_rate) {
        Lao_rate = lao_rate;
    }
}
