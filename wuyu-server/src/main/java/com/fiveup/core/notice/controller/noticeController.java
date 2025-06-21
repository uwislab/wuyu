package com.fiveup.core.notice.controller;

import com.fiveup.core.notice.dto.NoticePageQuery;
import com.fiveup.core.notice.entity.NoticeEntity;
import com.fiveup.core.notice.entity.UserIdentity;
import com.fiveup.core.notice.service.noticeService;
import com.fiveup.core.notice.vo.NoticeVO;
import com.fiveup.core.notice.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/read/{id}")
    public Map<String, Object> markAsRead(@PathVariable Long id, @RequestParam("userId") int userId) {
        noticeService.markAsRead(id, userId);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "标记成功");
        return result;
    }
    @GetMapping("/statistics")
    public Map<String, Object> getNoticeStatistics(@RequestParam int userId,@RequestParam String identityId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> statistics = noticeService.getNoticeStatistics(userId,identityId);
        result.put("code", 200);
        result.put("data", statistics);
        return result;
    }
    @GetMapping("/list") // 统一为获取分页公告列表接口，包含搜索功能
    public Map<String, Object> getNoticeList(NoticePageQuery query) {
        // 从 HttpSession 获取用户ID（如果 SecurityUtils 不可用）
        // Long userId = (Long) request.getSession().getAttribute("userId");
        // 或者通过其他方式获取当前登录用户ID并设置到 query 对象中
        // query.setUserId(userId);

        PageResult<NoticeVO> pageResult = noticeService.getPaginatedNoticeList(query);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", pageResult); // 返回 PageResult 对象
        return result;
    }

}
