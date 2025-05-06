package com.fiveup.core.management.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 读取学生信息
 * @author tqx
 * @date 2022/4/19
 */
@Data
public class StuUploadExt {

    @ExcelProperty("学生学号")
    private String studentNum;

    @ExcelProperty("学生姓名")
    private String studentName;

    @ExcelProperty("性别")
    private String genderChineseCharacter;

    @ExcelProperty("年级")
    private String gradeName;

    @ExcelProperty("班级")
    private String className;

    @ExcelProperty("联系方式")
    private String parentPhoneNum;
}
