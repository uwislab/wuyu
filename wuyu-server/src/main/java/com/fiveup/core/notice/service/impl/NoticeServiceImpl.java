package com.fiveup.core.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fiveup.core.notice.dto.NoticePageQuery;
import com.fiveup.core.notice.entity.*;
import com.fiveup.core.notice.mapper.NoticeIdentityMappingMapper;
import com.fiveup.core.notice.mapper.NoticeMapper;
import com.fiveup.core.notice.mapper.NoticeReadRecordMapper;
import com.fiveup.core.notice.service.NoticeService;
import com.fiveup.core.notice.vo.NoticeVO;
import com.fiveup.core.notice.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeReadRecordMapper noticeReadRecordMapper;

    @Autowired
    private NoticeIdentityMappingMapper noticeIdentityMappingMapper; // 注入新增的 Mapper

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

    @Override
    public void deleteNotice(int id) {
        // 删除公告
        noticeMapper.deleteById(id);
        // 删除相关的阅读记录
        noticeReadRecordMapper.delete(new QueryWrapper<NoticeReadRecord>()
                .eq("notice_id", id));
    }

    @Override
    public void markAsRead(Long noticeId, int userId) {
        // 检查是否已读
        NoticeReadRecord record = noticeReadRecordMapper.selectByNoticeIdAndUserId(noticeId, userId);
        if (record != null) {
            return;
        }
        // 创建阅读记录
        NoticeReadRecord newRecord = new NoticeReadRecord();
        newRecord.setNoticeId(noticeId);
        newRecord.setUserId(userId);
        noticeReadRecordMapper.insert(newRecord);
    }

    @Override
    public PageResult<NoticeVO> getPaginatedNoticeList(NoticePageQuery queryParams) {
        PageHelper.startPage(queryParams.getPageNum(), queryParams.getPageSize());

        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Notice::getReleaseTime);

        if (StringUtils.isNotBlank(queryParams.getKeyword())) {
            queryWrapper.like(Notice::getTheme, queryParams.getKeyword())
                    .or()
                    .like(Notice::getContent, queryParams.getKeyword());
        }

        if (queryParams.getIdentityId() != null) {
            List<Long> filteredNoticeIds = noticeIdentityMappingMapper.selectNoticeIdsByIdentityId(queryParams.getIdentityId());
            if (filteredNoticeIds.isEmpty()) {
                PageResult<NoticeVO> emptyResult = new PageResult<>();
                emptyResult.setTotal(0L);
                emptyResult.setRecords(new ArrayList<>());
                emptyResult.setPageNum(queryParams.getPageNum());
                emptyResult.setPageSize(queryParams.getPageSize());
                return emptyResult;
            }
            queryWrapper.in(Notice::getId, filteredNoticeIds);
        }

        List<Notice> noticeList = noticeMapper.selectList(queryWrapper);

        PageInfo<Notice> pageInfo = new PageInfo<>(noticeList);

        List<Long> readNoticeIds = (queryParams.getUserId() != 0) ?
                noticeReadRecordMapper.selectReadNoticeIdsByUserId(queryParams.getUserId()) :
                new ArrayList<>();

        List<NoticeVO> noticeVOList = noticeList.stream().map(notice -> {
            NoticeVO noticeVO = new NoticeVO();
            BeanUtils.copyProperties(notice, noticeVO);
            noticeVO.setIsRead(readNoticeIds.contains(notice.getId())); // Set read status based on user data
            return noticeVO;
        }).collect(Collectors.toList());

        PageResult<NoticeVO> resultPage = new PageResult<>();
        resultPage.setTotal(pageInfo.getTotal());
        resultPage.setRecords(noticeVOList);
        resultPage.setPageNum(pageInfo.getPageNum());
        resultPage.setPageSize(pageInfo.getPageSize());
        return resultPage;
    }

}
