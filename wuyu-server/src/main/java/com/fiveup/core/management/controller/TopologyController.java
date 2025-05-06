package com.fiveup.core.management.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import com.fiveup.core.management.model.topology.TopologyNode;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.TopologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
@Slf4j
@RestController
@CrossOrigin
public class TopologyController {

    @Resource
    private TopologyService topologyService;

    @GetMapping("/api/getTopology/{schoolId}")
    public CommonResponse<TopologyNode> getTopology(@PathVariable Long schoolId) {
        TopologyNode topologyNode = topologyService.getTopology(schoolId);
        return CommonResponse.ok(topologyNode);
    }
}