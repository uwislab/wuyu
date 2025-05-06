package com.fiveup.core.miniapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityStudent {

    private Integer activityId;
    private String studentNum;
    private Integer stuActScore;//教师评分
    private Integer activityScoreFamily;//家长评分
    private Integer activityScoreTotal;//总得分

}
