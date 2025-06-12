package com.fiveup.core.notice.mapper;

import com.fiveup.core.notice.enetity.NoticeEntity;
import com.fiveup.core.notice.enetity.NoticeIdentityEntity;
import com.fiveup.core.notice.enetity.UserIdentity;
import com.fiveup.core.notice.info.noticeInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface noticeMapper {

    @Select("select * from notice_bord")
    List<noticeInfo> getNoticeList();

    @Delete("delete from notice_bord where id = #{id}")
    int deleteById(int id);

    @Insert("insert into notice_bord(release_time, theme, content) values (#{releaseTime}, #{theme}, #{content})")
    int addList(noticeInfo info);
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into notice (theme,content,release_time,is_important,created_by) " +
            "values (#{theme},#{content},#{releaseTime},#{isImportant},#{createdBy})")
    void insertNotice(NoticeEntity noticeEntity);

    @Insert("insert into notice_identity_record (notice_id,identity_id) values (#{noticeId},#{identityId})")
    void insertNoticeIdentityRecord(NoticeIdentityEntity entity);

    @Select("select identity_id,identity_info from basic_user_identity")
    List<UserIdentity> getIdentityIds();

}
