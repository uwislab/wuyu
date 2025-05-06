package com.fiveup.core.demonstrate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveup.core.demonstrate.service.MyDataService;

@RestController
@RequestMapping("/api/demonstrate")
public class MyDataController {
    @Autowired
    private MyDataService myDataService;

    @GetMapping("/getWydc")
    public Object getWydc() {
        return myDataService.getWydc();
    }

    @PostMapping("/getNjqk")
    public Object getNjqk() {
        return myDataService.getNjqk();
    }

    @GetMapping("/getNjcz")
    public Object getNjcz() {
        return myDataService.getNjcz();
    }

    @PostMapping("/getXYStudent")
    public Object getXYStudent() {
        return myDataService.getXYStudent();
    }

    @PostMapping("/getXYClass")
    public Object getXYClass() {
        return myDataService.getXYClass();
    }

    @GetMapping("/getLineTj")
    public Object getLineTj() {
        return myDataService.getLineTj();
    }

    @GetMapping("/getLineTj2")
    public Object getLineTj2() {
        return myDataService.getLineTj2();
    }

    @GetMapping("/getWanchengdu")
    public Object getWanchengdu() {
        return myDataService.getWanchengdu();
    }

    @GetMapping("/getQxwycj")
    public Object getQxwycj() {
        return myDataService.getQxwycj(null);
    }
}
