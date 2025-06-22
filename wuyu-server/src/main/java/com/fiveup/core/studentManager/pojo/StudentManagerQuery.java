package com.fiveup.core.studentManager.pojo;

import lombok.Data;

@Data
public class StudentManagerQuery {
    private Integer id;
    private String studentNum;
    private String studentName;
    private Integer gender;
    private Integer classId;
    private String className;
    private Integer gradeId;
    private String parentPhoneNum;
    private Integer deleted;
    private Integer isreview;
    private Integer isenter;
    private Integer schoolId;
    //一页多少条数据
    private Integer sizeOfPage;
    //当前页码
    private Integer page;
}
