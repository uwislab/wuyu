package com.fiveup.core.questionnaire.controller;

import java.util.List;

import com.fiveup.core.common.result.ResultBo;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.questionnaire.po.Answer;
import com.fiveup.core.questionnaire.vo.AnswerVO;
import com.fiveup.core.questionnaire.vo.ResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fiveup.core.questionnaire.domain.QAnswer;
import com.fiveup.core.questionnaire.service.IQAnswerService;

/**
 * 答题管理Controller
 * 
 * @author admin
 * @date
 */
@RestController
@RequestMapping("/questionnaire/answer")
public class QAnswerController
{
    @Autowired
    private IQAnswerService qAnswerService;

    /**
     * 查询答题管理列表
     */
    @GetMapping("/list")
    public ResultBo list(QAnswer qAnswer)
    {
        PageHelper.startPage(qAnswer.getPageNum(), qAnswer.getPageSize());
        List<QAnswer> list = qAnswerService.selectQAnswerList(qAnswer);
        PageInfo<QAnswer> pageInfo = new PageInfo<>(list);
        System.out.println("答题内容"+qAnswer);
        return ResultBo.ok(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 获取答题管理详细信息
     */
    @GetMapping(value = "/{answerId}")
    public ResultBo getInfo(@PathVariable("answerId") Long answerId)
    {
        System.out.println("答题ID"+answerId);
        return ResultBo.ok(qAnswerService.selectQAnswerByAnswerId(answerId));
    }

    /**
     * 收集答题内容
     */
    @PostMapping
    public ResultBo add(@RequestBody List<QAnswer> qAnswerList)
    {
//        System.out.println("-----------------------==="+qAnswerList);
        return ResultBo.ok(qAnswerService.insertQAnswer(qAnswerList));
    }

    /**
     * 修改答题管理
     */
    @PutMapping
    public ResultBo edit(@RequestBody QAnswer qAnswer)
    {
        System.out.println("修改答题"+qAnswer);
        return ResultBo.ok(qAnswerService.updateQAnswer(qAnswer));
    }

    /**
     * 删除答题管理
     */
	@DeleteMapping("/{answerIds}")
    public ResultBo remove(@PathVariable Long[] answerIds)
    {
        System.out.println("删除答题"+answerIds);
        return ResultBo.ok(qAnswerService.deleteQAnswerByAnswerIds(answerIds));
    }

    @PostMapping("/addAnswers")
    public CommonResponse addAnswers(@RequestBody List<QAnswer> answerList){
        int count = qAnswerService.addAnswers(answerList);
        if (count == 0) {
            return CommonResponse.fail(1001, "添加失败");
        } else {
            return CommonResponse.ok(answerList);
        }

    }

    @GetMapping("/getAnswerListByPaperId")
    public CommonResponse getAnswerListByPaperId(QAnswer qAnswer)
    {
        List<QAnswer> AnswerList = qAnswerService.getAnswerListByPaperId(qAnswer);
        return CommonResponse.ok(AnswerList);
    }
}
