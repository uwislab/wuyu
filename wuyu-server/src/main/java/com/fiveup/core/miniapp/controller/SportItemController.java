package com.fiveup.core.miniapp.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.miniapp.service.SportItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 史林
 * @date 2022/10/10
 * @function 体育评测项的相关数据库操作--查询
 */
@RestController
@RequestMapping("/miniapp")
public class SportItemController {

    @Resource
    private SportItemService sportItemService;

    @GetMapping("/getItemList")
    public CommonResponse getItemList() {
        return CommonResponse.ok(sportItemService.getItemList());
    }

    @GetMapping("/getClassLimit")
    public CommonResponse getClassLimit(@RequestParam String sportName) {
//        System.out.println(sportName);
        if (sportName == "") {
            return CommonResponse.fail(10001, "未收到当前体育项目名");
        } else {
            return CommonResponse.ok(sportItemService.getClassLimit(sportName));
        }
    }
}
