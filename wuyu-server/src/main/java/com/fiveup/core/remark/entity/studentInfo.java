package com.fiveup.core.remark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("basic_student")//位于basic_student表中的数据
public class studentInfo {

    @TableId(type = IdType.AUTO)
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
//    增加sid后修改的代码
}
