package com.fiveup.core.cultivation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.cultivation.entity.Guidance;
import com.fiveup.core.cultivation.mapper.GuidanceMapper;
import com.fiveup.core.cultivation.service.GuidanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvi
 */
@Service
public class GuidanceServiceImpl extends ServiceImpl<GuidanceMapper, Guidance> implements GuidanceService {
    private final GuidanceMapper guidanceMapper;

    @Autowired
    public GuidanceServiceImpl(GuidanceMapper guidanceMapper) {
        this.guidanceMapper = guidanceMapper;
    }

    @Override
    public List<Guidance> getAll(Guidance o) {
        return guidanceMapper.getAll(o);
    }
}
