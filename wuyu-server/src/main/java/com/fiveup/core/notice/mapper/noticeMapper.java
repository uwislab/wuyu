package com.fiveup.core.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.notice.entity.Notice;
import com.fiveup.core.notice.entity.NoticeEntity;
import com.fiveup.core.notice.entity.NoticeIdentityEntity;
import com.fiveup.core.notice.entity.UserIdentity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    // 基础CRUD方法由MyBatis-Plus提供
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into notice (theme,content,release_time,is_important,created_by) " +
            "values (#{theme},#{content},#{releaseTime},#{isImportant},#{createdBy})")
    void insertNotice(NoticeEntity noticeEntity);

    @Insert("insert into notice_identity_record (notice_id,identity_id) values (#{noticeId},#{identityId})")
    void insertNoticeIdentityRecord(NoticeIdentityEntity entity);

    @Select("select identity_id,identity_info from basic_user_identity")
    List<UserIdentity> getIdentityIds();
}
