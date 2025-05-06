package com.fiveup.core.questionnaire.service.impl;

import com.fiveup.core.questionnaire.enums.PaperStatus;
import com.fiveup.core.questionnaire.mapper.AnswerMapper;
import com.fiveup.core.questionnaire.mapper.PaperMapper;
import com.fiveup.core.questionnaire.service.AnswerService;
import com.fiveup.core.questionnaire.vo.AnswerVO;
import com.fiveup.core.questionnaire.vo.PaperVO;
import com.fiveup.core.questionnaire.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: AnswerServiceImpl
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:22
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    private final static String EARLY="问卷未开始发放";
    private final static String INVALIDATION="问卷已失效";
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    PaperMapper paperMapper;

    @Override
    public ResponseVO addAnswers(List<AnswerVO> answerVOList) {
        try {
            int paperId=answerVOList.get(0).getPaperId();
            PaperVO paperVO=paperMapper.selectByPaperId(paperId);
            if(paperVO.getStartTime()!=null && paperVO.getEndTime()!=null){
                if(paperVO.getPaperStatus()== PaperStatus.INIT.getCode())
                    return ResponseVO.buildFailure(EARLY);
                if(paperVO.getPaperStatus()== PaperStatus.STOP.getCode())
                    return ResponseVO.buildFailure(INVALIDATION);
            }
            String uuid = UUID.randomUUID().toString();  //转化为String对象
            uuid = uuid.replace("-", ""); //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
            for(AnswerVO answerVO:answerVOList) {
                answerVO.setUser_uuid(uuid);
                answerMapper.addAnswer(answerVO);
            }
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            System.out.println(e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @Override
    public ResponseVO reviewAnswers(int paperId) {
        List res = new ArrayList();
        List<String> users = answerMapper.selectUUIDbyPaper(paperId);
        for (String user_uuid:users){
            List<AnswerVO> answerVOS = answerMapper.selectAnswersByUUID(user_uuid);
            List<String> ansString = new ArrayList<>();
            ansString.add(answerVOS.get(0).getCreateTime());
            for(AnswerVO answerVO:answerVOS) {
                if (answerVO.getQuestionType() == 3){
                    ansString.add(answerVO.getAnswerContent());
                }else if (answerVO.getQuestionType()==2){
                    String[] seqs = answerVO.getAnswerContent().split(",");
                    String tempRes = "";
                    for(String seq:seqs){
                        tempRes+=answerMapper.selectOption(answerVO.getQuestionId(),seq);
                        tempRes+=";";
                    }
                    ansString.add(tempRes);
                }else if(answerVO.getQuestionType()==1){
                    ansString.add(answerMapper.selectOption(answerVO.getQuestionId(),answerVO.getAnswerContent()));
                }
            }
            res.add(ansString);
        }
        return ResponseVO.buildSuccess(res);
    }
}

