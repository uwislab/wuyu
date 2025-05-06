package com.fiveup.core.events.service;

import com.fiveup.core.events.model.TemplateDTO;
import com.fiveup.core.events.model.response.TemplateSimpleResp;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/28
 */
public interface TemplateService {


    List<TemplateSimpleResp> getActivityTemplateList();

    TemplateDTO getTemplateDetailById(Long templateId);
}
