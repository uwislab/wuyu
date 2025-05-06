package com.fiveup.core.radar.controller;


import com.fiveup.core.radar.info.radarInfo;
import com.fiveup.core.radar.service.radarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class radarController {

    @Autowired
    private radarService radarService;

    @RequestMapping("/getRadarList")
    public List<radarInfo> getRadarList(){
        List<radarInfo> list = radarService.getRadarList();
        return list;
    }
}
