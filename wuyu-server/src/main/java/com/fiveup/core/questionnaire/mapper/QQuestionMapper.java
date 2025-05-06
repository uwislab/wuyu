package com.fiveup.core.questionnaire.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.questionnaire.domain.QQuestion;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * 题目管理Mapper接口
 * 
 * @author admin
 * @date
 */
public interface QQuestionMapper
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
     * 删除题目管理
     * 
     * @param questId 题目管理主键
     * @return 结果
     */
    public int deleteQQuestionByQuestId(Long questId);

    /**
     * 批量删除题目管理
     *
     * @param questIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQQuestionByQuestIds(Long[] questIds);

    List<QQuestion> selectAnswerCount(QQuestion qQuestion);

    /**
     * 删除题目
     */
    public  int delQuestoptList(Long questId);

   int selcetListByPaperId(Long id);


    /**
     * 根据问卷id获取问题列表
     *
     * @param paperId 问卷id
     * @return 结果
     */
    @Results(id = "QQuestion", value = {
            @Result(property = "questId", column = "quest_id"),
            @Result(property = "paperId", column = "paper_id"),
            @Result(property = "questType", column = "quest_type"),
            @Result(property = "questTitle", column = "quest_title")
    })


    @Select("select * from q_question " +
            "where paper_id= #{paperId}")
    List<QQuestion> getQuestionListByPaperId(int paperId);


    /**
     * 删除选项
     *
     * @param questIds 需要删除的数据主键集合
     * @return 结果
     */
    public int delquest(Long[] questIds);

    /**
     * 删除回答
     *
     * @param questIds 需要删除的数据主键集合
     * @return 结果
     */
    public int delanswer(Long[] questIds);
}
