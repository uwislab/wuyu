package com.fiveup.core.cultivation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@TableName("indicator")
public class Indicator extends BaseEntity {

    private Integer ctvGoalId;
    private Integer max1;
    private Integer min1;
    private Integer max2;
    private Integer min2;
    private Integer max3;
    private Integer min3;
    private Integer max4;
    private Integer min4;

}
