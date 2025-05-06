package com.fiveup.core.remark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("fu_student_score")//位于basic_student表中的数据
@Data
public class studentScore {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "student_num")
    private String studentNum;
    private String studentName;
    private Integer classId;
    private Integer moralityScore;
    private Integer intelligenceScore;
    private Integer physicalScore;
    private Integer aestheticScore;
    private Integer labourScore;
//    @TableField(value = "evaluate_date")
    private Integer evaluateDate;
    private String remark;
    private Integer tid;
    private Integer isreview;
    private Integer isenter;
//    增加sid后修改的代码
}
