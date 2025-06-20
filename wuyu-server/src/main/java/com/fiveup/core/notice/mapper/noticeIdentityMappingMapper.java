package com.fiveup.core.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.notice.entity.NoticeIdentityEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface noticeIdentityMappingMapper extends BaseMapper<NoticeIdentityEntity> {

    /**
     * 根据身份ID查询所有相关的公告ID
     * @param identityId 身份ID
     * @return 公告ID列表
     */
    @Select("SELECT notice_id FROM notice_identity_record WHERE identity_id = #{identityId}")
    List<Long> selectNoticeIdsByIdentityId(@Param("identityId") String identityId);
    @Select("SELECT count(*) FROM notice_identity_record WHERE identity_id = #{identityId}")
    int selectTotalCountByIdentityId(String identityId);
}
