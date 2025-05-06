package com.fiveup.core.cultivation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.cultivation.entity.BasicTeacher;
import com.fiveup.core.cultivation.mapper.BasicTeacherMapper;
import com.fiveup.core.cultivation.service.BasicTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvi
 */
@Service
public class BasicTeacherServiceImpl extends ServiceImpl<BasicTeacherMapper, BasicTeacher> implements BasicTeacherService {
    private final BasicTeacherMapper basicTeacherMapper;

    @Autowired
    public BasicTeacherServiceImpl(BasicTeacherMapper basicTeacherMapper) {
        this.basicTeacherMapper = basicTeacherMapper;
    }

    @Override
    public List<BasicTeacher> getList() {
        LambdaQueryWrapper<BasicTeacher> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(BasicTeacher::getDeleted, 0) ;
        return basicTeacherMapper.selectList(lambdaQueryWrapper);
    }
}