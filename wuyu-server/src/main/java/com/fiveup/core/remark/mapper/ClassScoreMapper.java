package com.fiveup.core.remark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.remark.entity.classScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassScoreMapper extends BaseMapper<classScore> {
    @Select("SELECT * FROM fu_class_score WHERE class_ID=#{classId} AND data=#{data}")
    classScore getOneClassScore(int classId,int data);
}
