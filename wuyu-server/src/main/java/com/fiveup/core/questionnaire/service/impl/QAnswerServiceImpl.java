package com.fiveup.core.questionnaire.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import com.fiveup.core.common.util.DateUtils;
import java.util.Date;
import com.fiveup.core.questionnaire.po.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiveup.core.questionnaire.mapper.QAnswerMapper;
import com.fiveup.core.questionnaire.domain.QAnswer;
import com.fiveup.core.questionnaire.service.IQAnswerService;
import javax.transaction.Transactional;
/**
 * 答题管理Service业务层处理
 * 
 * @author admin
 * @date
 */
@Service
public class QAnswerServiceImpl implements IQAnswerService 
{
    @Autowired
    private QAnswerMapper qAnswerMapper;

    /**
     * 查询答题管理
     * 
     * @param answerId 答题管理主键
     * @return 答题管理
     */
    @Override
    public QAnswer selectQAnswerByAnswerId(Long answerId)
    {
        return qAnswerMapper.selectQAnswerByAnswerId(answerId);
    }

    /**
     * 查询答题管理列表
     * 
     * @param qAnswer 答题管理
     * @return 答题管理
     */
    @Override
    public List<QAnswer> selectQAnswerList(QAnswer qAnswer)
    {
        return qAnswerMapper.selectQAnswerList(qAnswer);
    }

    /**
     * 查询答题管理列表
     *
     * @param qAnswer 答题管理
     * @return 答题管理
     */
    @Override
    public List<QAnswer> getAnswerListByPaperId(QAnswer qAnswer) {
        return qAnswerMapper.getAnswerListByPaperId(qAnswer);
    }

    /**
     * 新增答题管理
     * 
     * @param qAnswerList 答题管理
     * @return 结果
     */
    @Override
    public int insertQAnswer(List<QAnswer> qAnswerList)
    {
        int res = 0;
        Date date = DateUtils.getNowDate();
        for(QAnswer qAnswer : qAnswerList){
            if(qAnswer.getAnswerContent() != null){
                qAnswer.setCreateTime(date);
                qAnswerMapper.insertQAnswer(qAnswer);
                res = 1;
            }
        }
        return res;
    }

    /**
     * 修改答题管理
     * 
     * @param qAnswer 答题管理
     * @return 结果
     */
    @Override
    public int updateQAnswer(QAnswer qAnswer)
    {
        return qAnswerMapper.updateQAnswer(qAnswer);
    }

    /**
     * 批量删除答题管理
     * 
     * @param answerIds 需要删除的答题管理主键
     * @return 结果
     */
    @Override
    public int deleteQAnswerByAnswerIds(Long[] answerIds)
    {
        return qAnswerMapper.deleteQAnswerByAnswerIds(answerIds);
    }

    /**
     * 删除答题管理信息
     * 
     * @param answerId 答题管理主键
     * @return 结果
     */
    @Override
    public int deleteQAnswerByAnswerId(Long answerId)
    {
        return qAnswerMapper.deleteQAnswerByAnswerId(answerId);
    }

    @Override
    @Transactional
    public int addAnswers(List<QAnswer> answerList) {
//        long timestamp = System.currentTimeMillis(); // 假设时间戳为当前时间
//        String format = "yyyy-MM-dd HH:mm:ss"; //设定日期格式
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        String dateString = sdf.format(new Date(timestamp));

        answerList.forEach(answer -> {
            answer.setCreateTime(new Date());
        });


        return qAnswerMapper.addAnswers(answerList);
//        return qAnswerMapper.;
    }
}
