package com.fiveup.core.questionnaire.mapper;

import java.util.List;

import com.fiveup.core.questionnaire.domain.QOptions;
import com.fiveup.core.questionnaire.domain.QQuestion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * 选项管理Mapper接口
 *
 * @author admin
 * @date
 */
public interface QOptionsMapper {
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
     * 删除选项管理
     *
     * @param optId 选项管理主键
     * @return 结果
     */
    public int deleteQOptionsByOptId(Long optId);

    /**
     * 批量删除选项管理
     *
     * @param optIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQOptionsByOptIds(Long[] optIds);


    /**
     * 根据问卷id获取选项
     *
     * @param paperId 问卷id
     * @return 结果
     */
    @Results(id = "QOptions", value = {
            @Result(property = "optId", column = "opt_id"),
            @Result(property = "questId", column = "quest_id"),
            @Result(property = "optSeque", column = "opt_seque"),
            @Result(property = "optContent", column = "opt_content")
    })


    @Select("select * from q_options where quest_id=any(select quest_id from q_question WHERE paper_id=#{paperId})")
    List<QOptions> getOptionListByPaperId(int paperId);

    @Select("SELECT * FROM q_options WHERE quest_id = #{questionId}")
    List<QOptions> getOptionListByQuestionId(@Param("questionId") int questionId);
}
