package com.fiveup.core.notice.service.impl;

import com.fiveup.core.notice.enetity.NoticeEntity;
import com.fiveup.core.notice.enetity.NoticeIdentityEntity;
import com.fiveup.core.notice.enetity.UserIdentity;
import com.fiveup.core.notice.info.noticeInfo;
import com.fiveup.core.notice.mapper.noticeMapper;
import com.fiveup.core.notice.service.noticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class noticeServiceImpl implements noticeService {

    @Autowired
    private noticeMapper noticeMapper;

    @Override
    public List<noticeInfo> getNoticeList() {
        return noticeMapper.getNoticeList();
    }

    @Override
    public int deleteById(int id) {
        return noticeMapper.deleteById(id);
    }

    @Override
    public int addList(noticeInfo noticeInfo) {
        System.out.println(noticeInfo);
        return noticeMapper.addList(noticeInfo);
    }
    @Transactional
    @Override
    public void addNotice(NoticeEntity noticeEntity) {
        noticeEntity.setReleaseTime(LocalDateTime.now());
        noticeMapper.insertNotice(noticeEntity);

        String noticeId = noticeEntity.getId();
        String identityId = noticeEntity.getIdentityId();
        NoticeIdentityEntity entity = new NoticeIdentityEntity();
        entity.setNoticeId(noticeId);
        entity.setIdentityId(identityId);

        noticeMapper.insertNoticeIdentityRecord(entity);
    }

    @Override
    public List<UserIdentity> getIdentityIds() {
        return noticeMapper.getIdentityIds();
    }
}
