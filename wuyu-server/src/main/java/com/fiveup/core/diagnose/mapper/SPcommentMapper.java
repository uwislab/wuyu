package com.fiveup.core.diagnose.mapper;


import com.fiveup.core.diagnose.bean.student_Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface SPcommentMapper {

    /*新增或更新评价*/
    @Insert("insert into di_studentplancomment(p_id,comment,update_time) values(#{id},#{comment},#{updateTime})")
    public void InsertComment(Long id, String comment, LocalDateTime updateTime);

    @Select("select p_id,comment,update_time from di_studentplancomment where p_id = #{id}")
    student_Comment getComment(Long id);

    @Select("select p_id,comment,update_time from di_studentplancomment where p_id = #{id}")
    student_Comment getCommentByid(Long id);
    @Update("update di_studentplancomment set comment=#{comment},update_time=#{updateTime} where p_id=#{id}")
    void updateComment(Long id, String comment, LocalDateTime updateTime);
}
