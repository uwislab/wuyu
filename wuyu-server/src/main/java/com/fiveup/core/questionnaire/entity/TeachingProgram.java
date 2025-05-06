package com.fiveup.core.questionnaire.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "教学大纲")
@TableName("teaching_program")
public class TeachingProgram  extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "大纲标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "大纲编号")
    @TableField("code")
    @TableId(type = IdType.ASSIGN_ID)
    private String code;

    @ApiModelProperty(value = "作者姓名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "概要")
    @TableField("outline")
    private String outline;

    @ApiModelProperty(value = "大纲内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "重要等级")
    @TableField("importance")
    private String importance;

}
