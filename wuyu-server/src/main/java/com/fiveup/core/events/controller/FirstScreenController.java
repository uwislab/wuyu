package com.fiveup.core.events.controller;

import com.fiveup.core.events.model.Card;
import com.fiveup.core.events.model.response.CardResp;
import com.fiveup.core.events.model.response.PictureUrlListResp;
import com.fiveup.core.events.model.response.StatisticNumResp;
import com.fiveup.core.events.service.ActivityService;
import com.fiveup.core.events.service.PictureActivityService;
import com.fiveup.core.management.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/12
 */
@Slf4j
@RestController
@RequestMapping("/api/firstScreen")
@CrossOrigin
public class FirstScreenController {


    @Resource
    private PictureActivityService pictureActivityService;


    @Resource
    private ActivityService activityService;

    @GetMapping("/getFirstScreenData")
    public CommonResponse<StatisticNumResp> getFirstScreenData() {
        StatisticNumResp statisticNumResp = activityService.getStatisticNum();
        return CommonResponse.ok(statisticNumResp);
    }

    @GetMapping("/getAllActPic")
    public CommonResponse<PictureUrlListResp> getAllActPic() {
        List<String> allUrlList = pictureActivityService.getAllActPicUrlList();
        PictureUrlListResp pictureUrlListResp = new PictureUrlListResp(allUrlList);
        return CommonResponse.ok(pictureUrlListResp);
    }


    @GetMapping("/getDisplayData")
    public CommonResponse<CardResp> getDisplayData() {
        List<Card> cards = activityService.getDisplayData();
        CardResp cardResp = new CardResp(cards);
        return CommonResponse.ok(cardResp);
    }

}
