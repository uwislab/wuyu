package com.fiveup.core.miniapp.service.impl;


import com.fiveup.core.miniapp.mapper.ParentMapper;
import com.fiveup.core.miniapp.model.Parent;
import com.fiveup.core.miniapp.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ParentServiceImpl
 * @Author: shilin
 * @Date: 2022/9/18 16:32
 */
@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    ParentMapper parentMapper;

    @Override
    public int addParent(Parent parent) {
        return parentMapper.addParent(parent);
    }

    @Override
    public Parent login(Parent parent) {
        return  parentMapper.getParent(parent);
    }
}
