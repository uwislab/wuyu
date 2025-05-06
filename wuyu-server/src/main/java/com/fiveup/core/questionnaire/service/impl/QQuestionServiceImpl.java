package com.fiveup.core.questionnaire.service.impl;

import java.util.List;

import com.fiveup.core.questionnaire.domain.QOptions;
import com.fiveup.core.questionnaire.dto.QuestionDto;
import com.fiveup.core.questionnaire.mapper.QOptionsMapper;
import com.fiveup.core.questionnaire.po.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiveup.core.questionnaire.mapper.QQuestionMapper;
import com.fiveup.core.questionnaire.domain.QQuestion;
import com.fiveup.core.questionnaire.service.IQQuestionService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 题目管理Service业务层处理
 * 
 * @author admin
 * @date
 */
@Service
public class QQuestionServiceImpl implements IQQuestionService 
{
    @Autowired
    private QQuestionMapper qQuestionMapper;
    @Autowired
    private QOptionsMapper qOptionsMapper;

    /**
     * 查询题目管理
     * 
     * @param questId 题目管理主键
     * @return 题目管理
     */
    @Override
    public QQuestion selectQQuestionByQuestId(Long questId)
    {
        return qQuestionMapper.selectQQuestionByQuestId(questId);
    }

    /**
     * 查询题目管理列表
     * 
     * @param qQuestion 题目管理
     * @return 题目管理
     */
    @Override
    public List<QQuestion> selectQQuestionList(QQuestion qQuestion)
    {
        return qQuestionMapper.selectQQuestionList(qQuestion);
    }

    /**
     * 新增题目管理
     * 
     * @param qQuestion 题目管理
     * @return 结果
     */
    @Override
    public int insertQQuestion(QQuestion qQuestion)
    {
        int i = qQuestionMapper.insertQQuestion(qQuestion);
        if(i > 0){ // 插入选项
            for(QOptions qOptions : qQuestion.getqOptionsList()){
                qOptions.setQuestId(qQuestion.getQuestId());
                qOptionsMapper.insertQOptions(qOptions);
            }
        }
        return 1;
    }

    /**
     * 修改题目管理
     * 
     * @param qQuestion 题目管理
     * @return 结果
     */
    @Override
    public int updateQQuestion(QQuestion qQuestion)
    {
        return qQuestionMapper.updateQQuestion(qQuestion);
    }

    /**
     * 批量删除题目管理
     * 
     * @param questIds 需要删除的题目管理主键
     * @return 结果
     */
    @Override
    public int deleteQQuestionByQuestIds(Long[] questIds)
    {
        return qQuestionMapper.deleteQQuestionByQuestIds(questIds);
    }

    /**
     * 删除题目管理信息
     * 
     * @param questId 题目管理主键
     * @return 结果
     */
    @Override
    public int deleteQQuestionByQuestId(Long questId)
    {
        return qQuestionMapper.deleteQQuestionByQuestId(questId);
    }

    @Override
    public List<QQuestion> selectAnswerCount(QQuestion qQuestion) {
        return qQuestionMapper.selectAnswerCount(qQuestion);
    }

    @Override
    @Transactional
    public int updQuestion(QuestionDto questionDto) {
       if("q".equals(questionDto.getType())){
        QQuestion qQuestion=new QQuestion();
        qQuestion.setQuestId(questionDto.getQuestId());
        qQuestion.setQuestTitle(questionDto.getName());
        return qQuestionMapper.updateQQuestion(qQuestion);

       }else{
           QOptions options=new QOptions();
           options.setOptContent(questionDto.getName());
           options.setOptId(questionDto.getQuestId());
           return  qOptionsMapper.updateQOptions(options);
       }
    }

    @Override
    @Transactional
    public int delquest(QuestionDto questionDto) {
        int i =0;
        if("q".equals(questionDto.getType())) {
             i = qQuestionMapper.deleteQQuestionByQuestId(questionDto.getQuestId());
            qQuestionMapper.delQuestoptList(questionDto.getQuestId());
        }else{
             i=qOptionsMapper.deleteQOptionsByOptId(questionDto.getQuestId());
        }
        return i;
    }

    /**
     * 根据问卷id获取问题列表
     *
     * @param paperId 问卷id
     * @return 结果
     */
    @Override
    public List<QQuestion> getQuestionListByPaperId(int paperId) {
        return qQuestionMapper.getQuestionListByPaperId(paperId);
    }

    @Override
    public int delquest(Long[] questIds) {
        return qQuestionMapper.delquest(questIds);
    }

    @Override
    public int delanswer(Long[] questIds) {
        return qQuestionMapper.delanswer(questIds);
    }
}
