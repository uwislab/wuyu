package com.fiveup.core.sixGroup.entity;

import lombok.Data;

@Data
public class StudentScoreQuery {
    private Integer 当前页;
    private Integer 每页数量;
    private Integer year;
    private Integer tid;
    private Integer cls;
    private String studentNum;
    private String studentName;
}
