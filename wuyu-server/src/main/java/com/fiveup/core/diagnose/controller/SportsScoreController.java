package com.fiveup.core.diagnose.controller;

import com.fiveup.core.diagnose.bean.student_sportsScore;
import com.fiveup.core.diagnose.mapper.SportsScoreMapper;
import com.fiveup.core.diagnose.service.SportsScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/diagnose")
public class SportsScoreController {

    @Autowired
    SportsScoreService sportsScoreService;
    @RequestMapping("/tiyuscore")
    @ResponseBody
    public String getSportsScoreByKey(@RequestParam("name") String name, @RequestParam("id") int id){
        return sportsScoreService.StudnettiyuScoreDiagnose(name,id);
    }

    @RequestMapping("/tiyuscore/json")
    @ResponseBody
    public Map<String, Object> getSportsScoreAsJson(@RequestParam("name") String name, @RequestParam("id") int id) {
        return sportsScoreService.StudnettiyuScoreDetail(name, id);
    }
}
