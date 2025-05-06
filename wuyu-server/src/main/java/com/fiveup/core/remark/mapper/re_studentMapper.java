package com.fiveup.core.remark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.remark.entity.studentScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper //不是我们需要用的数据
public interface re_studentMapper extends BaseMapper<studentScore> {
    //  根据学生学号从学生表中取出所有信息
    @Select("select * FROM fu_student_score where student_num=#{studentNum}")
    public studentScore findstumsg(Integer studentNum);
}
