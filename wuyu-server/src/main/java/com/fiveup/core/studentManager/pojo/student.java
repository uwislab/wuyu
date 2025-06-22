package com.fiveup.core.studentManager.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class student {
    @ExcelProperty("学生学号")
    private String student_num;
    @ExcelProperty("学生姓名")
    private String student_name;
    @ExcelProperty("性别")
    private int gender;
    @ExcelProperty("年级")
    private int class_id;
    @ExcelProperty("班级")
    private int grade_id;
    @ExcelProperty("联系方式")
    private String parent_phone_num;
}
