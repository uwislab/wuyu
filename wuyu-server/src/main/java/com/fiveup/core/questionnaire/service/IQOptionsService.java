package com.fiveup.core.questionnaire.service;

import java.util.List;
import com.fiveup.core.questionnaire.domain.QOptions;
import com.fiveup.core.questionnaire.domain.QQuestion;

/**
 * 选项管理Service接口
 * 
 * @author admin
 * @date
 */
public interface IQOptionsService 
{
    /**
     * 查询选项管理
     * 
     * @param optId 选项管理主键
     * @return 选项管理
     */
    public QOptions selectQOptionsByOptId(Long optId);

    /**
     * 查询选项管理列表
     * 
     * @param qOptions 选项管理
     * @return 选项管理集合
     */
    public List<QOptions> selectQOptionsList(QOptions qOptions);

    /**
     * 新增选项管理
     * 
     * @param qOptions 选项管理
     * @return 结果
     */
    public int insertQOptions(QOptions qOptions);

    /**
     * 修改选项管理
     * 
     * @param qOptions 选项管理
     * @return 结果
     */
    public int updateQOptions(QOptions qOptions);

    /**
     * 批量删除选项管理
     * 
     * @param optIds 需要删除的选项管理主键集合
     * @return 结果
     */
    public int deleteQOptionsByOptIds(Long[] optIds);

    /**
     * 删除选项管理信息
     * 
     * @param optId 选项管理主键
     * @return 结果
     */
    public int deleteQOptionsByOptId(Long optId);


    /**
     * 根据问卷id获取选项列表
     *
     * @param paperId 问卷id
     * @return 结果
     */
    List<QOptions> getOptionListByPaperId(int paperId);

    /**
     * 根据问题id获取选项列表
     *
     * @param questionId 问卷id
     * @return 结果
     */
    List<QOptions> getOptionListByQuestionId(int questionId);
}
