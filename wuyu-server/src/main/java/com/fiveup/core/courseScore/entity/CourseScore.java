package com.fiveup.core.courseScore.entity;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseScore implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @Alias("课程名称")
    private String courseName;
    @Alias("课程类型")
    private int courseType;
    @Alias("考试序号")
    private int testNumber;
    @Alias("课程教师")
    private String teacherName;
    @Alias("学生学号")
    private String studentNum;
    @Alias("学生姓名")
    private String studentName;
    @Alias("录入日期")
    private String inputTime;
    @Alias("学生成绩")
    private Integer score;
    @Alias("教师评语")
    private String remark;
}
