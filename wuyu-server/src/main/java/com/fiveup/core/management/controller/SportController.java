package com.fiveup.core.management.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BizErrorCodeEnum;
import com.fiveup.core.management.pojo.SportScore;
import com.fiveup.core.management.pojo.SportSearchVO;
import com.fiveup.core.management.service.SportScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sportScore")
@CrossOrigin
public class SportController {
    @Resource
    private SportScoreService service;

    /**
     * 获取所有体育成绩
     * @return 结果信息
     */
    @GetMapping("/getAllScores")
    public CommonResponse<List<SportScore>> getAllScores(){
        List<SportScore> scores = service.getSportScore();
        System.out.println("这里的大小:"+scores.size());
        return CommonResponse.ok(scores);
    }

    /**
     * 根据条件搜索体育成绩
     * @param sportSearchVO 查询条件
     * @return 结果信息
     */
    @GetMapping("/getSportsForSearch")
    public CommonResponse<List<SportScore>> getSportsForSearch(SportSearchVO sportSearchVO){
        System.out.println(sportSearchVO);
        List<SportScore> scores = service.findSportScoreForSearch(sportSearchVO);
        return CommonResponse.ok(scores);
    }

    /**
     * 修改体育成绩
     * @param score 要修改的数据
     * @return 结果信息
     */
    @PutMapping("/editSportScore")
    public CommonResponse<Void> editSportScore(@RequestBody SportScore score){
        System.out.println(score);
        List<String> flag = service.editBmiSportScore(score);
        if(!flag.isEmpty()){
            return CommonResponse.fail(BizErrorCodeEnum.CREATE_PLAN_ERRNO,flag.toString());
        }else return CommonResponse.ok();
    }

    /**
     * 删除体育成绩
     * @param score 要删除的数据
     * @return 结果信息
     */
    @DeleteMapping("/deleteSportScore")
    public CommonResponse<Void> deleteSportScore(SportScore score){
        System.out.println(score);
        List<String> flag = service.removeSportScore(score);
        if(!flag.isEmpty()){
            return CommonResponse.fail(BizErrorCodeEnum.CREATE_PLAN_ERRNO,flag.toString());
        }else return CommonResponse.ok();
   }

    /**
     * 添加体育成绩
     * @param score 要添加的数据
     * @return 结果信息
     */
   @PostMapping("/addSportScore")
   public CommonResponse<Void> addSportScore(@RequestBody SportScore score){
        System.out.println(score);
        List<String> flag = service.addSportScore(score);
        if(!flag.isEmpty()){
            return CommonResponse.fail(BizErrorCodeEnum.CREATE_PLAN_ERRNO,flag.toString());
        }else return CommonResponse.ok();
    }

    /**
     * 批量删除体育成绩
     * @param ids 要删除的学号集合
     * @return 结果信息
     */
    @DeleteMapping("/deleteBatchSportScore")
    public CommonResponse<Void> deleteBatchSportScore(@RequestBody List<String> ids){
        System.out.println(ids);
        List<String> flag = service.deleteBatchSportScore(ids);
        if(!flag.isEmpty()){
            return CommonResponse.fail(BizErrorCodeEnum.CREATE_PLAN_ERRNO,flag.toString());
        }else return CommonResponse.ok();
    }

}
