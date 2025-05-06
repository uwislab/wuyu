package com.fiveup.core.analyze.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Excel_wuyu_score {
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

}
