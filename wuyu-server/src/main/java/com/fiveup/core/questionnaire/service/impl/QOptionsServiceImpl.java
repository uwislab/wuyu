package com.fiveup.core.questionnaire.service.impl;

import java.util.List;

import com.fiveup.core.questionnaire.domain.QQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiveup.core.questionnaire.mapper.QOptionsMapper;
import com.fiveup.core.questionnaire.domain.QOptions;
import com.fiveup.core.questionnaire.service.IQOptionsService;

/**
 * 选项管理Service业务层处理
 * 
 * @author admin
 * @date
 */
@Service
public class QOptionsServiceImpl implements IQOptionsService 
{
    @Autowired
    private QOptionsMapper qOptionsMapper;

    /**
     * 查询选项管理
     * 
     * @param optId 选项管理主键
     * @return 选项管理
     */
    @Override
    public QOptions selectQOptionsByOptId(Long optId)
    {
        return qOptionsMapper.selectQOptionsByOptId(optId);
    }

    /**
     * 查询选项管理列表
     * 
     * @param qOptions 选项管理
     * @return 选项管理
     */
    @Override
    public List<QOptions> selectQOptionsList(QOptions qOptions)
    {
        return qOptionsMapper.selectQOptionsList(qOptions);
    }

    /**
     * 新增选项管理
     * 
     * @param qOptions 选项管理
     * @return 结果
     */
    @Override
    public int insertQOptions(QOptions qOptions)
    {
        return qOptionsMapper.insertQOptions(qOptions);
    }

    /**
     * 修改选项管理
     * 
     * @param qOptions 选项管理
     * @return 结果
     */
    @Override
    public int updateQOptions(QOptions qOptions)
    {
        return qOptionsMapper.updateQOptions(qOptions);
    }

    /**
     * 批量删除选项管理
     * 
     * @param optIds 需要删除的选项管理主键
     * @return 结果
     */
    @Override
    public int deleteQOptionsByOptIds(Long[] optIds)
    {
        return qOptionsMapper.deleteQOptionsByOptIds(optIds);
    }

    /**
     * 删除选项管理信息
     * 
     * @param optId 选项管理主键
     * @return 结果
     */
    @Override
    public int deleteQOptionsByOptId(Long optId)
    {
        return qOptionsMapper.deleteQOptionsByOptId(optId);
    }


    /**
     * 根据问卷id获取选项列表
     *
     * @param paperId 问卷id
     * @return 结果
     */
    @Override
    public List<QOptions> getOptionListByPaperId(int paperId) {
        return qOptionsMapper.getOptionListByPaperId(paperId);
    }

    /**
     * 根据问卷id获取选项列表
     *
     * @param questionId 问卷id
     * @return 结果
     */
    @Override
    public List<QOptions> getOptionListByQuestionId(int questionId) {
        return qOptionsMapper.getOptionListByQuestionId(questionId);
    }
}
