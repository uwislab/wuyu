package com.fiveup.core.notice.service;

import com.fiveup.core.notice.entity.NoticeEntity;
import com.fiveup.core.notice.entity.UserIdentity;

import java.util.List;

public interface NoticeService {
    //List<NoticeVO> getNoticeList(int userId);
    void addNotice(NoticeEntity noticeEntity);
    void deleteNotice(int id);
    void markAsRead(Long noticeId, int userId);
    // 统一的分页查询方法，同时处理搜索功能
    //PageResult<NoticeVO> getPaginatedNoticeList(NoticePageQuery query);
    //List<NoticeVO> searchNotices(int userId, String keyword); // 新增搜索方法
    List<UserIdentity> getIdentityIds();//拿到用户身份


}
