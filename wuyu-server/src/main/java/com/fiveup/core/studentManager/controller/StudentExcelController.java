package com.fiveup.core.studentManager.controller;

import com.fiveup.core.common.result.Result;
import com.fiveup.core.studentManager.service.StudentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/studentExcel")
public class StudentExcelController {
    @Autowired
    private StudentManagerService studentManagerService;
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        studentManagerService.export(response);
    }
    @PostMapping("/import")
    public Result inport(@RequestBody MultipartFile file){
        studentManagerService.importstudent(file);
        return  Result.ok();
    }
}
