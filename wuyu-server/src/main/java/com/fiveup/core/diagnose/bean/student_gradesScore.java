package com.fiveup.core.diagnose.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class student_gradesScore {
    private Long studentId;
    private String studentName;
    private String studentClassNumber;
    private String studentGrade;
    private int sDeyu;
    private int sZhiyu;
    private int sTiyu;
    private int sMeiyu;
    private int sLaoyu;
    private Long sExdate;
}
