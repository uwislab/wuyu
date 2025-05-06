package com.fiveup.core.fuScore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuScore {
    private int id;
    private int upid;
    private String title;
    private String type;
    private String startTime;
    private String finishTime;
    private String zhibiao;
    private String zhibiao2;
    private String zhibiao3;
    private String score;
    private String beizhu;
    private String status;
    private String teacher_name;
}
