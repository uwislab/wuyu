package com.fiveup.core.demonstrate.controller;

import com.fiveup.core.demonstrate.entity.GradeScore;
import com.fiveup.core.demonstrate.entity.qianDuan;
import com.fiveup.core.demonstrate.service.GradeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/GradeScore")
public class GradeScoreController {
    @Autowired
    private GradeScoreService gradeScoreService;

    @RequestMapping("/getGradeScore")
    public List<qianDuan> getGradeScore(String shuju){
        System.out.println(shuju);
       return gradeScoreService.getGradeScore(shuju);
    }

}
