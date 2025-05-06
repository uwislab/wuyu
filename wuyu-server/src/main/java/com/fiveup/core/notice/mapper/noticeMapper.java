package com.fiveup.core.notice.mapper;

import com.fiveup.core.notice.info.noticeInfo;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface noticeMapper {

    @Select("select * from notice_bord")
    List<noticeInfo> getNoticeList();

    @Delete("delete from notice_bord where id = #{id}")
    int deleteById(int id);

    @Insert("insert into notice_bord(release_time, theme, content) values (#{releaseTime}, #{theme}, #{content})")
    int addList(noticeInfo info);
}
