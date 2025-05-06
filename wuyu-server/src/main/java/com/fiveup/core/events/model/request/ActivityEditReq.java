package com.fiveup.core.events.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityEditReq {
    private Long activityId;
    private String activityName;
    private List<Long> classIdList;
    private Long startTime;
    private Date startTimeDate;
    private Long teacherId;
    private Integer activityState;
    private Float activityScore;
    private String activityComment;
}
