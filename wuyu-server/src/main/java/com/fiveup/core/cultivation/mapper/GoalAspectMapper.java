package com.fiveup.core.cultivation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.cultivation.entity.GoalAspect;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Harvi
 */
@Mapper
@Component
public interface GoalAspectMapper extends BaseMapper<GoalAspect> { }
