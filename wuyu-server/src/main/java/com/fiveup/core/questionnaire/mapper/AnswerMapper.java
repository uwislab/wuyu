package com.fiveup.core.questionnaire.mapper;

import com.fiveup.core.questionnaire.vo.AnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: AnswerMapper
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:20
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Mapper
@Repository
public interface AnswerMapper {
    int addAnswer(AnswerVO answerVO);

    List<AnswerVO> selectByQuestionId(int questionId);

    List<String> selectUUIDbyPaper(int paperId);

    List<AnswerVO> selectAnswersByUUID(String UUID);

    String selectOption(@Param("question_id")int question_id, @Param("sequence")String sequence);
}
