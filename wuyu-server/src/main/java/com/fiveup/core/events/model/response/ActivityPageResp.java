package com.fiveup.core.events.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityPageResp {
    private Long activityId;
    private String activityName;
    private Integer activityType;
    private Integer activityAspect;
    private Long startTime;
    private Date startTimeDate;
    private Integer activityState;
    private Float activityScore;
    private List<String> classNameList;
    private String teacherComment;

    private Integer studentNumber;
}
