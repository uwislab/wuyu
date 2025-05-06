package com.fiveup.core.sixGroup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.sixGroup.entity.IndexScoreQuery;
import com.fiveup.core.sixGroup.entity.Ret01;
import com.fiveup.core.sixGroup.entity.SixStuIcScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StuIcScoreMapper extends BaseMapper<SixStuIcScore> {
    public List<Ret01> listData(IndexScoreQuery q);
}
