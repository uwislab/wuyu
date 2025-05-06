package com.fiveup.core.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //绑定数据库的表
@Data
public class Ana_center_data {
    @Id //绑定数据库的表的元素
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id自增
    private Integer id;
    private String grade;
    private Integer Cluster;
    private float De;
    private float Zhi;
    private float Ti;
    private float Mei;
    private float Lao;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getCluster() {
        return Cluster;
    }

    public void setCluster(Integer cluster) {
        Cluster = cluster;
    }

    public float getDe() {
        return De;
    }

    public void setDe(float de) {
        De = de;
    }

    public float getZhi() {
        return Zhi;
    }

    public void setZhi(float zhi) {
        Zhi = zhi;
    }

    public float getTi() {
        return Ti;
    }

    public void setTi(float ti) {
        Ti = ti;
    }

    public float getMei() {
        return Mei;
    }

    public void setMei(float mei) {
        Mei = mei;
    }

    public float getLao() {
        return Lao;
    }

    public void setLao(float lao) {
        Lao = lao;
    }
}
