package com.fiveup.core.fuScore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuSemesterTotalScore {
    private String semester; // 学期，如 "2024-第一学期"
    private Integer deyu;    // 德育
    private Integer zhiyu;   // 智育
    private Integer tiyu;    // 体育
    private Integer meiyu;   // 美育
    private Integer laoyu;   // 劳育
    private Integer totalScore; // 总成绩
}
