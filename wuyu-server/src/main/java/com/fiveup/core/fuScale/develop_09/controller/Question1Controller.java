package com.fiveup.core.fuScale.develop_09.controller;

import com.fiveup.core.fuScale.develop_09.entity.Fu_score_question;
import com.fiveup.core.fuScale.develop_09.service.Question1Service;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
public class Question1Controller {

    @Autowired
    private Question1Service question1Service;
    @PostMapping("/import")
    public String importQuestions(@RequestParam("file") MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            List<Fu_score_question> questions = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // 跳过标题行
                    continue;
                }
               Fu_score_question question = new Fu_score_question();
                question.setContent(row.getCell(0).getStringCellValue());
                question.setOption1(row.getCell(1).getStringCellValue());
                question.setOption2(row.getCell(2).getStringCellValue());
                question.setOption3(row.getCell(3).getStringCellValue());
                question.setOption4(row.getCell(4).getStringCellValue());
                question.setAnswer(row.getCell(5).getStringCellValue());

                questions.add(question);
            }
            System.out.println(questions);

            question1Service.saveBatch(questions); // 使用MyBatis Plus批量插入
            return "导入成功！";
        } catch (Exception e) {
            e.printStackTrace();
            return "导入失败：" + e.getMessage();
        }
    }
}