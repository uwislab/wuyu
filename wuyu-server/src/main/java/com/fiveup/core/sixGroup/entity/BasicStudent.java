package com.fiveup.core.sixGroup.entity;

import lombok.Data;

@Data
public class BasicStudent {
    private Integer id;
    private String studentNum;
    private String studentName;
    private Integer classId;
    private Integer gradeId;
    private Integer gender;
}
