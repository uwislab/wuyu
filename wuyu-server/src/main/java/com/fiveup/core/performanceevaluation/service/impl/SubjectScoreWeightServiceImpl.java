package com.fiveup.core.performanceevaluation.service.impl;

import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.bean.SubjectScoreWeight;
import com.fiveup.core.performanceevaluation.mapper.StudentPerformanceMapper;
import com.fiveup.core.performanceevaluation.mapper.SubjectScoreWeightMapper;
import com.fiveup.core.performanceevaluation.service.StudentPerformanceService;
import com.fiveup.core.performanceevaluation.service.SubjectScoreWeightService;
import com.fiveup.core.performanceevaluation.vo.StudentPerformanceVO;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 科目分数权重业务逻辑实现
 */
@Service
public class SubjectScoreWeightServiceImpl implements SubjectScoreWeightService {

    @Autowired
    private SubjectScoreWeightMapper subjectScoreWeightMapper;


    @Override
    public void add(SubjectScoreWeight subjectScoreWeight) {
        subjectScoreWeightMapper.insert(subjectScoreWeight);
    }

    @Override
    public void deleteById(Integer id) {
        subjectScoreWeightMapper.deleteById(id);
    }

    @Override
    public void updateById(SubjectScoreWeight subjectScoreWeight) {
        subjectScoreWeightMapper.updateById(subjectScoreWeight);
    }

    @Override
    public List<SubjectScoreWeightVO> getAll() {
        List<SubjectScoreWeightVO> subjectScoreWeightVOS = subjectScoreWeightMapper.selectAll();
        return subjectScoreWeightVOS;
    }

    @Override
    public SubjectScoreWeightVO getByTId(Integer tid) {
        SubjectScoreWeightVO subjectScoreWeightVO = subjectScoreWeightMapper.selectByTId(tid);
        return subjectScoreWeightVO;
    }

    @Override
    public Integer getCount(String sql) {
        Integer count = subjectScoreWeightMapper.selectCountBySql(sql);
        return count;
    }

    @Override
    public List<SubjectScoreWeightVO> getPagination(Integer start, Integer pageSize) {
        List<SubjectScoreWeightVO> subjectScoreWeightVOS = subjectScoreWeightMapper.selectPagination(start, pageSize);
        return subjectScoreWeightVOS;
    }

    @Override
    public SubjectScoreWeightVO getById(Integer id) {
        SubjectScoreWeightVO subjectScoreWeightVO = subjectScoreWeightMapper.selectById(id);
        return subjectScoreWeightVO;
    }
}
