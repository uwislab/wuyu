package com.fiveup.core.collection.controller;

import com.fiveup.core.collection.model.ColScore;
import com.fiveup.core.collection.service.ColScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColScoreController
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 9:03
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@RestController
@RequestMapping("/api/studentscore")
public class ColScoreController {

    @Autowired
    private ColScoreService colScoreService;

    //获得学生全部成绩信息
    @RequestMapping(value = "/scorelist",method = RequestMethod.GET)
    @ResponseBody
    public List<ColScore> getList(){
        return colScoreService.list();
    }

    //获取学生成绩
    @RequestMapping(value = "/getscore/{id}",method = RequestMethod.GET)
    public ColScore getItem(@PathVariable("id") Long id){
        return colScoreService.getScore(id);
    }

    //添加学生成绩
    @RequestMapping(value = "/addscore",method = RequestMethod.POST)
    @ResponseBody
    public ColScore add(ColScore colScore){
        int count = colScoreService.addScore(colScore);
        if (count==1){
            return colScore;
        }else {
            return null;
        }
    }

    //修改学生成绩评价
    @RequestMapping(value = "/updatescore",method = RequestMethod.POST)
    public ColScore update(ColScore colScore){
        System.out.println("============================"+colScore.toString());
        int count = colScoreService.updateScore(colScore);
        if (count ==1){
            return  colScore;
        }else {
            return null;
        }
    }

    //删除学生成绩信息
    @RequestMapping(value = "/deletescore",method = RequestMethod.POST)
    @ResponseBody
    public ColScore delete(@RequestParam("id") Long id){
        boolean count = colScoreService.deleteScore(id);
        if (count){
            return null;
        }else {
            return null;
        }
    }

    //批量删除学生成绩
    @RequestMapping(value = "/deletescorelist",method = RequestMethod.POST)
    @ResponseBody
    public ColScore deleteScore(@RequestParam("ids")List<Long> ids){
        boolean count = colScoreService.deleteScore(ids);
        if (count){
            return null;
        }else {
            return null;
        }
    }
}
