package com.fiveup.core.teacherworkspace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private Long id;
    private int grade;
    private int classNum;
    private String className;
    private String course;
    private String teacherName;
    private Long teacherId;
}
