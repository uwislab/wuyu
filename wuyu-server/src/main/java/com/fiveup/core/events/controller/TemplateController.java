package com.fiveup.core.events.controller;

import com.fiveup.core.events.model.TemplateDTO;
import com.fiveup.core.events.model.response.TemplateListResp;
import com.fiveup.core.events.model.response.TemplateSimpleResp;
import com.fiveup.core.events.service.TemplateService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/28
 */
@Slf4j
@RestController
@CrossOrigin
public class TemplateController {

    @Resource
    private TemplateService templateService;


    @GetMapping("/api/getActivityTemplate")
    public CommonResponse<TemplateListResp> getActivityTemplateList() {
        try {
            List<TemplateSimpleResp> templateSimpleRespList = templateService.getActivityTemplateList();
            TemplateListResp templateListResp = new TemplateListResp(templateSimpleRespList);
            return CommonResponse.ok(templateListResp);
        }catch (Exception e){
            log.error("getActivityTemplateList || error={}",e.getMessage(),e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }

    @GetMapping("/api/getBaseActivityDetail")
    public CommonResponse<TemplateDTO> getBaseActivityDetail(@RequestParam("templateId") Long templateId) {

        try {
            TemplateDTO templateDTO = templateService.getTemplateDetailById(templateId);
            return CommonResponse.ok(templateDTO);
        }catch (Exception e){
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }
}
