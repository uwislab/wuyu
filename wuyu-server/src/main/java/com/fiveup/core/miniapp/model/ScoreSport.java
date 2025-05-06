package com.fiveup.core.miniapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreSport<T> {
    private Long studentNum;
    private T valueFirst;
    private T valueSecond;
    private Integer gender;
    private String classId;//班级ID
    private Integer score;
    private String level;
    private String sportName;
}
