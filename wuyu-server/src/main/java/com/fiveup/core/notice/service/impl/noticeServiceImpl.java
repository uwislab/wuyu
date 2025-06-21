package com.fiveup.core.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveup.core.excption.AppException;
import com.fiveup.core.notice.dto.NoticePageQuery;
import com.fiveup.core.notice.entity.*;
import com.fiveup.core.notice.mapper.noticeIdentityMappingMapper;
import com.fiveup.core.notice.mapper.noticeMapper;
import com.fiveup.core.notice.mapper.noticeMapperExtendBaseMapper;
import com.fiveup.core.notice.mapper.noticeReadRecordMapper;
import com.fiveup.core.notice.service.noticeService;
import com.fiveup.core.notice.vo.NoticeVO;
import com.fiveup.core.notice.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class noticeServiceImpl implements noticeService {

    @Autowired
    private noticeReadRecordMapper noticeReadRecordMapper;
    @Autowired
    private noticeIdentityMappingMapper noticeIdentityMappingMapper;
    @Resource
    private noticeMapper noticeMapper;
    @Autowired
    private noticeMapperExtendBaseMapper noticeMapperExtendBaseMapper;

    @Resource
    private RedissonClient redissonClient;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addNotice(NoticeEntity noticeEntity) {
        long startTime = System.currentTimeMillis();
        // 1. 增加参数校验层
        if (noticeEntity == null) {
            log.error("添加公告失败: 参数不能为空");
            throw new IllegalArgumentException("公告参数不可为空");
        }

        log.info("添加公告开始 | 标题[{}] 发布人[{}]",
                noticeEntity.getTheme(),
                noticeEntity.getCreatedBy());

        // 2. 设置扩展属性
        noticeEntity.setReleaseTime(LocalDateTime.now());
        try {
            // 3. 主记录插入前置校验
            if (StringUtils.isBlank(noticeEntity.getContent())) {
                log.warn("公告内容为空 | ID[{}]", noticeEntity.getId());
                noticeEntity.setContent("暂无内容");
            }

            // 4. 主记录插入
            noticeMapper.insertNotice(noticeEntity);
            String noticeId = noticeEntity.getId();
            log.debug("公告主记录插入成功 | ID[{}]", noticeId);

            // 5. 关联记录处理（增强逻辑）
            String identityId = noticeEntity.getIdentityId();
            if (StringUtils.isBlank(identityId)) {
                log.error("身份标识缺失 | 公告ID[{}]", noticeId);
                throw new AppException("身份标识不能为空");
            }

            NoticeIdentityEntity entity = new NoticeIdentityEntity();
            entity.setNoticeId(noticeId);
            entity.setIdentityId(identityId);
            // 6. 关联记录插入
            noticeMapper.insertNoticeIdentityRecord(entity);
            log.info("公告关联记录创建 | 公告ID[{}] 身份ID[{}]", noticeId, identityId);

            //updateCache(noticeId);           // 模拟缓存更新

        } catch (DuplicateKeyException e) {
            log.error("公告重复插入 | 标题[{}] 错误信息: {}",
                    noticeEntity.getTheme(), e.getMessage());
            throw new AppException("公告已存在", e);
        } catch (DataAccessException e) {
            log.error("数据库访问异常 | 错误详情: ", e);
            throw new ServiceException("数据服务异常", e);
        } catch (Exception e) {
            log.error("添加公告未知错误 | 实体: {} | 异常: ",
                    noticeEntity, e);
            throw new ServiceException("系统内部错误");
        } finally {
            // 8. 资源清理（示例）
            //clearTemporaryResources(noticeEntity);
        }

        log.info("公告添加成功 | ID[{}] 耗时[{}ms]",
                noticeEntity.getId(),
                System.currentTimeMillis() - startTime);
    }


    private void updateCache(String noticeId) {
//        try {
//            // 模拟缓存更新
//            redissonClient.refresh(CacheRegion.NOTICE, noticeId);
//        } catch (CacheException e) {
//            log.warn("公告缓存更新失败 | ID[{}] {}", noticeId, e.getMessage());
//        }
    }

    private void clearTemporaryResources(NoticeEntity entity) {
//        // 模拟资源清理
//        if (entity.getAttachments() != null) {
//            tempFileCleaner.clean(entity.getAttachments());
//        }
    }

    @Override
    public List<UserIdentity> getIdentityIds() {
        log.info("查询发放对象开始");
        try {
            return noticeMapper.getIdentityIds();
        }catch (Exception e){
            log.info("查询出现异常");
            throw new AppException("查询异常");
        }
    }

    @Override
    public PageResult<NoticeVO> getPaginatedNoticeList(NoticePageQuery queryParams) {
        // 开启 PageHelper 分页
        PageHelper.startPage(queryParams.getPageNum(), queryParams.getPageSize());

        // 构建查询条件 (MyBatis-Plus 的 LambdaQueryWrapper)
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        // 默认按发布时间倒序
        queryWrapper.orderByDesc(Notice::getReleaseTime);

        // 如果存在搜索关键词，则添加模糊查询条件
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(queryParams.getKeyword())) {
            queryWrapper.like(Notice::getTheme, queryParams.getKeyword())
                    .or()
                    .like(Notice::getContent, queryParams.getKeyword());
        }

        // 如果指定了身份ID，则通过 notice_identity_mapping 表查询相关公告ID
        if (queryParams.getIdentityId() != null) {
            List<Long> filteredNoticeIds = noticeIdentityMappingMapper.selectNoticeIdsByIdentityId(queryParams.getIdentityId());
            // 如果没有找到任何匹配的公告ID，直接返回空结果
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

        //执行查询。PageHelper 会自动拦截此查询并进行分页
        List<Notice> noticeList = noticeMapperExtendBaseMapper.selectList(queryWrapper);

        //使用 PageInfo 封装分页结果
        PageInfo<Notice> pageInfo = new PageInfo<>(noticeList);

        //获取用户已读的公告ID列表
        List<Long> readNoticeIds = (queryParams.getUserId() != 0) ?
                noticeReadRecordMapper.selectReadNoticeIdsByUserId(queryParams.getUserId()) :
                new ArrayList<>();

        //组装返回数据 (NoticeVO 列表)
        List<NoticeVO> noticeVOList = noticeList.stream().map(notice -> {
            NoticeVO noticeVO = new NoticeVO();
            BeanUtils.copyProperties(notice, noticeVO);
            noticeVO.setIsRead(readNoticeIds.contains(notice.getId())); // Set read status based on user data
            return noticeVO;
        }).collect(Collectors.toList());

        //构建 PageResult 返回给前端
        PageResult<NoticeVO> resultPage = new PageResult<>();
        // 从 PageInfo 获取总记录数
        resultPage.setTotal(pageInfo.getTotal());
        // 当前页数据
        resultPage.setRecords(noticeVOList);
        // 从 PageInfo 获取当前页码
        resultPage.setPageNum(pageInfo.getPageNum());
        // 从 PageInfo 获取每页大小
        resultPage.setPageSize(pageInfo.getPageSize());
        return resultPage;
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
    public Map<String, Object> getNoticeStatistics(int userId, String identityId) {
        Map<String, Object> result = new HashMap<>();
        // 查询该用户已读数量
        int readCount = noticeReadRecordMapper.selectCountReadNotice(userId);
        // 查询属于该用户身份总的通知数量
        int totalCount = noticeIdentityMappingMapper.selectTotalCountByIdentityId(identityId);
        // 计算未读数量
        result.put("readCount", readCount);
        result.put("unreadCount", totalCount-readCount);
        return result;
    }

}
