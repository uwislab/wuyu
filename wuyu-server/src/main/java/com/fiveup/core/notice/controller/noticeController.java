package com.fiveup.core.notice.controller;

import com.fiveup.core.notice.entity.NoticeEntity;
import com.fiveup.core.notice.entity.UserIdentity;
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

    @PostMapping("/add")
    public Map<String, Object> addNotice(@RequestBody NoticeEntity noticeEntity) {
        noticeService.addNotice(noticeEntity);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "添加成功");
        return result;
    }
    @GetMapping("/getIdentityIds")
    public Map<String, Object> getIdentityIds(){
        Map<String, Object> result = new HashMap<>();
        List<UserIdentity> identityIds = noticeService.getIdentityIds();
        result.put("code", 200);
        result.put("data",identityIds);
        return result;
    }

}
