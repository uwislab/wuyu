package com.fiveup.core.cultivation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.cultivation.entity.Goal;
import com.fiveup.core.cultivation.entity.Indicator;
import com.fiveup.core.cultivation.mapper.GoalMapper;
import com.fiveup.core.cultivation.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvi
 */
@Service
public class GoalServiceImpl extends ServiceImpl<GoalMapper, Goal> implements GoalService {
    private final GoalMapper goalMapper;

    @Autowired
    public GoalServiceImpl(GoalMapper goalMapper) {
        this.goalMapper = goalMapper;
    }

    @Override
    public Goal getById(Integer id) {
        return goalMapper.getById(id);
    }

    @Override
    public List<Goal> getList(Goal goal) {
        goalMapper.getList(goal);
        return goalMapper.getList(goal);
    }

    /**
     * 通过发起人查询
     * @param goal
     * @return {@code List<Goal>}
     */
    @Override
    public List<Goal> getListByTn(Goal goal){
        goalMapper.getListByTn(goal);
        return goalMapper.getListByTn(goal);
    }

    @Override
    public List<Goal> getListByEditornames(Goal goal){
        goalMapper.getAllByEditorTids(goal);
        return goalMapper.getAllByEditorTids(goal);
    }

    @Override
    public Indicator getIndicator(Integer id) {
        return goalMapper.selectIndicator(id);
    }

    @Override
    public void updateIndicator(Indicator indicator) {
        goalMapper.updateIndicator(indicator);
    }
}
