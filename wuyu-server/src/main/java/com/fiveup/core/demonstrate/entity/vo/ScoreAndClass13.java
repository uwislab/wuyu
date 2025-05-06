package com.fiveup.core.demonstrate.entity.vo;

import com.fiveup.core.demonstrate.entity.Score13;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScoreAndClass13 {
    private String Id;
    private String studentNum;//学号
    private String studentName;
    private String classID;

    private int moralityScore;
    private int intelligenceScore;
    private int physicalScore;
    private int aestheticScore;
    private int labourScore;

    private String evaluateDate;//日期
    private String grade;//年级
    private String className;//班级
    private String gender;
}
