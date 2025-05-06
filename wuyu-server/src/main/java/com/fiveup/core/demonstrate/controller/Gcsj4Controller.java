package com.fiveup.core.demonstrate.controller;

import com.fiveup.core.demonstrate.entity.Gcsj4;
import com.fiveup.core.demonstrate.service.Gcsj4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/Gcsj4")
public class Gcsj4Controller {
    @Autowired
    private Gcsj4Service gcsj4Service;

    @RequestMapping("/getGcsj4")
    public List<Gcsj4> getGcsj4(){

        return gcsj4Service.getGcsj4();
    }
}

