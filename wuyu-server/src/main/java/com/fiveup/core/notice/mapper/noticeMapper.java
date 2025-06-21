package com.fiveup.core.notice.mapper;

import com.fiveup.core.notice.entity.Notice;
import com.fiveup.core.notice.entity.NoticeEntity;
import com.fiveup.core.notice.entity.NoticeIdentityEntity;
import com.fiveup.core.notice.entity.UserIdentity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface noticeMapper {
    /**
     * 添加公告
     * @param noticeEntity
     */
    void insertNotice(NoticeEntity noticeEntity);

    /**
     * 增加公告与发放对象关联记录
     * @param entity
     */
    void insertNoticeIdentityRecord(NoticeIdentityEntity entity);

    /**
     *
     * @return 获取发放对象集合列表
     */
    List<UserIdentity> getIdentityIds();

    Notice selectById(Long noticeId);
}
