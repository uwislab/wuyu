package com.fiveup.core.notice.service;

import com.fiveup.core.notice.dto.NoticePageQuery;
import com.fiveup.core.notice.entity.Notice;
import com.fiveup.core.notice.vo.NoticeVO;
import com.fiveup.core.notice.vo.PageResult;

public interface NoticeService {
    //List<NoticeVO> getNoticeList(int userId);
    void addNotice(Notice notice);
    void deleteNotice(int id);
    void markAsRead(Long noticeId, int userId);
    // 统一的分页查询方法，同时处理搜索功能
    PageResult<NoticeVO> getPaginatedNoticeList(NoticePageQuery query);
    //List<NoticeVO> searchNotices(int userId, String keyword); // 新增搜索方法
}
