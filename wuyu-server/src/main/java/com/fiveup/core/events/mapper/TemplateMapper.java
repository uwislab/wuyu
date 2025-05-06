package com.fiveup.core.events.mapper;

import com.fiveup.core.events.model.ActivityTemplate;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/28
 */
public interface TemplateMapper {

    List<ActivityTemplate> getTemplateList();

    ActivityTemplate selectOneById(Long templateId);
}
