package com.fiveup.core.remark.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fiveup.core.remark.entity.studentScore;
import com.fiveup.core.remark.mapper.MyscoreMapper;
import com.fiveup.core.remark.mapper.StudentScoreALLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class scoreController {
    @Autowired
    private StudentScoreALLMapper studentScoreALLMapper;
    @Autowired
    private MyscoreMapper myscoreMapper;

    @GetMapping ("/Mystudent")
    studentScore getByNumAndDate(String studentNum){
        QueryWrapper<studentScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentNum",studentNum);
//        queryWrapper.eq("evaluateDate",evaluateDate);
        List<studentScore> scoreList = studentScoreALLMapper.selectByStudentNum(studentNum,2024);
        return scoreList.get(0);
    }
    @GetMapping("/Mystu2")
    String hello(){
        return "hello";
    }

    @GetMapping("/Mystu3")
    studentScore QuerryAll(String studentNum,int evaluateDate){
//        QueryWrapper<studentScore> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("studentNum",studentNum);
        studentScore scoreList=myscoreMapper.selectOneScore(studentNum,evaluateDate);
        return scoreList;
    }
}
