package com.fiveup.core.management.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wangyong
 * @since 2024-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("di_tiyuScore")
public class DiTiyuscore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "student_id",type = IdType.INPUT)
    private String studentId;

    @TableField("student_name")
    private String studentName;

    @TableField("sports_morality")
    private Float sportsMorality;

    @TableField("health_knowledge")
    private Float healthKnowledge;

    @TableField("mental_health")
    private Float mentalHealth;

    @TableField("sports_skills")
    private Float sportsSkills;

    @TableField("sports_activity")
    private Float sportsActivity;

    @TableField(exist = false)
    private int gender;
    @TableField(exist = false)
    private int gradeId;
    @TableField(exist = false)
    private String className;

}