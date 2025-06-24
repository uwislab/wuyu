package com.fiveup.core.fuScale.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveup.core.fuScale.aop.Log;
import com.fiveup.core.fuScale.model.*;
import com.fiveup.core.fuScale.service.FuScaleHisService;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/fuScaleHis")
public class FuScaleHisController {

    @Resource
    private FuScaleHisService fuScaleHisService;

    @GetMapping(value = "/getFuScaleByStates")
    public CommonResponse getFuScaleByStates(String[] stateIds){
        List<ScaleHisInfo> scaleInfos=new ArrayList<>();
        List<ScaleHisInfo> temp;
        for(int stateId=0;stateId<stateIds.length;stateId++){
            temp= fuScaleHisService.getScaleBySate(Integer.parseInt(stateIds[stateId]));
            scaleInfos.addAll(temp);
        }
        return CommonResponse.ok(scaleInfos);
    }
}
