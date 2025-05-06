package com.fiveup.core.analyze.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //绑定数据库的表
@Data
public class Ana_wuyu_score {
    @Id //绑定数据库的表的元素
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id自增
    private Integer id;
    @ExcelProperty(index = 0, value = "学号")
    private String stu_num;

    @ExcelProperty(index = 1, value = "姓名")
    private String name;

    @ExcelProperty(index = 2, value = "年级")
    private String grade;

    @ExcelProperty(index = 3, value = "德育")
    private float de;

    @ExcelProperty(index = 4, value = "智育")
    private float zhi;

    @ExcelProperty(index = 5, value = "体育")
    private float ti;

    @ExcelProperty(index = 6, value = "美育")
    private float mei;

    @ExcelProperty(index = 7, value = "劳育")
    private float lao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public float getDe() {
        return de;
    }

    public void setDe(float de) {
        this.de = de;
    }

    public float getZhi() {
        return zhi;
    }

    public void setZhi(float zhi) {
        this.zhi = zhi;
    }

    public float getTi() {
        return ti;
    }

    public void setTi(float ti) {
        this.ti = ti;
    }

    public float getMei() {
        return mei;
    }

    public void setMei(float mei) {
        this.mei = mei;
    }

    public float getLao() {
        return lao;
    }

    public void setLao(float lao) {
        this.lao = lao;
    }
}
