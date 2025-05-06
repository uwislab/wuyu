package com.fiveup.core.cultivation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.cultivation.entity.Meeting;
import com.fiveup.core.cultivation.mapper.MeetingMapper;
import com.fiveup.core.cultivation.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvi
 */
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements MeetingService {
    MeetingMapper meetingMapper;

    @Autowired
    public MeetingServiceImpl(MeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    @Override
    public Meeting getOne(Integer id) {
        return meetingMapper. getOne(id);
    }

    @Override
    public List<Meeting> getAll(Meeting o) {
        return meetingMapper.getAll(o);
    }
}
