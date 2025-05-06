package com.fiveup.core.cultivation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * @author Harvi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@TableName("ctv_guidance")
public class Guidance extends BaseEntity {
    private Integer tid;
    private String gid;
    private String fileName;
    private String fileLocation;

    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private String guidanceNames;
    @TableField(exist = false)
    private Integer pageNo;
    @TableField(exist = false)
    private Integer pageSize;
}
