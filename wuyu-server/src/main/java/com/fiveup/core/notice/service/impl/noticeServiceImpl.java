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
import lombok.Data;
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
import java.util.*;
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
        }

        log.info("公告添加成功 | ID[{}] 耗时[{}ms]",
                noticeEntity.getId(),
                System.currentTimeMillis() - startTime);
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
        // 构建查询条件 (MyBatis-Plus 的 LambdaQueryWrapper)
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
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
        // 开启 PageHelper 分页
        PageHelper.startPage(queryParams.getPageNum(), queryParams.getPageSize());
        // 默认按发布时间倒序
        queryWrapper.orderByDesc(Notice::getReleaseTime);

        // 如果存在搜索关键词，则添加模糊查询条件
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(queryParams.getKeyword())) {
            queryWrapper.like(Notice::getTheme, queryParams.getKeyword())
                    .or()
                    .like(Notice::getContent, queryParams.getKeyword());
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void markAsRead(Long noticeId, int userId) {
        long startTime = System.currentTimeMillis();
        String operationId = generateOperationId();

        log.info("开始标记公告已读 | 操作ID[{}] 公告ID[{}] 用户ID[{}]",
                operationId, noticeId, userId);

        try {
            // 1. 参数校验
            if (noticeId == null || noticeId <= 0) {
                log.error("标记已读失败 | 操作ID[{}] 公告ID无效: {}", operationId, noticeId);
                throw new IllegalArgumentException("公告ID不能为空或无效");
            }

            if (userId <= 0) {
                log.error("标记已读失败 | 操作ID[{}] 用户ID无效: {}", operationId, userId);
                throw new IllegalArgumentException("用户ID无效");
            }

            // 2. 验证公告是否存在
            Notice notice = noticeMapper.selectById(noticeId);
            if (notice == null) {
                log.error("标记已读失败 | 操作ID[{}] 公告不存在: {}", operationId, noticeId);
                throw new AppException("公告不存在");
            }

            // 3. 检查是否已读（避免重复操作）
            NoticeReadRecord existingRecord = noticeReadRecordMapper.selectByNoticeIdAndUserId(noticeId, userId);
            if (existingRecord != null) {
                log.info("公告已标记为已读 | 操作ID[{}] 公告ID[{}] 用户ID[{}] 阅读时间[{}]",
                        operationId, noticeId, userId, existingRecord.getReadTime());
                return;
            }

            // 4. 创建阅读记录
            NoticeReadRecord newRecord = new NoticeReadRecord();
            newRecord.setNoticeId(noticeId);
            newRecord.setUserId(userId);
            newRecord.setReadTime(LocalDateTime.now());

            // 5. 数据库操作
            int insertResult = noticeReadRecordMapper.insert(newRecord);
            if (insertResult <= 0) {
                log.error("标记已读失败 | 操作ID[{}] 数据库插入失败", operationId);
                throw new ServiceException("标记已读失败，请稍后重试");
            }

            // 6. 记录操作日志
            logOperation(operationId, "MARK_AS_READ", noticeId, userId, "SUCCESS");

            log.info("标记已读成功 | 操作ID[{}] 公告ID[{}] 用户ID[{}] 耗时[{}ms]",
                    operationId, noticeId, userId, System.currentTimeMillis() - startTime);

        } catch (DuplicateKeyException e) {
            log.error("标记已读失败 | 操作ID[{}] 重复标记: {}", operationId, e.getMessage());
            throw new AppException("该公告已被标记为已读");
        } catch (DataAccessException e) {
            log.error("标记已读失败 | 操作ID[{}] 数据库访问异常: {}", operationId, e.getMessage());
            throw new ServiceException("数据库服务异常，请稍后重试");
        } catch (AppException e) {
            log.error("标记已读失败 | 操作ID[{}] 业务异常: {}", operationId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("标记已读失败 | 操作ID[{}] 未知异常: {}", operationId, e.getMessage(), e);
            throw new ServiceException("系统内部错误，请稍后重试");
        } finally {
            // 7. 清理临时资源
            cleanupTemporaryResources(operationId);
        }
    }

    @Override
    public Map<String, Object> getNoticeStatistics(int userId, String identityId) {
        long startTime = System.currentTimeMillis();
        String operationId = generateOperationId();

        log.info("开始获取公告统计 | 操作ID[{}] 用户ID[{}] 身份ID[{}]",
                operationId, userId, identityId);

        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 参数校验
            if (userId <= 0) {
                log.error("获取统计失败 | 操作ID[{}] 用户ID无效: {}", operationId, userId);
                throw new IllegalArgumentException("用户ID无效");
            }

            if (StringUtils.isBlank(identityId)) {
                log.error("获取统计失败 | 操作ID[{}] 身份ID为空", operationId);
                throw new IllegalArgumentException("身份ID不能为空");
            }

            // 2. 验证用户身份
            validateUserIdentity(userId, identityId);

            // 3. 查询统计数据
            StatisticsData statisticsData = queryStatisticsData(userId, identityId);

            // 4. 计算衍生数据
            calculateDerivedStatistics(statisticsData);

            // 5. 构建返回结果
            result.put("readCount", statisticsData.getReadCount());
            result.put("unreadCount", statisticsData.getUnreadCount());
            result.put("totalCount", statisticsData.getTotalCount());
            result.put("readRate", statisticsData.getReadRate());
            result.put("lastReadTime", statisticsData.getLastReadTime());
            result.put("statisticsTime", LocalDateTime.now());

            // 6. 记录操作日志
            logOperation(operationId, "GET_STATISTICS", null, userId, "SUCCESS");

            log.info("获取统计成功 | 操作ID[{}] 用户ID[{}] 已读[{}] 未读[{}] 总计[{}] 耗时[{}ms]",
                    operationId, userId, statisticsData.getReadCount(),
                    statisticsData.getUnreadCount(), statisticsData.getTotalCount(),
                    System.currentTimeMillis() - startTime);

        } catch (IllegalArgumentException e) {
            log.error("获取统计失败 | 操作ID[{}] 参数错误: {}", operationId, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("获取统计失败 | 操作ID[{}] 数据库访问异常: {}", operationId, e.getMessage());
            throw new ServiceException("数据服务异常，请稍后重试");
        } catch (Exception e) {
            log.error("获取统计失败 | 操作ID[{}] 未知异常: {}", operationId, e.getMessage(), e);
            throw new ServiceException("系统内部错误，请稍后重试");
        }

        return result;
    }

    // 辅助方法：生成操作ID
    private String generateOperationId() {
        return "OP_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
    }

    // 记录操作日志
    private void logOperation(String operationId, String operation, Long noticeId, int userId, String status) {
        try {
            // 这里可以记录到数据库或日志文件
            log.info("操作日志 | ID[{}] 操作[{}] 公告ID[{}] 用户ID[{}] 状态[{}] 时间[{}]",
                    operationId, operation, noticeId, userId, status, LocalDateTime.now());
        } catch (Exception e) {
            log.warn("记录操作日志失败: {}", e.getMessage());
        }
    }

    // 清理临时资源
    private void cleanupTemporaryResources(String operationId) {
        try {
            // 清理临时文件、缓存等资源
            log.debug("清理临时资源 | 操作ID[{}]", operationId);
        } catch (Exception e) {
            log.warn("清理临时资源失败 | 操作ID[{}] 错误: {}", operationId, e.getMessage());
        }
    }

    // 验证用户身份
    private void validateUserIdentity(int userId, String identityId) {
        try {
            // 这里可以添加用户身份验证逻辑
            log.debug("验证用户身份 | 用户ID[{}] 身份ID[{}]", userId, identityId);
        } catch (Exception e) {
            log.warn("用户身份验证失败 | 用户ID[{}] 身份ID[{}] 错误: {}",
                    userId, identityId, e.getMessage());
            throw new AppException("用户身份验证失败");
        }
    }

    // 查询统计数据
    private StatisticsData queryStatisticsData(int userId, String identityId) {
        StatisticsData data = new StatisticsData();

        try {
            // 查询该用户已读数量
            data.setReadCount(noticeReadRecordMapper.selectCountReadNotice(userId));

            // 查询属于该用户身份总的通知数量
            data.setTotalCount(noticeIdentityMappingMapper.selectTotalCountByIdentityId(identityId));

            // 计算未读数量
            data.setUnreadCount(data.getTotalCount() - data.getReadCount());

            // 查询最后阅读时间
            data.setLastReadTime(queryLastReadTime(userId));

            log.debug("查询统计数据成功 | 用户ID[{}] 已读[{}] 总计[{}] 未读[{}]",
                    userId, data.getReadCount(), data.getTotalCount(), data.getUnreadCount());

        } catch (Exception e) {
            log.error("查询统计数据失败 | 用户ID[{}] 错误: {}", userId, e.getMessage());
            throw new ServiceException("查询统计数据失败");
        }

        return data;
    }

    // 计算已读未读数量统计
    private void calculateDerivedStatistics(StatisticsData data) {
        try {
            if (data.getTotalCount() > 0) {
                data.setReadRate((double) data.getReadCount() / data.getTotalCount());
            } else {
                data.setReadRate(0.0);
            }

            log.debug("计算已读未读数量统计 | 阅读率[{}%]", data.getReadRate() * 100);

        } catch (Exception e) {
            log.warn("计算已读未读数量统计: {}", e.getMessage());
            data.setReadRate(0.0);
        }
    }

    // 查询最后阅读时间
    private LocalDateTime queryLastReadTime(int userId) {
        try {
            // 这里可以添加查询最后阅读时间的逻辑
            return LocalDateTime.now();
        } catch (Exception e) {
            log.warn("查询最后阅读时间失败 | 用户ID[{}] 错误: {}", userId, e.getMessage());
            return null;
        }
    }

    // 统计数据内部类
    @Data
    private static class StatisticsData {
        private int readCount;
        private int unreadCount;
        private int totalCount;
        private double readRate;
        private LocalDateTime lastReadTime;
    }
}
