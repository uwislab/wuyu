package com.fiveup.core.teacherworkspace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    private Long id;
    private Long upid;
    private String title;
    private String type;
    private String starttime;
    private String finishtime;
    private String zhibiao;
    private String zhibiao2;
    private String zhibiao3;
    private Long score;
    private String beizhu;
    private int status;
    private String teacherName;
}
