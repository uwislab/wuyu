package com.fiveup.core.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //绑定数据库的表
@Data
public class Ana_kmeanspoint_clunumber {
    @Id //绑定数据库的表的元素
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id自增
    private Integer id;
    private Integer Cluster;
    private Integer Number;

    public Integer getCluster() {
        return Cluster;
    }

    public void setCluster(Integer cluster) {
        Cluster = cluster;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }
}
