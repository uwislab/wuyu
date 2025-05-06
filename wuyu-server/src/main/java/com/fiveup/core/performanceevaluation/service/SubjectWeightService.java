package com.fiveup.core.performanceevaluation.service;

import com.fiveup.core.performanceevaluation.bean.SubjectWeight;

import java.util.List;

public interface SubjectWeightService {

    List<SubjectWeight> selectInTid(List<Integer> tidS);
    //查询单个数据
    SubjectWeight selectOne(int id);

    List<SubjectWeight> selectByTid(Integer tid);

    List<SubjectWeight> selectAll();
}
