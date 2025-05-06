package com.fiveup.core.performanceevaluation.service.impl;

import com.fiveup.core.performanceevaluation.bean.SubjectWeight;
import com.fiveup.core.performanceevaluation.mapper.SubjectWeightMapper;
import com.fiveup.core.performanceevaluation.service.SubjectWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectWeightServiceImpl implements SubjectWeightService {

    @Autowired
    private SubjectWeightMapper subjectWeightMapper;

    @Override
    public List<SubjectWeight> selectInTid(List<Integer> tidS){
        List<SubjectWeight> list = subjectWeightMapper.selectInTid(tidS);

        return list;
    }

    @Override
    public SubjectWeight selectOne(int id) {
        return subjectWeightMapper.selectOne(id);
    }

    @Override
    public List<SubjectWeight> selectByTid(Integer tid) {
        return subjectWeightMapper.selectByTid(tid);
    }

    @Override
    public List<SubjectWeight> selectAll() {
        return subjectWeightMapper.selectAll();
    }
}
