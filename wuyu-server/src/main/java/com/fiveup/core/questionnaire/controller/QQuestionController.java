package com.fiveup.core.questionnaire.controller;

import java.util.List;

import com.fiveup.core.common.result.BaseResultStatusEnum;
import com.fiveup.core.common.result.ResultBo;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.miniapp.model.ActivityFamily;
import com.fiveup.core.questionnaire.dto.QuestionDto;
import com.fiveup.core.questionnaire.enums.PaperStatus;
import com.fiveup.core.questionnaire.mapper.PaperMapper;
import com.fiveup.core.questionnaire.service.IQOptionsService;
import com.fiveup.core.questionnaire.vo.PaperQuestionVo;
import com.fiveup.core.questionnaire.vo.PaperVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.fiveup.core.questionnaire.domain.QQuestion;
import com.fiveup.core.questionnaire.service.IQQuestionService;

/**
 * 题目管理Controller
 * 
 * @author admin
 * @date
 */
@RestController
@RequestMapping("/questionnaire/question")
public class QQuestionController
{
    @Autowired
    private IQQuestionService qQuestionService;
    @Autowired
    private IQOptionsService qOptionsService;
    @Autowired
    private PaperMapper paperMapper;


    /**
     * 预览问卷题目
     */
    @GetMapping("/list")
    public ResultBo list(QQuestion qQuestion) {
        // 校验传入参数
        if (qQuestion.getPaperId() == null) {
            return ResultBo.error(BaseResultStatusEnum.SYSTEM_ERROR,"没有找到对应问卷");
        }

        // 根据 paperId 查询
        List<QQuestion> list = qQuestionService.getQuestionListByPaperId(Math.toIntExact(qQuestion.getPaperId()));

        for (QQuestion question : list) {
            question.setqOptionsList(qOptionsService.getOptionListByQuestionId(Math.toIntExact(question.getQuestId())));
        }
        
        // 返回结果
        return ResultBo.ok(list, Long.parseLong(list.size() + ""));
    }


    /**
     * 数据统计查询
     */
    @GetMapping("/listCount")
    public ResultBo AnswerCount(QQuestion qQuestion)
    {
//        System.out.println("统计"+qQuestion);
        List<QQuestion> list = qQuestionService.selectAnswerCount(qQuestion);
        return ResultBo.ok(list, Long.parseLong(list.size()+""));
    }

    /**
     * 获取题目管理详细信息
     */
    @GetMapping(value = "/{questId}")
    public ResultBo getInfo(@PathVariable("questId") Long questId)
    {
        System.out.println("查询题目"+questId);
        return ResultBo.ok(qQuestionService.selectQQuestionByQuestId(questId));
    }

    /**
     * 新增题目
     */
    @PostMapping
    public ResultBo add(@RequestBody QQuestion qQuestion)
    {
//        System.out.println("添加题目"+qQuestion);
        return ResultBo.ok(qQuestionService.insertQQuestion(qQuestion));
    }

    /**
     * 修改题目管理
     */
    @PutMapping
    public ResultBo edit(@RequestBody QQuestion qQuestion)
    {
        System.out.println("修改题目"+qQuestion);
        return ResultBo.ok(qQuestionService.updateQQuestion(qQuestion));
    }

    /**
     * 删除题目管理
     */
	@DeleteMapping("/{questIds}")
    @Transactional
    public ResultBo remove(@PathVariable Long[] questIds)
    {
        System.out.println("删除"+questIds);
        //根据题目ID删除选项
        if(questIds!=null) {
            qQuestionService.delquest(questIds);
            //根据题目ID删除回答
            qQuestionService.delanswer(questIds);
        }
        //删除题目
        return ResultBo.ok(qQuestionService.deleteQQuestionByQuestIds(questIds));
    }

    //修改题目选项
    @PostMapping("/updQuestion")
    public  ResultBo updQuestion(@RequestBody QuestionDto questionDto){
//        System.out.println("++++++++++++++==="+questionDto);
        return  ResultBo.ok(qQuestionService.updQuestion(questionDto));
    }

    //删除题目选项
    @DeleteMapping("/delQuest")
    public  ResultBo delQuest(@RequestBody QuestionDto questionDto){
//        System.out.println("------------==="+questionDto);
	    return ResultBo.ok(qQuestionService.delquest(questionDto));
    }

    //链接或二维码填写问卷
    @GetMapping("/list2")
    public ResultBo list2(QQuestion qQuestion)
    {
//        System.out.println("================="+qQuestion.getPaperId().toString());
        PaperVO paperVO = paperMapper.selectByPaperId(Integer.valueOf(qQuestion.getPaperId().toString()));
        if(!paperVO.getPaperStatus().equals(PaperStatus.STOP.getCode())&&!paperVO.getPaperStatus().equals(PaperStatus.INIT.getCode())) {
            List<QQuestion> list = qQuestionService.selectQQuestionList(qQuestion);
            PaperVO paperVO1 = paperMapper.selectByPaperId(Integer.parseInt(qQuestion.getPaperId().toString()));
            PaperQuestionVo paperQuestionVo=new PaperQuestionVo();
            paperQuestionVo.setqQuestions(list);
            paperQuestionVo.setPaperVO(paperVO1);
            return ResultBo.ok(paperQuestionVo,Long.parseLong(list.size()+"") );
        }else{
            return ResultBo.error(103,"问卷已经截止或未发布");
        }

    }

    //通过问卷id获得问卷里的问题
    @GetMapping(value="/getQuestionListByPaperId")
    public CommonResponse<List<QQuestion>> getQuestionListByPaperId(@RequestParam("paperId") String paperId) {
        Integer paperIdInt = Integer.parseInt(paperId.isEmpty()? "0" : paperId );
        List<QQuestion> questionListResp=qQuestionService.getQuestionListByPaperId(paperIdInt);
        if(questionListResp.size() == 0) {
            return CommonResponse.fail(1001, "未查询到当前问卷的问题");
        } else return CommonResponse.ok(questionListResp);
    }
}
