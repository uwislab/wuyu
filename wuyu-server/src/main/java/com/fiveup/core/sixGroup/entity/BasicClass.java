package com.fiveup.core.sixGroup.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Column;

@Data
public class BasicClass {
    private Integer id;
    private String grade;
    @TableField(value = "class")
    private Integer cls;
    private String className;
}
