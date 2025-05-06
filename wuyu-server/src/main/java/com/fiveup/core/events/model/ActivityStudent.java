package com.fiveup.core.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityStudent {
    private Long activityId;
    private String studentNum;
    private Float stuActScore;
}
