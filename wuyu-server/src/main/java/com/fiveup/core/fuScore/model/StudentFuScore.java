package com.fiveup.core.fuScore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFuScore {

        private String studentName;
        private String studentNum;
        private int gender;
        private int gradeId;
        private int classId;
        private int moralityScore;
        private int intelligenceScore;
        private int physicalScore;
        private int aestheticScore;
        private int labourScore;
        private int evaluateDate;
        private String remark;
}
