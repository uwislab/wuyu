package com.fiveup.core.notice.controller;

import com.fiveup.core.notice.info.noticeInfo;
import com.fiveup.core.notice.service.noticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/notice")
public class noticeController {

//    private final ChatClient chatClient;

    @Autowired
    private noticeService noticeService;

    // 注意看返回值，是Flux<String>，也就是流式结果，另外需要设定响应类型和编码，不然前端会乱码
//    @RequestMapping(value = "/chat", produces = "text/html;charset=UTF-8")
//    public Flux<String> chat(@RequestParam(defaultValue = "讲个笑话") String prompt) {
//        return chatClient
//                .prompt(prompt)
//                .stream() // 流式调用
//                .content();
//    }

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
