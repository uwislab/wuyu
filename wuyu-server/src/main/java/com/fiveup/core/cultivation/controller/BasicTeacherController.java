package com.fiveup.core.cultivation.controller;

import com.fiveup.core.cultivation.entity.BasicTeacher;
import com.fiveup.core.cultivation.service.BasicTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harvi
 */
@RestController
@RequestMapping("/api/basicTeacher")
public class BasicTeacherController {
    private final BasicTeacherService basicTeacherService;

    @Autowired
    public BasicTeacherController(BasicTeacherService basicTeacherService) {
        this.basicTeacherService = basicTeacherService;
    }

    @RequestMapping("/list")
    public Object list(@RequestBody BasicTeacher o){
        return basicTeacherService.getList();
    }
}
