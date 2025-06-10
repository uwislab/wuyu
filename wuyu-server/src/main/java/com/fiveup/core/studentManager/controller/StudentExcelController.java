package com.fiveup.core.studentManager.controller;

import com.fiveup.core.commentgeneration.utils.Result;
import com.fiveup.core.studentManager.service.StudentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("studentExcel")
public class StudentExcelController {
    @Autowired
    private StudentManagerService studentManagerService;
    @RequestMapping("export")
    public Result<Void> export() {
        studentManagerService.export();
        return Result.SUCCESS;
    }
}
