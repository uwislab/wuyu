package com.fiveup.core.cultivation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("ctv_goal_aspect")
public class GoalAspect extends BaseEntity {
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    private Integer gid;
    private String type;
    private String summary;

    @TableField(exist = false)
    private Integer pageNo;
    @TableField(exist = false)
    private Integer pageSize;
}
