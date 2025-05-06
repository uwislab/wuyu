package com.fiveup.core.remark.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("fu_class_score")
public class classScore {
    int id;
    int classId;
    int gradeId;
    String className;
    int moralityScore;
    int intelligenceScore;
    int physicalScore;
    int aestheticScore;
    int labourScore;
    int data;
    int isenter;
    int isreview;
}
