package com.fiveup.core.remark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.remark.entity.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface feedbackMapper extends BaseMapper<feedback>{
//    根据学生的学号从feedback表中取出所有信息
    @Select("select * FROM re_feedback where sid=#{Sid}")
    public feedback findfeedback(Integer Sid);
//    根据学生的学号修改该学生的反馈信息
    @Update("UPDATE re_feedback SET rate=Rate,fobject=Fobject,hobject=Hobject,feedback=feedback WHERE sid=Sid")
    public void updatefeedback(@Param("Sid") Integer Sid,@Param("Rate") Integer Rate,@Param("Fobject") String Fobject,@Param("Hobject") String Hobject,@Param("Feedback") String Feedback);
}
