package com.fiveup.core.cultivation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Harvi
 */
@Data
@Entity
@TableName("basic_teacher")
public class BasicTeacher {
    @Id
    private Integer id;
    private String teacherName;
    private Integer gender;
    private String phoneNum;
    private String position;
    private String title;
    private String role;
    private Integer deleted;
}