package com.fiveup.core.studentManager.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "basic_student")
public class StudentManager  {
    private Integer id;
    private String studentNum;
    private String studentName;
    private Integer gender;
    private Integer classId;
    private Integer gradeId;
    private String parentPhoneNum;
    private Integer deleted;
    private Integer isreview;
    private Integer isenter;
    private Integer schoolId;

}
