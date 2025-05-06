package com.fiveup.core.overallOperation.domain;

import lombok.Data;

@Data
public class StudentScore {
    private String studentName;
    private Integer moralityScore;
    private Integer intelligenceScore;
    private Integer physicalScore;
    private Integer aestheticScore;
    private Integer labourScore;
    private String isReview;
    private String isEnter;

}
