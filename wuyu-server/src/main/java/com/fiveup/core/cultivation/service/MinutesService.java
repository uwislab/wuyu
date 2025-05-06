package com.fiveup.core.cultivation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.cultivation.entity.Minutes;

import java.util.List;

/**
 * @author Harvi
 */
public interface MinutesService extends IService<Minutes> {
    /**
     * TODO
     * @param id TODO
     * @return TODO
     */
    Minutes getOne(Integer id);

    /**
     * TODO
     * @param o TODO
     * @return TODO
     */
    List<Minutes> getAll(Minutes o);
}
