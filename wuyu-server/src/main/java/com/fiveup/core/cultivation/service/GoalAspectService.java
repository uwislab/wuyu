package com.fiveup.core.cultivation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.cultivation.entity.GoalAspect;

import java.util.List;

/**
 * @author Harvi
 */
public interface GoalAspectService extends IService<GoalAspect> {
    /**
     * TODO
     * @param aspect TODO
     * @return TODO
     */
    List<GoalAspect> getListByGid(GoalAspect aspect);

    /**
     * TODO
     * @param gid TODO
     */
    void deleteByGid(Integer gid);
}
