package com.fiveup.core.questionnaire.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "班级信息")
@TableName("basic_class")
public class Class {

    @ApiModelProperty(value = "班级id")
    @TableId(type = IdType.ASSIGN_ID)
    private Integer classId;

    @ApiModelProperty(value = "班级别名")
    @TableField("class_name")
    private String className;

    @ApiModelProperty(value = "班级所在的年级")
    @TableField("class_grade")
    private Integer classGrade;

    @ApiModelProperty(value = "班级名称")
    @TableField("class_number")
    private Integer classNumber;

    @ApiModelProperty(value = "班主任")
    @TableField("class_headmaster")
    private String classHeadmaster;

}
