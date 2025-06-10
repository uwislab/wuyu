package com.fiveup.core.studentManager.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "basic_student")
public class StudentManager  {
    private Integer id;
    @ExcelProperty(value = "学号")
    private String studentNum;
    @ExcelProperty(value = "学生姓名")
    private String studentName;
    @ExcelProperty(value = "性别")
    private Integer gender;
    @ExcelProperty(value = "班级")
    private Integer classId;
    @ExcelProperty(value = "年级")
    private Integer gradeId;
    @ExcelProperty(value = "手机号")
    private String parentPhoneNum;
    private Integer deleted;
    private Integer isreview;
    private Integer isenter;
    @ExcelProperty(value = "学校")
    private Integer schoolId;

}
