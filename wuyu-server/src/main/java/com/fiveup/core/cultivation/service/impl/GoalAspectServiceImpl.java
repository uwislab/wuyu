package com.fiveup.core.cultivation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.cultivation.entity.GoalAspect;
import com.fiveup.core.cultivation.mapper.GoalAspectMapper;
import com.fiveup.core.cultivation.service.GoalAspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvi
 */
@Service
public class GoalAspectServiceImpl extends ServiceImpl<GoalAspectMapper, GoalAspect> implements GoalAspectService {
    private final GoalAspectMapper goalAspectMapper;

    @Autowired
    public GoalAspectServiceImpl(GoalAspectMapper goalAspectMapper) {
        this.goalAspectMapper = goalAspectMapper;
    }

    @Override
    public List<GoalAspect> getListByGid(GoalAspect aspect) {
        LambdaQueryWrapper<GoalAspect> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(GoalAspect::getGid, aspect.getGid());
        if (aspect.getType() != null && aspect.getType().trim().length() > 0) {
            lambdaQueryWrapper.like(GoalAspect::getType, aspect.getType()) ;
        }
        goalAspectMapper.selectList(lambdaQueryWrapper);
        return goalAspectMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void deleteByGid(Integer gid) {
        LambdaQueryWrapper<GoalAspect> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(GoalAspect::getGid, gid) ;
        goalAspectMapper.delete(lambdaQueryWrapper);
    }
}
