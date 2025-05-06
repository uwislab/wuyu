package com.fiveup.core.questionnaire.service;

import java.util.List;
import com.fiveup.core.questionnaire.domain.QPaper;

/**
 * 问卷信息Service接口
 * 
 * @author
 * @date
 */
public interface IQPaperService 
{
    /**
     * 查询问卷信息
     * 
     * @param paperId 问卷信息主键
     * @return 问卷信息
     */
    public QPaper selectQPaperByPaperId(Long paperId);

    /**
     * 查询问卷信息列表
     * 
     * @param qPaper 问卷信息
     * @return 问卷信息集合
     */
    public List<QPaper> selectQPaperList(QPaper qPaper);


    public Integer selectCount(QPaper qPaper);

    /**
     * 新增问卷信息
     * 
     * @param qPaper 问卷信息
     * @return 结果
     */
    public int insertQPaper(QPaper qPaper);

    /**
     * 修改问卷信息
     * 
     * @param qPaper 问卷信息
     * @return 结果
     */
    public int updateQPaper(QPaper qPaper);

    /**
     * 批量删除问卷信息
     * 
     * @param paperIds 需要删除的问卷信息主键集合
     * @return 结果
     */
    public int deleteQPaperByPaperIds(Long[] paperIds);

    /**
     * 删除问卷信息信息
     * 
     * @param paperId 问卷信息主键
     * @return 结果
     */
    public int deleteQPaperByPaperId(Long paperId);

    /**
     * 根据问卷ID查询题目ID
     *
     * @param paperId 问卷Id
     * @return 题目ID
     */
    public Long[] selectQuestId(Long paperId);
}
