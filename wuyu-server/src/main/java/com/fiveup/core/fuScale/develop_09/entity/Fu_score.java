package com.fiveup.core.fuScale.develop_09.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fu_score implements Serializable {
    private Integer id;
    private Integer upid;
    private String title;
    private String type;
    private String starttime;
    private String finishtime;
    private String zhibiao;
    private String beizhu;
    private int score;
    private String zhibiao2;
    private String zhibiao3;
    private String status;
    private String teacherName;
}
