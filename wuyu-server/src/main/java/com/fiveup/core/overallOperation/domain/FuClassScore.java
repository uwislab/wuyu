package com.fiveup.core.overallOperation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class FuClassScore {
    private Integer id;
    private Integer gradeId;
    private Integer classId;
    private String className;
    private Integer moralityScore;
    private Integer intelligenceScore;
    private Integer physicalScore;
    private Integer aestheticScore;
    private Integer labourScore;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime date;
    private String data;
    private int isEnter;
    private int isReview;
}
