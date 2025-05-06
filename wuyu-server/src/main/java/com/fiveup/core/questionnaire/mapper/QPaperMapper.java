package com.fiveup.core.questionnaire.mapper;

import java.util.List;
import com.fiveup.core.questionnaire.domain.QPaper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 问卷信息Mapper接口
 * 
 * @author
 * @date
 */
@Repository
@Mapper
public interface QPaperMapper 
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
     * 删除问卷信息
     * 
     * @param paperId 问卷信息主键
     * @return 结果
     */
    public int deleteQPaperByPaperId(Long paperId);

    /**
     * 批量删除问卷信息
     * 
     * @param paperIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQPaperByPaperIds(Long[] paperIds);

    public Long[] selectQuestId(Long paperId);
}
