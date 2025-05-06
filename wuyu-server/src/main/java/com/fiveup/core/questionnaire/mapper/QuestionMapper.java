package com.fiveup.core.questionnaire.mapper;

import com.fiveup.core.questionnaire.po.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    int addQuestion(Question question);

    void updateQuestion(Question question);

    Question selectByQuestionId(int id);

    void deleteQuestion(int questionId);

    void deleteByPaperId(int paperId);

    List<Question> selectByPaperId(int paperId);
}
