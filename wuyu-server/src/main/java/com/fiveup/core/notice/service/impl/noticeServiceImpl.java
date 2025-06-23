package com.fiveup.core.notice.service.impl;

import com.fiveup.core.notice.info.noticeInfo;
import com.fiveup.core.notice.mapper.noticeMapper;
import com.fiveup.core.notice.service.noticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
