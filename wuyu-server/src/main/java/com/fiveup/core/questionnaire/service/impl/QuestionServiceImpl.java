package com.fiveup.core.questionnaire.service.impl;


import com.fiveup.core.questionnaire.mapper.OptionsMapper;
import com.fiveup.core.questionnaire.mapper.QuestionMapper;
import com.fiveup.core.questionnaire.po.Options;
import com.fiveup.core.questionnaire.po.Question;
import com.fiveup.core.questionnaire.service.QuestionService;
import com.fiveup.core.questionnaire.vo.OptionsVO;
import com.fiveup.core.questionnaire.vo.QuestionVO;
import com.fiveup.core.questionnaire.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestionServiceImpl implements QuestionService {
    private final static String INVALIDATION="无效ID";
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    OptionsMapper optionsMapper;

    @Override
    public ResponseVO addQuestion(Integer paperId) {
        try {     //前端的逻辑是 添加问题时啥也没有是空的 只返回questionId 真正填写完了调updateQuestion
            Question question=new Question();
            question.setPaperId(paperId);
            questionMapper.addQuestion(question);
            return ResponseVO.buildSuccess(question.getId());
        }catch (Exception e){
            System.out.println(e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @Override
    public ResponseVO updateQuestion(QuestionVO questionVO) {
        try {
            if(questionMapper.selectByQuestionId(questionVO.getId())==null)
                return ResponseVO.buildFailure(INVALIDATION);
            Question question=new Question();
            BeanUtils.copyProperties(questionVO,question);
            questionMapper.updateQuestion(question);
            for(Options options:questionVO.getOptions()){
                OptionsVO optionsVO=new OptionsVO();
                BeanUtils.copyProperties(options,optionsVO);
                if(optionsMapper.findOption(optionsVO)==null)
                    optionsMapper.addOption(optionsVO);
                else
                    optionsMapper.updateOption(optionsVO);
            }
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            System.out.println(e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @Override
    public ResponseVO deleteQuestion(Integer questionId) {
        try {
            if(questionMapper.selectByQuestionId(questionId)==null)
                return ResponseVO.buildFailure(INVALIDATION);
            questionMapper.deleteQuestion(questionId);
            optionsMapper.deleteByQuestionId(questionId);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            System.out.println(e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }
}
