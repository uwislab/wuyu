package com.fiveup.core.management.model.DTO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("basic_student")
public class BasicStudent {
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
}
