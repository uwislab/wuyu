package com.fiveup.core.fuScore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSemesterScore {
    private Integer studentId;
    private String semester;
    private Integer moralityScore; // 德育成绩
    private Integer intelligenceScore; // 智育成绩
    private Integer physicalScore; // 体育成绩
    private Integer aestheticScore; // 美育成绩
    private Integer labourScore; // 劳育成绩

}