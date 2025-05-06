package com.fiveup.core.diagnose.controller;

import com.fiveup.core.diagnose.bean.student_gradesScore;
import com.fiveup.core.diagnose.service.FiveupManagementService;
import com.fiveup.core.diagnose.service.studentscoreService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/diagnose")
public class FiveupManagement {


    @Autowired
    studentscoreService stService;

    @Autowired
    FiveupManagementService fiveupManagementService;

    @RequestMapping("/getAllScore")
    @ResponseBody
    public List<student_gradesScore> getAllScore(){
        return stService.getAllScore();
    }

    @RequestMapping("/insertstudentscore")
    @ResponseBody
    public String Insertstudentscore(@RequestParam("id") int id,@RequestParam("deyu") int deyu,
    @RequestParam("zhiyu") int zhiyu,@RequestParam("tiyu") int tiyu,
                                     @RequestParam("meiyu") int meiyu,@RequestParam("laoyu") int laoyu

    ) throws ParseException {
        long timestamp = System.currentTimeMillis();
        boolean insertstudentscore = fiveupManagementService.insertstudentscore(id, deyu, zhiyu, tiyu, meiyu, laoyu, timestamp);
        if (insertstudentscore){
            return "success";
        }
        return "此学生不存在";
    }

    @RequestMapping("/updatestudentscore")
    @ResponseBody
    public String Updatestudentscore(@RequestParam("id") int id,@RequestParam("deyu") int deyu,
                                     @RequestParam("zhiyu") int zhiyu,@RequestParam("tiyu") int tiyu,
                                     @RequestParam("meiyu") int meiyu,@RequestParam("laoyu") int laoyu
    ) throws ParseException {
        fiveupManagementService.updatestudentscore(id,deyu,zhiyu,tiyu,meiyu,laoyu);
        return "success";
    }
    @RequestMapping("/deletestudentscore")
    @ResponseBody
    public String Deletestudentscore(@RequestParam("id") int id) throws ParseException {
        fiveupManagementService.deletestudentscore(id);
        return "success";
    }

}
