package com.fiveup.core.management.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
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
public class TeaExt {

    @ExcelProperty("老师Id")
    private Long teacherId;

    @ExcelProperty("老师姓名")
    private String teacherName;

    @ExcelProperty("性别标识")
    private Integer gender;

    @ExcelProperty("学生性别")
    private String genderChineseCharacter;

    @ExcelProperty("电话号码")
    private String phoneNum;

    @ExcelProperty("职位")
    private String position;

    @ExcelProperty("职级")
    private String title;

    @ExcelProperty("角色")
    private String role;
}
