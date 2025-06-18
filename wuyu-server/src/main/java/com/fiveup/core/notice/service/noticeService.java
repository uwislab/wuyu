package com.fiveup.core.notice.service;

import com.fiveup.core.notice.entity.NoticeEntity;
import com.fiveup.core.notice.entity.UserIdentity;
import com.fiveup.core.notice.info.noticeInfo;

import java.util.List;

public interface noticeService {

    void addNotice(NoticeEntity noticeEntity);

    List<UserIdentity> getIdentityIds();

}
