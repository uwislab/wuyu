package com.fiveup.core.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 钟承远
 * @date 2022/4/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {

    private TemplateUnit activityName;
    private TemplateUnit activityContent;
    private TemplateUnit teacherId;
    private TemplateUnit activityPlace;
    private TemplateUnit activityType;
    private TemplateUnit grade;
    private TemplateUnit startTime;
    private TemplateUnit withParent;
    private TemplateUnit activityFee;
    private TemplateUnit shouldCarryStuff;
    private TemplateUnit gameContent;
    private TemplateUnit theme;
    private TemplateUnit wearingLimit;
}
