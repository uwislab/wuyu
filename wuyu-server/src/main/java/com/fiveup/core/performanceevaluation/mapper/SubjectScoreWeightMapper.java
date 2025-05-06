package com.fiveup.core.performanceevaluation.mapper;

import com.fiveup.core.performanceevaluation.bean.SubjectScoreWeight;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;

import java.util.List;

/**
 * 对科目分数权重的数据操作
 */
public interface SubjectScoreWeightMapper {
    /**
     * 插入数据
     * @param subjectScoreWeight
     */
    void insert(SubjectScoreWeight subjectScoreWeight);

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
     * 查询所有数据
     * @return
     */
    List<SubjectScoreWeightVO> selectAll();

    /**
     * 根据教师ID查询数据
     * @param id
     * @return
     */
    SubjectScoreWeightVO selectByTId(Integer id);

    /**
     * 查询相关记录总数
     * @return
     */
    Integer selectCountBySql(String sql);

    /**
     * 分页查询
     * @param start
     * @param pageSize
     * @return
     */
    List<SubjectScoreWeightVO> selectPagination(Integer start, Integer pageSize);

    /**
     * 根据ID查询对应的数据
     * @param id
     * @return
     */
    SubjectScoreWeightVO selectById(Integer id);
}
