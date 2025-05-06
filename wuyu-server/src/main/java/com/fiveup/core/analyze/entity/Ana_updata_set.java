package com.fiveup.core.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity //绑定数据库的表
@Data
public class Ana_updata_set {
    @Id //绑定数据库的表的元素
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id自增
    private Integer id;
    private String Fill_kmeans;
    private String Normal_kmeans;
    private String Distance;
    private Integer P;
    private String Fill_apriori;
    private String Normal_apriori;
    private String Fill_bayes;
    private String Normal_bayes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFill_kmeans() {
        return Fill_kmeans;
    }

    public void setFill_kmeans(String fill_kmeans) {
        Fill_kmeans = fill_kmeans;
    }

    public String getNormal_kmeans() {
        return Normal_kmeans;
    }

    public void setNormal_kmeans(String normal_kmeans) {
        Normal_kmeans = normal_kmeans;
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

    public String getFill_apriori() {
        return Fill_apriori;
    }

    public void setFill_apriori(String fill_apriori) {
        Fill_apriori = fill_apriori;
    }

    public String getNormal_apriori() {
        return Normal_apriori;
    }

    public void setNormal_apriori(String normal_apriori) {
        Normal_apriori = normal_apriori;
    }

    public String getFill_bayes() {
        return Fill_bayes;
    }

    public void setFill_bayes(String fill_bayes) {
        Fill_bayes = fill_bayes;
    }

    public String getNormal_bayes() {
        return Normal_bayes;
    }

    public void setNormal_bayes(String normal_bayes) {
        Normal_bayes = normal_bayes;
    }
}
