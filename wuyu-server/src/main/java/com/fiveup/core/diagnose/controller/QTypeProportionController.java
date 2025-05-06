package com.fiveup.core.diagnose.controller;


import com.fiveup.core.diagnose.bean.student_QTypeProportion;
import com.fiveup.core.diagnose.bean.student_classgrade;
import com.fiveup.core.diagnose.service.QTypeProportionService;
import com.fiveup.core.diagnose.service.studentscoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/diagnose")
public class QTypeProportionController {

    @Autowired
    QTypeProportionService qTypeProportionService;
    @Autowired
    studentscoreService stService;
    @RequestMapping("/qtypeproportion")
    @ResponseBody
    public student_QTypeProportion selectQTypeP(@RequestParam("subject") String subject,
    @RequestParam("name") String name,@RequestParam("id") Long id){

        student_classgrade scg = new student_classgrade();
        /*获取学生点的对应年级*/
        scg=stService.getClass(name,id);
        return qTypeProportionService.selectQTypeP(subject,scg.getStudentGrade());
    }

}
