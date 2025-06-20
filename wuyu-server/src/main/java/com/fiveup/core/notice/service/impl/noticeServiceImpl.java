package com.fiveup.core.notice.service.impl;

import com.fiveup.core.excption.AppException;
import com.fiveup.core.notice.entity.NoticeEntity;
import com.fiveup.core.notice.entity.NoticeIdentityEntity;
import com.fiveup.core.notice.entity.UserIdentity;
import com.fiveup.core.notice.mapper.noticeMapper;
import com.fiveup.core.notice.service.noticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class noticeServiceImpl implements noticeService {

    @Resource
    private noticeMapper noticeMapper;

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
}
