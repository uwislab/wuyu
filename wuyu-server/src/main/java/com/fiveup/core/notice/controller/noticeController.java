package com.fiveup.core.notice.controller;

import com.fiveup.core.notice.info.noticeInfo;
import com.fiveup.core.notice.service.noticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class noticeController {

    @Autowired
    private noticeService noticeService;

    @RequestMapping("/getNoticeList")
    public List<noticeInfo> getNoticeList(){
        List<noticeInfo> list = noticeService.getNoticeList();
        return list;
    }

    @RequestMapping("/deleteById/{id}")
    public int deleteById(@PathVariable int id){
        return noticeService.deleteById(id);
    }

    @PostMapping("/addList")
    public int addList(@RequestBody noticeInfo noticeInfo){
        LocalDate localDate = LocalDate.now();
        noticeInfo.setReleaseTime(localDate);
        System.out.println(noticeInfo);
        return noticeService.addList(noticeInfo);
    }
}
