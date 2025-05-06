package com.fiveup.core.remark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data//传输学生的所有数据，包括basic_student表和fu_student_score表的需要数据
public class studentAllInfo {

    //这些数据在basic_student表中
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String studentNum;
    private String studentName;
    private Integer classId;
    private Integer gradeId;
    private Integer isreview;
    private Integer isenter;

    //这些数据在fu_student_score表中
    private Integer moralityScore;
    private Integer intelligenceScore;
    private Integer physicalScore;
    private Integer aestheticScore;
    private Integer labourScore;
    private Integer tid;
    private String remark;
}
