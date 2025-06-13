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

//    @Override
//    public List<NoticeVO> getNoticeList(int userId) {
//        // 1. 获取所有公告
//        List<Notice> notices = noticeMapper.selectList(null);
//
//        // 2. 获取用户已读的公告ID列表
//        List<Long> readNoticeIds = noticeReadRecordMapper.selectReadNoticeIdsByUserId(userId);
//
//        // 3. 组装返回数据
//        return notices.stream().map(notice -> {
//            NoticeVO vo = new NoticeVO();
//            BeanUtils.copyProperties(notice, vo);
//            vo.setIsRead(readNoticeIds.contains(notice.getId()));
//            return vo;
//        }).collect(Collectors.toList());
//    }

//    @Override
//    public void addNotice(Notice notice) {
//        notice.setReleaseTime(LocalDateTime.now());
//        noticeMapper.insert(notice);
//    }
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
//    @Override
//    public List<NoticeVO> searchNotices(int userId, String keyword) {
//        // 1. 使用 MyBatis-Plus 的 LambdaQueryWrapper 构建搜索条件
//        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
//        wrapper.like(StringUtils.isNotBlank(keyword), Notice::getTheme, keyword)
//                .or()
//                .like(StringUtils.isNotBlank(keyword), Notice::getContent, keyword)
//                .orderByDesc(Notice::getReleaseTime);
//
//        // 2. 获取符合条件的公告
//        List<Notice> notices = noticeMapper.selectList(wrapper);
//
//        // 3. 获取用户已读的公告ID列表
//        List<Long> readNoticeIds = noticeReadRecordMapper.selectReadNoticeIdsByUserId(userId);
//
//        // 4. 组装返回数据
//        return notices.stream().map(notice -> {
//            NoticeVO vo = new NoticeVO();
//            BeanUtils.copyProperties(notice, vo);
//            vo.setIsRead(readNoticeIds.contains(notice.getId()));
//            return vo;
//        }).collect(Collectors.toList());
//    }
//@Override
//public PageResult<NoticeVO> getPaginatedNoticeList(NoticePageQuery query) {
//    // 1. 开启 PageHelper 分页
//    PageHelper.startPage(query.getPageNum(), query.getPageSize()); // 在查询之前调用
//
//    // 2. 构建查询条件 (MyBatis-Plus 的 LambdaQueryWrapper 仍然可以用于构建查询条件)
//    LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
//    wrapper.orderByDesc(Notice::getReleaseTime); // 默认按发布时间倒序
//
//    // 如果存在搜索关键词，则添加模糊查询条件
//    if (StringUtils.isNotBlank(query.getKeyword())) {
//        wrapper.like(Notice::getTheme, query.getKeyword())
//                .or()
//                .like(Notice::getContent, query.getKeyword());
//    }
//
//    // 如果指定了身份ID，则只查询该身份的公告
//    // 如果指定了身份ID，则通过 notice_identity_mapping 表查询相关公告ID
//    if (query.getIdentityId() != null) {
//        List<Long> noticeIds = noticeIdentityMappingMapper.selectNoticeIdsByIdentityId(query.getIdentityId());
//        if (noticeIds.isEmpty()) {
//            // 如果没有找到任何匹配的公告ID，直接返回空结果
//            PageResult<NoticeVO> emptyResult = new PageResult<>();
//            emptyResult.setTotal(0L);
//            emptyResult.setRecords(new java.util.ArrayList<>());
//            emptyResult.setPageNum(query.getPageNum());
//            emptyResult.setPageSize(query.getPageSize());
//            return emptyResult;
//        }
//        wrapper.in(Notice::getId, noticeIds); // 这一行是正确的，使用 Notice 的 ID 进行筛选
//    }
//    // 3. 执行查询。PageHelper 会自动拦截此查询并进行分页
//    List<Notice> notices = noticeMapper.selectList(wrapper);
//
//    // 4. 使用 PageInfo 封装分页结果
//    PageInfo<Notice> pageInfo = new PageInfo<>(notices);
//
//    // 5. 获取用户已读的公告ID列表
//    List<Long> readNoticeIds;
//    if (query.getUserId() != 0) {
//        readNoticeIds = noticeReadRecordMapper.selectReadNoticeIdsByUserId(query.getUserId());
//    } else {
//        readNoticeIds = new ArrayList<>();
//    }
//
//    // 6. 组装返回数据 (NoticeVO 列表)
//    List<NoticeVO> noticeVOs = notices.stream().map(notice -> {
//        NoticeVO vo = new NoticeVO();
//        BeanUtils.copyProperties(notice, vo);
//        vo.setIsRead(readNoticeIds.contains(notice.getId()));
//        return vo;
//    }).collect(Collectors.toList());
//
//    // 7. 构建 PageResult 返回给前端
//    PageResult<NoticeVO> result = new PageResult<>();
//    result.setTotal(pageInfo.getTotal());     // 从 PageInfo 获取总记录数
//    result.setRecords(noticeVOs);             // 当前页数据
//    result.setPageNum(pageInfo.getPageNum()); // 从 PageInfo 获取当前页码
//    result.setPageSize(pageInfo.getPageSize()); // 从 PageInfo 获取每页大小
//    return result;
//}
}
