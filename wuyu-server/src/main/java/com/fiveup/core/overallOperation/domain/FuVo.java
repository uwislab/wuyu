package com.fiveup.core.overallOperation.domain;

import lombok.Data;

@Data
public class FuVo {
    private String className;
    private Integer moralityScore;
    private Integer intelligenceScore;
    private Integer physicalScore;
    private Integer aestheticScore;
    private Integer labourScore;
    private boolean reviewed=true;
    private boolean gradeEntered=true;
}
