package com.fiveup.core.management.model.Resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class DiTiyuscoreResp  {

    private String studentId;

    private String studentName;

    private Float sportsMorality;

    private Float healthKnowledge;

    private Float mentalHealth;

    private Float sportsSkills;

    private Float sportsActivity;

    private int gender;
    private int gradeId;
    private String className;

}