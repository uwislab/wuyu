package com.fiveup.core.cultivation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Harvi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@TableName("ctv_minutes")
public class Minutes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tid;
    private Integer mid;
    private String content;

    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private Integer pageNo;
    @TableField(exist = false)
    private Integer pageSize;
}
