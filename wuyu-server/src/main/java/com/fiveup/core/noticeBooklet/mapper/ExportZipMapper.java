package com.fiveup.core.noticeBooklet.mapper;

import com.fiveup.core.noticeBooklet.domain.vo.NoticeBookletVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExportZipMapper {
    /**
     * 获取学生通知簿
     * @param ids 学生ids集合
     * @return 通知簿（学生基本信息、成绩单、评语、建议、假期要求）
     */

    List<NoticeBookletVo> getByIds(List<Long> ids);
}
