package com.fiveup.core.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity //绑定数据库的表
@Data
public class Ana_kmeanspoint {
    @Id //绑定数据库的表的元素
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id自增
    private Integer id;
    private Integer Cluster;
    private float Score_x;
    private float Score_y;


    public Integer getCluster() {
        return Cluster;
    }

    public void setCluster(Integer cluster) {
        Cluster = cluster;
    }

    public float getScore_x() {
        return Score_x;
    }

    public void setScore_x(float score_x) {
        Score_x = score_x;
    }

    public float getScore_y() {
        return Score_y;
    }

    public void setScore_y(float score_y) {
        Score_y = score_y;
    }
}
