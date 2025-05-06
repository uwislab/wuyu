package com.fiveup.core.collection.controller;

import com.fiveup.core.collection.model.ColView;
import com.fiveup.core.collection.service.ColViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColViewController
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/5/29 17:35
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@RestController
@RequestMapping("/api/view")
public class ColViewController {

    @Autowired
    private ColViewService colViewService;

    //获取全部活动的数据
    @RequestMapping(value = "/listViewAll",method = RequestMethod.GET)
    @ResponseBody
    public List<ColView> getList(){return  colViewService.list();}

    @RequestMapping(value = "/getView/{id}",method = RequestMethod.GET)
    public ColView getView(@PathVariable("id") Long id){
        return colViewService.getView(id);
    }

    @RequestMapping(value = "/deleteview/{id}",method = RequestMethod.POST)
    public ColView delete(@PathVariable("id") String id){
        long l = Long.parseLong(id);
        boolean count = colViewService.deleteView(l);
        if (count){
            return null;
        }else {
            return null;
        }
    }

    @RequestMapping(value = "/updateview",method = RequestMethod.POST)
    public ColView update(ColView colView){
        System.out.println("============================"+colView.toString());
        int count = colViewService.updateView(colView);
        if (count == 1) {
            return colView;
        } else {
            return null;
        }
    }
}
