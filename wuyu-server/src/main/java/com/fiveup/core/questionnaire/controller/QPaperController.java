package com.fiveup.core.questionnaire.controller;

import java.util.List;

import com.fiveup.core.common.result.ResultBo;
import com.fiveup.core.questionnaire.service.IQQuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.fiveup.core.questionnaire.domain.QPaper;
import com.fiveup.core.questionnaire.service.IQPaperService;

/**
 * 问卷信息Controller
 *
 * @author
 * @date
 */
@RestController
@RequestMapping("questionnaire/paper")
public class QPaperController {
    @Autowired
    private IQPaperService qPaperService;
    @Autowired
    private IQQuestionService qQuestionService;

    /**
     * 获取整个问卷列表
     */
    @PostMapping("/list")
    public ResultBo list(@RequestBody QPaper qPaper) {
//        PageHelper.startPage(qPaper.getPageNum(), qPaper.getPageSize());
        qPaper.setPageNum((qPaper.getPageNum()-1)*qPaper.getPageSize());
        System.out.println(qPaper.getPageSize());
        System.out.println(qPaper.getPageNum());
        List<QPaper> list = qPaperService.selectQPaperList(qPaper);
        System.out.println(list);
        PageInfo<QPaper> pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(qPaperService.selectCount(qPaper));
        System.out.println(pageInfo.getTotal());
        return ResultBo.ok(pageInfo.getList(), pageInfo.getTotal());
    }


    @GetMapping(value = "/{paperId}")
    public ResultBo getInfo(@PathVariable("paperId") Long paperId) {
        return ResultBo.ok(qPaperService.selectQPaperByPaperId(paperId));
    }

    /**
     * 新增问卷基本信息
     */
    @PostMapping("/add")
    public ResultBo add(@RequestBody QPaper qPaper) {
        return ResultBo.ok(qPaperService.insertQPaper(qPaper));
    }

    /**
     * 修改问卷基本信息
     */
    @PutMapping("/edit")
    public ResultBo edit(@RequestBody QPaper qPaper) {
        return ResultBo.ok(qPaperService.updateQPaper(qPaper));
    }

    /**
     * 删除对应ID问卷
     */
    @DeleteMapping("/{paperIds}")
    @Transactional
    public ResultBo remove(@PathVariable Long[] paperIds) {

        //将多个问卷分开处理
        if(paperIds!=null){
            for(int i=0;i<paperIds.length;i++) {
                //根据问卷ID查询题目ID
                if (paperIds.length>=1) {
                    Long[] questId=null;
                    questId = qPaperService.selectQuestId(paperIds[i]);
                    //根据题目ID删除选项
                    if (questId.length >= 1) {
                        qQuestionService.delquest(questId);
                        //根据题目ID删除回答
                        qQuestionService.delanswer(questId);
                        //删除题目
                        qQuestionService.deleteQQuestionByQuestIds(questId);
                    }
                }
                System.out.println("3");
            }
        }
        //删除问卷
        return ResultBo.ok(qPaperService.deleteQPaperByPaperIds(paperIds));
    }
}
