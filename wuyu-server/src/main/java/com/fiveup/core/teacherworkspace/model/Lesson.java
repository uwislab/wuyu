package com.fiveup.core.teacherworkspace.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("basic_lesson")
public class Lesson {
    @TableId
    private Long id;
    private int grade;
    private int classNum;
    private String className;
    private String course;
    private String teacherName;
    private Long teacherId;
    private String academicYear;
    private int semester;
    private Boolean isCurrent;
}
