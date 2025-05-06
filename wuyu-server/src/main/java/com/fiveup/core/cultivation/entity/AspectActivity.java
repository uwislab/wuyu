package com.fiveup.core.cultivation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("ctv_aspect_activity")
public class AspectActivity extends BaseEntity {
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    private Integer gid;
    private Integer aid;
    private String title;
    private String tags;
    private String content;
    private Double weight;

    @TableField(exist = false)
    private Integer pageNo;
    @TableField(exist = false)
    private Integer pageSize;
}
