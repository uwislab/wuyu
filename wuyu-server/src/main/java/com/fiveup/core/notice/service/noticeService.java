package com.fiveup.core.notice.service;

import com.fiveup.core.notice.dto.NoticePageQuery;
import com.fiveup.core.notice.entity.NoticeEntity;
import com.fiveup.core.notice.entity.UserIdentity;
import com.fiveup.core.notice.vo.NoticeVO;
import com.fiveup.core.notice.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface noticeService {

    void addNotice(NoticeEntity noticeEntity);

    List<UserIdentity> getIdentityIds();

    public PageResult<NoticeVO> getPaginatedNoticeList(NoticePageQuery queryParams);
    void markAsRead(Long noticeId, int userId);
    // 统一的分页查询方法，同时处理搜索功能

    Map<String, Object> getNoticeStatistics(int userId, String identityId);//统计未读和已读公告数量

}
