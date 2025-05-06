package com.fiveup.core.questionnaire.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentVO {

    @ApiModelProperty(value = "学生学号")
    private String StudentId;

    @ApiModelProperty(value = "学生姓名")
    private String StudentName;

    @ApiModelProperty(value = "学生年级")
    private String StudentGrade;

    @ApiModelProperty(value = "学生班级")
    private String StudentClassNumber;

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentGrade() {
        return StudentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        StudentGrade = studentGrade;
    }

    public String getStudentClassNumber() {
        return StudentClassNumber;
    }

    public void setStudentClassNumber(String studentClassNumber) {
        StudentClassNumber = studentClassNumber;
    }
}
