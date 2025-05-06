package com.fiveup.core.remark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.remark.entity.studentInfo;
import com.fiveup.core.remark.entity.studentAllInfo;
import com.fiveup.core.remark.entity.studentScore;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface studentBZinfoMapper extends BaseMapper<studentInfo> {
    //  根据学生学号从学生表中取出所有信息
    @Select("select * FROM fu_student_score where student_num=#{studentNum}")
    public studentInfo findstuInfo(Integer studentNum);

}