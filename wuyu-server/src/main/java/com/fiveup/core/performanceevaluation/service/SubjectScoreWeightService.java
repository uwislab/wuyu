package com.fiveup.core.performanceevaluation.service;

import com.fiveup.core.performanceevaluation.bean.SubjectScoreWeight;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;

import java.util.List;

/**
 * 学生表现的业务逻辑处理
 */
public interface SubjectScoreWeightService {

    /**
     * 增加
     * @param subjectScoreWeight
     */
    void add(SubjectScoreWeight subjectScoreWeight);

    /**
     * 根据ID删除数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据ID更新数据
     * @param subjectScoreWeight
     */
    void updateById(SubjectScoreWeight subjectScoreWeight);

    /**
     * 获取所有已封装数据
     * @return
     */
    List<SubjectScoreWeightVO> getAll();

    /**
     * 根据教师ID获取已封装数据
     * @param tid
     * @return
     */
    SubjectScoreWeightVO getByTId(Integer tid);

    /**
     * 查询记录总数
     * @return
     */
    Integer getCount(String sql);

    /**
     * 分页查询
     * @param start 起始
     * @param pageSize
     * @return
     */
    List<SubjectScoreWeightVO> getPagination(Integer start, Integer pageSize);

    /**
     * 根据ID查询对应的数据
     * @param id
     * @return
     */
    SubjectScoreWeightVO getById(Integer id);
}
