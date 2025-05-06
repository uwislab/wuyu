package com.fiveup.core.fuScale.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fiveup.core.fuScale.model.Detail;
import com.fiveup.core.fuScale.model.Domain;
import com.fiveup.core.fuScale.model.ScaleContent;
import com.fiveup.core.fuScale.model.ScaleInfo;
import com.fiveup.core.fuScale.service.FuRuleService;
import com.fiveup.core.fuScale.service.FuScaleService;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/fuRule")
public class FuRuleController {

    @Resource
    private FuRuleService fuRuleService;

    @GetMapping(value="/getRuleInfoByFuId")
    public CommonResponse getRuleInfoByFuId(@RequestParam int ScaleId,
                                            @RequestParam int itemId) {
        return null;
    }

    @PostMapping(value="/insertDetailList")
    public CommonResponse insertDetailList(@RequestBody Map<String, Object> map) {
        List<List<List<Detail>>> detailList = (List<List<List<Detail>>>) map.get("detailList");
        int i = 0, j = 0;
        for(List<List<Detail>> grade : detailList) {
            for(List<Detail> num : grade) {
                System.out.println("年级"+i+"细则"+j);
                System.out.println(num);
                j++;
                for(int k = 0; k < num.size(); k++) {
                    Detail detail = JSON.parseObject(JSON.toJSONString(num.get(k)), new TypeReference<Detail>() { });
                    System.out.println(detail);
                    fuRuleService.insertDetail(detail);
                }
//                for(int k = 0; k < num.size(); k++) {
//                    fuRuleService.insertDetail(i, num.get(k));
//                }
            }
            i++;
            j=0;
        }
        return CommonResponse.ok();
    }
}
