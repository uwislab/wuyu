package com.fiveup.core.cultivation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Harvi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@TableName("ctv_goal")
public class Goal extends BaseEntity {
    private Integer tid;
    private String editorTids;
    private Integer reviewerTid;
    private String title;
    private Integer gid;
    private String forGrade;
    private String forTerm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date launchDate;
    private Integer state;
    private String remark;

    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private String editorNames;
    @TableField(exist = false)
    private String reviewerName;
    @TableField(exist = false)
    private String fileLocation;
    @TableField(exist = false)
    private String fileName;
    @TableField(exist = false)
    private Integer stateType;
}
