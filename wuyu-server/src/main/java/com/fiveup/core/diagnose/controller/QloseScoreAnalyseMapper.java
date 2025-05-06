package com.fiveup.core.diagnose.controller;


import com.fiveup.core.diagnose.service.QloseScoreAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/diagnose")
public class QloseScoreAnalyseMapper {

    @Autowired
    QloseScoreAnalyseService qloseScoreAnalyseService;
    @RequestMapping("/losescoreanalyse")
    @ResponseBody
    public String QloseScoreAnalyse(@RequestParam("subject") String subject,
                                    @RequestParam("name") String name,@RequestParam("id") Long id){

        return qloseScoreAnalyseService.loseScoreAnalyse(subject,name,id);
    }

}
