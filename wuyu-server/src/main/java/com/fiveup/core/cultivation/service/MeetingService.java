package com.fiveup.core.cultivation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.cultivation.entity.Meeting;

import java.util.List;

/**
 * @author Harvi
 */
public interface MeetingService extends IService<Meeting> {
    /**
     * TODO
     * @param id TODO
     * @return TODO
     */
    Meeting getOne(Integer id);

    /**
     * TODO
     * @param o TODO
     * @return TODO
     */
    List<Meeting> getAll(Meeting o);
}
