package com.fiveup.core.questionnaire.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.questionnaire.domain.QAnswer;
import com.fiveup.core.questionnaire.po.Answer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * 答题管理Mapper接口
 * 
 * @author admin
 * @date
 */
public interface QAnswerMapper extends BaseMapper<QAnswer>
{
    /**
     * 查询答题管理
     * 
     * @param answerId 答题管理主键
     * @return 答题管理
     */
    public QAnswer selectQAnswerByAnswerId(Long answerId);

    /**
     * 查询答题管理列表
     * 
     * @param qAnswer 答题管理
     * @return 答题管理集合
     */
    public List<QAnswer> selectQAnswerList(QAnswer qAnswer);

    /**
     * 查询答题管理列表
     *
     * @param qAnswer 答题管理
     * @return 答题管理集合
     */
    @Select("select * from q_answer where paper_id=#{paperId} and create_user_id=#{createUserId} order by quest_id")
    public List<QAnswer> getAnswerListByPaperId(QAnswer qAnswer);


    /**
     * 新增答题管理
     * 
     * @param qAnswer 答题管理
     * @return 结果
     */
    public int insertQAnswer(QAnswer qAnswer);

    /**
     * 修改答题管理
     * 
     * @param qAnswer 答题管理
     * @return 结果
     */
    public int updateQAnswer(QAnswer qAnswer);

    /**
     * 删除答题管理
     * 
     * @param answerId 答题管理主键
     * @return 结果
     */
    public int deleteQAnswerByAnswerId(Long answerId);

    /**
     * 批量删除答题管理
     * 
     * @param answerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQAnswerByAnswerIds(Long[] answerIds);

    public int addAnswers(@Param("answerList") List<QAnswer> answerList);
}
