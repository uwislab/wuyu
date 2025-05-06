package com.fiveup.core.events.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityPageReq {
    private String activityName;
    private Integer activityState;
    private String grade;
    private Integer activityType;
    private Integer activityAspect;
    private List<Integer> classList;
    private Integer pageNum;
    private Integer pageSize;
}
