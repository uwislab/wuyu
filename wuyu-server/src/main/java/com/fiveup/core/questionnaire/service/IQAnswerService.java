package com.fiveup.core.questionnaire.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.questionnaire.domain.QAnswer;
import com.fiveup.core.questionnaire.po.Answer;

/**
 * 答题管理Service接口
 * 
 * @author admin
 * @date
 */
public interface IQAnswerService 
{
    /**
     * 查询答题管理
     * 
     * @param answerId 答题管理主键
     * @return 答题管理
     */
    public QAnswer selectQAnswerByAnswerId(Long answerId);

    /**
     * 查询答题管理
     *
     * @param qAnswer 答题管理
     * @return 答题管理
     */
    public List<QAnswer> getAnswerListByPaperId(QAnswer qAnswer);

    /**
     * 查询答题管理列表
     * 
     * @param qAnswer 答题管理
     * @return 答题管理集合
     */
    public List<QAnswer> selectQAnswerList(QAnswer qAnswer);

    /**
     * 新增答题管理
     * 
     * @param qAnswer 答题管理
     * @return 结果
     */
    public int insertQAnswer(List<QAnswer> qAnswerList);

    /**
     * 修改答题管理
     * 
     * @param qAnswer 答题管理
     * @return 结果
     */
    public int updateQAnswer(QAnswer qAnswer);

    /**
     * 批量删除答题管理
     * 
     * @param answerIds 需要删除的答题管理主键集合
     * @return 结果
     */
    public int deleteQAnswerByAnswerIds(Long[] answerIds);

    /**
     * 删除答题管理信息
     * 
     * @param answerId 答题管理主键
     * @return 结果
     */
    public int deleteQAnswerByAnswerId(Long answerId);

    public int addAnswers(List<QAnswer> answerList);
}
