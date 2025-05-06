package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//get,set,以及基本重写
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造
public class SportScore {
    // 学生名
    private String studentName;
    // 学号
    private String studentNum;
    // 性别
    private Integer gender;
    // 年级
    private Integer gradeNum;
    // 班级
    private Integer classNum;
    // 肺活量成绩
    private Integer fvcscore;
    // 跳绳成绩
    private Integer rsscore;
    // 身高体重成绩
    private Integer bmiscore;
    // 坐位体前屈成绩
    private Integer sarscore;
    // 仰卧起坐成绩
    private Integer subscore;
    // 50米往返跑成绩
    private Integer oabscore;
    // 50米短跑成绩
    private Integer dashscore;
}
