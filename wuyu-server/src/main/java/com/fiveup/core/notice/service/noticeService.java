package com.fiveup.core.notice.service;

import com.fiveup.core.notice.info.noticeInfo;

import java.util.List;

public interface noticeService {
    List<noticeInfo> getNoticeList();

    int deleteById(int id);

    int addList(noticeInfo noticeInfo);
}
