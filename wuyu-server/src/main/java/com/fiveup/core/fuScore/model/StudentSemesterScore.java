package com.fiveup.core.fuScore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSemesterScore {
    private int studentId;
    private String semester;
    private int moralityScore; // 德育成绩
    private int intelligenceScore; // 智育成绩
    private int physicalScore; // 体育成绩
    private int aestheticScore; // 美育成绩
    private int labourScore; // 劳育成绩

}