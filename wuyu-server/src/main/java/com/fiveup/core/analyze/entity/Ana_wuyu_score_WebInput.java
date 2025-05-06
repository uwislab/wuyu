package com.fiveup.core.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Ana_wuyu_score_WebInput {

    private float De;
    private float Zhi;
    private float Ti;
    private float Mei;
    private float Lao;
    private String regrade;
    private String rePer;
    private String X_axles;
    private String Y_axles;
    private String Kfill;
    private String Knormal;
    private String Distance;
    private Integer P;
    private String Afill;
    private String Anormal;
    private String Bfill;
    private String Bnormal;

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

    public String getRegrade() {
        return regrade;
    }

    public void setRegrade(String regrade) {
        this.regrade = regrade;
    }

    public String getRePer() {
        return rePer;
    }

    public void setRePer(String rePer) {
        this.rePer = rePer;
    }

    public String getX_axles() {
        return X_axles;
    }

    public void setX_axles(String x_axles) {
        X_axles = x_axles;
    }

    public String getY_axles() {
        return Y_axles;
    }

    public void setY_axles(String y_axles) {
        Y_axles = y_axles;
    }

    public String getKfill() {
        return Kfill;
    }

    public void setKfill(String kfill) {
        Kfill = kfill;
    }

    public String getKnormal() {
        return Knormal;
    }

    public void setKnormal(String knormal) {
        Knormal = knormal;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public Integer getP() {
        return P;
    }

    public void setP(Integer p) {
        P = p;
    }

    public String getAfill() {
        return Afill;
    }

    public void setAfill(String afill) {
        Afill = afill;
    }

    public String getAnormal() {
        return Anormal;
    }

    public void setAnormal(String anormal) {
        Anormal = anormal;
    }

    public String getBfill() {
        return Bfill;
    }

    public void setBfill(String bfill) {
        Bfill = bfill;
    }

    public String getBnormal() {
        return Bnormal;
    }

    public void setBnormal(String bnormal) {
        Bnormal = bnormal;
    }
}
