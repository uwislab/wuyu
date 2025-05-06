package com.fiveup.core.diagnose.controller;


import com.fiveup.core.diagnose.service.teachactivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

@Controller
@RequestMapping("/diagnose")
public class teachactivityController {

    @Autowired
    teachactivityService teachactivityservice;
    @GetMapping("/teachactivity")
    @ResponseBody
    public String Insertteachactivity(
            @RequestParam("activity") String activity,
            @RequestParam("sclass") int sclass,
            @RequestParam("grades") int grades,
            @RequestParam("date") String date
    ) throws ParseException {
        teachactivityservice.Insertteachactivity(activity,sclass,grades,date);
        return "success";
    }
}
