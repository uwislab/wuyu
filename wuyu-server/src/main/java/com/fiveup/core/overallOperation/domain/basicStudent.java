package com.fiveup.core.overallOperation.domain;

import lombok.Data;

@Data
public class basicStudent {
    private Integer  id;
    private String   student_num;
    private Integer  gender;
    private Integer  classId;
    private Integer  gradeId;
    private String   parentPhoneNum;
    private Integer  deleted;
    private Integer  isReview;
    private Integer  isEnter;
}
