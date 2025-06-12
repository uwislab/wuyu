package com.fiveup.core.teacherworkspace.model.bo;

import com.fiveup.core.teacherworkspace.common.annotation.ExcelField;
import lombok.Data;

@Data
public class LessonExcelBo {
    @ExcelField("年级（格式：年级数字）")
    private Integer grade;
    @ExcelField("班级（格式：班级数字）")
    private Integer classNum;
    @ExcelField("班级名称")
    private String className;
    @ExcelField("学年（格式：xxxx-xxxx）")
    private String academicYear;
    @ExcelField("学期（格式：学期数字，1、第一学期；2、代表第二学期）")
    private Integer semester;
    @ExcelField("课程名称")
    private String course;
    @ExcelField("任课老师")
    private String teacherName;
    @ExcelField("任课老师编号")
    private Long teacherId;
}
