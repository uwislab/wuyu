package com.fiveup.core.analyze.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fiveup.core.analyze.entity.Ana_wuyu_score;
import com.fiveup.core.analyze.entity.Excel_wuyu_score;
import com.fiveup.core.analyze.repository.WuyuScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/excel")
public class ExcelController {
//    @Autowired
//    private TestRepository testRepository;

    @Autowired
    private WuyuScoreRepository wuyuScoreRepository;

    private int flag = 0;

    @GetMapping("/createexcel")
    public String exceloutput(){
        String res = "成功";
        // 表格存放位置
        String path = "C:\\Users\\ASUS\\Desktop\\学生五育成绩.xlsx";
        File file = new File(path);
        if (!file.exists()) {
            // 数据
            List<String> list = new ArrayList<>();
            // 建立对象
            ExcelWriter build = EasyExcel.write(path, Excel_wuyu_score.class).build();
            WriteSheet sheet = EasyExcel.writerSheet("test").build();
            // 写入
            build.write(list, sheet);
            // 关闭
            build.finish();
        } else {
            res = "文件已存在";
        }
        return res;
    }
    @RequestMapping("/upload")
    @ResponseBody
    public Integer excelupload(MultipartFile file) {
//        System.out.println("开始shanch");
        if (file == null){
            flag = 0;
        }else {
            try {
                String originalFilename = file.getOriginalFilename();
                assert originalFilename != null;
                String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                if (!".xls".equalsIgnoreCase(fileType) && !".xlsx".equalsIgnoreCase(fileType)) {
                    flag = 0;
                }
                // 文件输入流
                InputStream in = file.getInputStream();
//            System.out.println("文件输入流");
                // 读
                EasyExcel.read(in, Ana_wuyu_score.class, new AnalysisEventListener<Ana_wuyu_score>() {
                    @Override
                    public void invoke(Ana_wuyu_score data, AnalysisContext context) {
                        //一行一行
                        if (data == null){
//                        System.out.println("错误");
                            flag = 0;
                        }else {
                            wuyuScoreRepository.save(data);
                            flag = 1;
//                        System.out.println("完成");
                        }
                    }
                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {

                    }
                }).sheet().doRead();

            }catch (Exception e){
                flag = 0;
            }
        }
        return flag;
    }
}
