package com.fiveup.core.management.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;

/**
 * @Author 龙江威
 * @Date 2023/11/20
 */

@Data
@ExcelIgnoreUnannotated
public class TeacherExcel implements Serializable {
    @ExcelProperty("老师Id")
    private Long teacherId;

    @ExcelProperty("老师姓名")
    private String teacherName;

    @ExcelProperty(value = "教师性别",converter = SexConverter.class)
    private Integer gender;


    @ExcelProperty("电话号码")
    private String phoneNum;

    @ExcelProperty("职位")
    private String position;

    @ExcelProperty("职级")
    private String title;

    @ExcelProperty("角色")
    private String role;

    @ExcelIgnore
    private String schoolId;
    @ExcelIgnore
    private String username;
    @ExcelIgnore
    private String password;
    @ExcelIgnore
    private String deleted;
}

