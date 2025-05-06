package com.fiveup.core.questionnaire.service;

import java.util.List;
import com.fiveup.core.questionnaire.domain.QQuestion;
import com.fiveup.core.questionnaire.dto.QuestionDto;

/**
 * 题目管理Service接口
 * 
 * @author admin
 * @date
 */
public interface IQQuestionService 
{
    /**
     * 查询题目管理
     * 
     * @param questId 题目管理主键
     * @return 题目管理
     */
    public QQuestion selectQQuestionByQuestId(Long questId);

    /**
     * 查询题目管理列表
     * 
     * @param qQuestion 题目管理
     * @return 题目管理集合
     */
    public List<QQuestion> selectQQuestionList(QQuestion qQuestion);

    /**
     * 新增题目管理
     * 
     * @param qQuestion 题目管理
     * @return 结果
     */
    public int insertQQuestion(QQuestion qQuestion);

    /**
     * 修改题目管理
     * 
     * @param qQuestion 题目管理
     * @return 结果
     */
    public int updateQQuestion(QQuestion qQuestion);

    /**
     * 批量删除题目管理
     * 
     * @param questIds 需要删除的题目管理主键集合
     * @return 结果
     */
    public int deleteQQuestionByQuestIds(Long[] questIds);

    /**
     * 删除题目管理信息
     * 
     * @param questId 题目管理主键
     * @return 结果
     */
    public int deleteQQuestionByQuestId(Long questId);

    List<QQuestion> selectAnswerCount(QQuestion qQuestion);

    /**
     * 更新题型
     */
    public  int  updQuestion( QuestionDto questionDto);

    /**
     * 删除题型
     */

    public  int delquest(QuestionDto questionDto);


    /**
     * 根据问卷id获取问题列表
     *
     * @param paperId 问卷id
     * @return 结果
     */
    List<QQuestion> getQuestionListByPaperId(int paperId);

    /**
     * 根据问题id删除选项
     *
     * @param questId 问题id
     * @return 结果
     */
    public  int delquest(Long[] questId);

    /**
     * 根据问题id删除回答
     *
     * @param questId 问题id
     * @return 结果
     */
    public  int delanswer(Long[] questId);
}
