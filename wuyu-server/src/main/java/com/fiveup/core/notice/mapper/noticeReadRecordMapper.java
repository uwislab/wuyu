package com.fiveup.core.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.notice.entity.NoticeReadRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface noticeReadRecordMapper extends BaseMapper<NoticeReadRecord> {
    // 查询用户已读的公告ID列表
    @Select("SELECT notice_id FROM notice_read_record WHERE user_id = #{userId}")
    List<Long> selectReadNoticeIdsByUserId(@Param("userId") int userId);

    // 查询特定公告和用户的阅读记录
    @Select("SELECT * FROM notice_read_record WHERE notice_id = #{noticeId} AND user_id = #{userId}")
    NoticeReadRecord selectByNoticeIdAndUserId(@Param("noticeId") Long noticeId, @Param("userId") int userId);

    @Select("select count(*) from notice_read_record WHERE user_id = #{userId}")
    int selectCountReadNotice(int userId);
}
