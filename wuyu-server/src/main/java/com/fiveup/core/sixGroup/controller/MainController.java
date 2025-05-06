package com.fiveup.core.sixGroup.controller;

import com.fiveup.core.sixGroup.entity.IndexScoreQuery;
import com.fiveup.core.sixGroup.entity.SixStuIcScore;
import com.fiveup.core.sixGroup.entity.StudentScoreQuery;
import com.fiveup.core.sixGroup.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/six")
public class MainController {
    @Autowired
    private MainService service;

    @GetMapping("/getYears")
    public Object 获取年级班级信息(){
      return service.获取年级班级信息();
    }

    @GetMapping("/getFirstIndex")
    public Object 获取一级评价指标(){
        return service.获取一级评价指标();
    }

    @GetMapping("/getChartData")
    public Object 计算生成图表数据(String 一级指标){
        return service.计算生成图表数据(一级指标);
    }

    @GetMapping("/getSportsItem")
    public Object 获取体育项目(){
        // 对应于 wuyu.basic_sprot_item 表
        return service.获取体育项目();
    }

    @PostMapping("/getTableData")
    public Object 分页获取表格数据(@RequestBody StudentScoreQuery q){
        return service.分页获取表格数据(q);
    }
    @PostMapping("/getIndexScoreList")
    public Object 获取指标成绩列表(@RequestBody IndexScoreQuery q){
        return service.获取指标成绩列表(q);
    }

    @GetMapping("/getAllStudent")
    public Object 所有学生(){
        return service.所有学生();
    }

    @GetMapping("/getAllTiScore")
    public Object 获取体育成绩列表(){
        return service.获取体育成绩列表();
    }

    @GetMapping("/mockData")
    public void mockData(){
        // 生成伪造数据
        service.mockData();
    }

    @GetMapping("/get3Index")
    public Object 获取三级评价指标(){
        return service.获取三级评价指标();
    }

    @PostMapping("/addIndexScore")
    public void 添加编辑指标成绩(@RequestBody SixStuIcScore data) {
        service.添加编辑指标成绩(data);
    }
}
