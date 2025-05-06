package com.fiveup.core.demonstrate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Score13 {
    private String id;
    private String student_num;
    private String student_name;
    private String class_ID;

    private int morality_score;
    private int intelligence_score;
    private int physical_score;
    private int aesthetic_score;
    private int labour_score;

    private String evaluate_date;

}
