package com.fiveup.core.commentgeneration.service;

import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.commentgeneration.vo.CorpusVO;
import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.vo.StudentPerformanceVO;

import java.util.List;

/**
 * 语料及评价的业务逻辑处理
 */
public interface CorpusService {
    /**
     * 增加
     * @param corpus
     */
    void add(Corpus corpus);

    /**
     * 根据ID删除数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据ID更新数据
     * @param corpus
     */
    void updateById(Corpus corpus);

    /**
     * 获取所有数据
     * @return
     */
    List<Corpus> getList();

    /**
     * 获取所有已封装数据
     * @return
     */
    List<CorpusVO> getAll();

    /**
     * 根据ID查询数据
     * @param id
     * @return
     */
    CorpusVO getById(Integer id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer deleteAll(Integer[] ids);

    List<Corpus> getCorpusByPage(Integer pageNum, Integer pageSize);

    List<CorpusVO> search(Integer subjectId,String comment);
}
