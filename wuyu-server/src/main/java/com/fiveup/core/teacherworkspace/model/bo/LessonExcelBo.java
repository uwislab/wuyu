package com.fiveup.core.teacherworkspace.model.bo;

import com.fiveup.core.teacherworkspace.common.annotation.ExcelField;
import lombok.Data;

@Data
public class LessonExcelBo {
    @ExcelField("年级")
    private Integer grade;
    @ExcelField("班级")
    private Integer classNum;
    @ExcelField("班级名称")
    private String className;
    @ExcelField("课程名称")
    private String course;
    @ExcelField("任课老师")
    private String teacherName;
    @ExcelField("任课老师编号")
    private Long teacherId;
}
