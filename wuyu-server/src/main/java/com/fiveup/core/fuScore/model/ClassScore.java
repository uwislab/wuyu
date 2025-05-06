package com.fiveup.core.fuScore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassScore {

        private int classID;
        private int moralityScore;
        private int intelligenceScore;
        private int physicalScore;
        private int aestheticScore;
        private int labourScore;
        private int evaluateDate;
}
