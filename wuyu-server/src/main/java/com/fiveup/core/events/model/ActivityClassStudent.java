package com.fiveup.core.events.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author LiuYang
 * @version 1.0
 * @date 2024-12-02 21:30
 * @description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="fu_activity_student")
public class ActivityClassStudent {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer activityId;
    private String studentName;
    private String studentNum;
    private String classId;
    private Integer gender;
    private String parentPhone;
    @TableField(value = "teacher")
    private String teacherName;
    private Float parentScore;
    private Float teacherScore;
    private Float totalScore;
    private Date createTime;
}
