package com.fiveup.core.demonstrate.controller;

import com.fiveup.core.demonstrate.entity.vo.ScoreAndClass13;
import com.fiveup.core.demonstrate.entity.vo.chartsObj;
import com.fiveup.core.demonstrate.service.StudentScoreService;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 13组编写
 */

@RestController
@RequestMapping("/score")
public class StudentScoreController {
    @Autowired
    StudentScoreService studentScoreService;

    @RequestMapping("/getScore")//列表后端
    public CommonResponse<List<ScoreAndClass13>> getScore(int page_num,
                                                          int page_size,
                                                          String student_num,
                                                          String student_name,
                                                          String class_name,
                                                          String grade){
        List<ScoreAndClass13> score = studentScoreService.getScore(page_num, page_size,
                                                                    student_num, student_name,
                                                                    class_name, grade);
        return CommonResponse.ok(score);
    }

    @RequestMapping("/getChartsData")//图表后端
    public CommonResponse<List<chartsObj>> getChartsData(String className,String gradeName){
        List<chartsObj> allScore = studentScoreService.getAllScore(className,gradeName);
        return CommonResponse.ok(allScore);
    }
    @RequestMapping("/getTableSize")//分页
    public CommonResponse<Integer> getTableSize(String student_num,
                                                String student_name,
                                                String class_name,
                                                String grade) {
        int tableSize = studentScoreService.getTableSize(student_num, student_name,
                class_name, grade);
        return CommonResponse.ok(tableSize);
    }

    @RequestMapping("/getClassName")//模糊查询后端
    public CommonResponse<List<String>> getClassName() {
        List<String> className = studentScoreService.getClassName();
        return CommonResponse.ok(className);
    }

    @RequestMapping("/getGradeName")
    public CommonResponse<List<String>> getGradeName() {
        List<String> gradeName = studentScoreService.getGradeName();
        return CommonResponse.ok(gradeName);
    }

    @GetMapping("/getOneScore")
    public CommonResponse<List<ScoreAndClass13>> getOneScore(String student_num){
        List<ScoreAndClass13> oneScore = studentScoreService.getOneScore(student_num);
        return CommonResponse.ok(oneScore);
    }
}
