package com.fiveup.core.studentManager.pojo;

import lombok.Data;

/**
 * 返回前端数据
 */
@Data
public class StudentVO {

    private Integer id;

    private String studentNum;

    private String studentName;

    private Integer gender;

    private String className;

    private Integer gradeId;

    private String parentPhoneNum;

    private Integer deleted;

    private Integer isreview;

    private Integer isenter;

    private String schoolName;
}
