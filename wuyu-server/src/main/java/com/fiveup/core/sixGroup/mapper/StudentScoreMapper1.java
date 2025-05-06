package com.fiveup.core.sixGroup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.sixGroup.entity.FuStudentScore;
import com.fiveup.core.sixGroup.entity.StudentScoreQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentScoreMapper1 extends BaseMapper<FuStudentScore> {
    public List<FuStudentScore> getStudentScore(StudentScoreQuery q);
}
