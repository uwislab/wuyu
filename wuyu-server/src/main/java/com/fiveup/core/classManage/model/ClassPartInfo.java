package com.fiveup.core.classManage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassPartInfo {
    private String grade;
    private String className;
    private String  headTeacherNAme;
    private String  introductionToClass;
    private int classId;
}