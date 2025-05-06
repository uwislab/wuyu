package com.fiveup.core.questionnaire.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "学生信息表")
@TableName("student")
public class Student {

    @ApiModelProperty(value = "学生学号")
    @TableField("student_id")
    private String studentId;

    @ApiModelProperty(value = "学生姓名")
    @TableField("student_name")
    private String studentName;

    @ApiModelProperty(value = "学生性别")
    @TableField("student_gender")
    private int studentGender;

    @ApiModelProperty(value = "班级名称")
    @TableField("student_classname")
    private String studentClassName;

    @ApiModelProperty(value = "班级号")
    @TableField("student_class_number")
    private int studentClassNumber;

    @ApiModelProperty(value = "年级")
    @TableField("student_grade")
    private int studentGrade;

}
