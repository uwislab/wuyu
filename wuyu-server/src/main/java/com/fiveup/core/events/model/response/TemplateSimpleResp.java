package com.fiveup.core.events.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 钟承远
 * @date 2022/4/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateSimpleResp {

    private String activityName;
    private Long templateId;
    private String activityType;
}
