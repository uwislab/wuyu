package com.fiveup.core.diagnose.controller;


import com.fiveup.core.diagnose.bean.student_QTypeScore;
import com.fiveup.core.diagnose.service.QTypeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/diagnose")
public class QTypeScoreController {

    @Autowired
    QTypeScoreService qTypeScoreService;
    @RequestMapping("/qtypescore")
    @ResponseBody
    public student_QTypeScore selectQTypeP(@RequestParam("subject") String subject,
                                           @RequestParam("name") String name, @RequestParam("id") Long id){

        return qTypeScoreService.slectQTypeS(id,name,subject);
    }

}
