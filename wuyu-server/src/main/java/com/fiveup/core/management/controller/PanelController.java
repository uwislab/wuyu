package com.fiveup.core.management.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.model.Resp.Panel;
import com.fiveup.core.management.model.Resp.PanelStatisticResp;
import com.fiveup.core.management.model.topology.TopologyNode;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.PanelService;
import jnr.ffi.annotations.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
@Slf4j
@RestController
@CrossOrigin
public class PanelController {

    @Resource
    private CommonManagementService commonManagementService;

    @Resource
    private PanelService panelService;

    @GetMapping("/api/management/getPanelData/{schoolId}")
    public CommonResponse<PanelStatisticResp> getPanelData(@PathVariable Integer schoolId) {

        PanelStatisticResp panelStatisticResp = panelService.getStatisticData(schoolId);
        
        return CommonResponse.ok(panelStatisticResp);
    }




}
