package com.fiveup.core.cultivation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.cultivation.entity.Minutes;
import com.fiveup.core.cultivation.mapper.MinutesMapper;
import com.fiveup.core.cultivation.service.MinutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvi
 */
@Service
public class MinutesServiceImpl extends ServiceImpl<MinutesMapper, Minutes> implements MinutesService {
    private final MinutesMapper minutesMapper;

    @Autowired
    public MinutesServiceImpl(MinutesMapper minutesMapper) {
        this.minutesMapper = minutesMapper;
    }

    @Override
    public Minutes getOne(Integer id) {
        return minutesMapper.getOne(id);
    }

    @Override
    public List<Minutes> getAll(Minutes o) {
        return minutesMapper.getAll(o);
    }
}
