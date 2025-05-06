package com.fiveup.core.events.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fiveup.core.events.model.ActivityTemplate;
import com.fiveup.core.events.model.TemplateDTO;
import com.fiveup.core.events.model.response.TemplateSimpleResp;
import com.fiveup.core.events.service.TemplateService;
import com.fiveup.core.events.mapper.TemplateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/28
 */
@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {

    @Resource
    private TemplateMapper templateMapper;

    @Override
    public List<TemplateSimpleResp> getActivityTemplateList() {
        List<ActivityTemplate> activityTemplateList = templateMapper.getTemplateList();
        List<TemplateSimpleResp> templateSimpleRespList = new ArrayList<>();
        if (activityTemplateList != null && activityTemplateList.size() != 0) {
            for (ActivityTemplate activityTemplate : activityTemplateList) {
                String templateRegulationJSONString = activityTemplate.getRegulation();
                if (StringUtils.isEmpty(templateRegulationJSONString)) {
                    continue;
                }
                TemplateDTO templateDTO = JSONObject.parseObject(templateRegulationJSONString, TemplateDTO.class);
                TemplateSimpleResp templateSimpleResp = new TemplateSimpleResp(
                        templateDTO.getActivityName().getContent(),
                        activityTemplate.getId(),
                        templateDTO.getActivityType().getContent());
                templateSimpleRespList.add(templateSimpleResp);
            }
        }
        return templateSimpleRespList;
    }

    @Override
    public TemplateDTO getTemplateDetailById(Long templateId) {

        ActivityTemplate activityTemplate = templateMapper.selectOneById(templateId);
        String JSON = activityTemplate.getRegulation();
        if(StringUtils.isEmpty(JSON)){
            return null;
        }
        TemplateDTO templateDTO = JSONObject.parseObject(JSON, TemplateDTO.class);
        return templateDTO;
    }
}
