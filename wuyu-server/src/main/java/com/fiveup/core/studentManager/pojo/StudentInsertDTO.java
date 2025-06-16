package com.fiveup.core.studentManager.pojo;

import lombok.Data;

@Data
public class StudentInsertDTO {
    private String studentNum;
    private String studentName;
    private Integer gender;
    private String className;
    private Integer gradeId;
    private String parentPhoneNum;
    private Integer deleted;
    private Integer isreview;
    private Integer isenter;
    private Integer schoolId;
}
