package com.fiveup.core.notice.controller;

import com.fiveup.core.notice.enetity.NoticeEntity;
import com.fiveup.core.notice.info.noticeInfo;
import com.fiveup.core.notice.service.noticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notice")
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
    @PostMapping("/add")
    public Map<String, Object> addNotice(@RequestBody NoticeEntity noticeEntity) {
        noticeService.addNotice(noticeEntity);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "添加成功");
        return result;
    }
}
