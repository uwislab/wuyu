package com.fiveup.core.events.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAddReq {
    private Long id;
    private String activityName;
    private List<Long> classIdList;
    private String classId;
    private Integer activityType;
    private Integer activityAspect;
    private String grade;
    private String activityContent;
    private String activityPlace;
    private Long startTime;
    private Date startTimeDate;
    private Long teacherId;
    private String withParent;
    private String activityFee;
    private String shouldCarryStuff;
    private String gameContent;
    private String wearingLimit;
}
