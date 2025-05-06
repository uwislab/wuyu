package com.fiveup.core.events.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 钟承远
 * @date 2022/4/26
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityStudentAddScoreReq {
    private Long activityId;
    private Float score;
}
