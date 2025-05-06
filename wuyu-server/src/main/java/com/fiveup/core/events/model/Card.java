package com.fiveup.core.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 钟承远
 * @date 2022/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Long activityId;
    private String imgUrl;
    private String activityContent;
    private String startTime;
}
