package com.fiveup.core.classManage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dandelion
 * @Date: 2023/11/27
 * @Time: 18:14
 * @desc
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeInfo {
    private int id;
    private String grade;
    private int classType;
    private String className;
    private int monitorId;
    private int gradeId;
    private String teacherName;
}