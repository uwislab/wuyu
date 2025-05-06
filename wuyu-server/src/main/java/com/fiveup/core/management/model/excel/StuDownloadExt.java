package com.fiveup.core.management.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import jnr.ffi.annotations.In;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author 尔宣赫
 * @date 2022/4/17
 */

@Data
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@HeadRowHeight(30)
@ColumnWidth(30)
@ContentRowHeight(25)
public class StuDownloadExt {


    @ExcelProperty("学生Id")
    private Long studentId;

    @ExcelProperty("学号")
    private String studentNum;

    @ExcelProperty("学生姓名")
    private String studentName;

    @ExcelProperty("性别标识")
    private Integer gender;

    @ExcelProperty("学生性别")
    private String genderChineseCharacter;

    @ExcelProperty("学生年级Id")
    private Integer gradeId;

    @ExcelProperty("学生班级Id")
    private Integer classId;

    @ExcelProperty("学生年级")
    private String gradeName;

    @ExcelProperty("学生班级")
    private String className;

    @ExcelProperty("家长电话")
    private String parentPhoneNum;

}
