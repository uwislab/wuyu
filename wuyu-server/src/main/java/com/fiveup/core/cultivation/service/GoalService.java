package com.fiveup.core.cultivation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.cultivation.entity.Goal;
import com.fiveup.core.cultivation.entity.Indicator;

import java.util.List;

/**
 * @author Harvi
 */
public interface GoalService extends IService<Goal> {
    /**
     * TODO
     * @param goal TODO
     * @return TODO
     */
    List<Goal> getList(Goal goal);

    /**
     * TODO
     * @param id TODO
     * @return TODO
     */
    Goal getById(Integer id);
     
    /**
     *
     * @param goal
     * @return
     */
    List<Goal> getListByTn(Goal goal);

    /**
     *
     * @param goal
     * @return {@link Goal}
     */
    List<Goal> getListByEditornames(Goal goal);

    Indicator getIndicator(Integer id);

    void updateIndicator(Indicator indicator);
}
