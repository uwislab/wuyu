package com.fiveup.core.cultivation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.cultivation.entity.Guidance;

import java.util.List;

/**
 * @author Harvi
 */
public interface GuidanceService extends IService<Guidance> {
    /**
     * todo
     * @param o todo
     * @return todo
     */
    List<Guidance> getAll(Guidance o);
}