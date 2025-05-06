package com.fiveup.core.performanceevaluation.mapper;

import com.fiveup.core.performanceevaluation.bean.SubjectWeight;

import java.util.List;

public interface SubjectWeightMapper {

    List<SubjectWeight> selectAll();

    void insert(SubjectWeight subjectWeight);

    void update(SubjectWeight subjectWeight);

    void deleteById(int id);

    List<SubjectWeight> selectInTid(List<Integer> tidS);

    List<SubjectWeight> selectByTid(Integer tid);

    SubjectWeight selectOne(int id);
}
