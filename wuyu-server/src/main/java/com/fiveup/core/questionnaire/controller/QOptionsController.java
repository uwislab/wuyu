package com.fiveup.core.questionnaire.controller;

import java.util.Arrays;
import java.util.List;

import com.fiveup.core.common.result.ResultBo;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.questionnaire.domain.QQuestion;
import com.fiveup.core.questionnaire.service.IQOptionsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fiveup.core.questionnaire.domain.QOptions;

/**
 * 选项管理Controller
 *
 * @author admin
 * @date
 */
@RestController
@RequestMapping("/questionnaire/options")
public class QOptionsController {
    @Autowired
    private IQOptionsService qOptionsService;

    /**
     * 查询选项管理列表
     */
    @GetMapping("/list")
    public ResultBo list(QOptions qOptions) {
        PageHelper.startPage(qOptions.getPageNum(), qOptions.getPageSize());
        List<QOptions> list = qOptionsService.selectQOptionsList(qOptions);
        System.out.println("查询选项" + qOptions);
        PageInfo<QOptions> pageInfo = new PageInfo<>(list);
        return ResultBo.ok(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 获取选项管理详细信息
     */
    @GetMapping(value = "/{optId}")
    public ResultBo getInfo(@PathVariable("optId") Long optId) {
        System.out.println("获取选项" + optId);
        return ResultBo.ok(qOptionsService.selectQOptionsByOptId(optId));
    }

    /**
     * 新增选项管理
     */
    @PostMapping
    public ResultBo add(@RequestBody QOptions qOptions) {
        System.out.println("添加选项" + qOptions);
        return ResultBo.ok(qOptionsService.insertQOptions(qOptions));
    }

    /**
     * 修改选项管理
     */
    @PutMapping
    public ResultBo edit(@RequestBody QOptions qOptions) {
        System.out.println("修改选项" + qOptions);
        return ResultBo.ok(qOptionsService.updateQOptions(qOptions));
    }

    /**
     * 删除选项管理
     */
    @DeleteMapping("/{optIds}")
    public ResultBo remove(@PathVariable Long[] optIds) {
        System.out.println("删除选项" + optIds);
        return ResultBo.ok(qOptionsService.deleteQOptionsByOptIds(optIds));
    }


    /**
     * 根据问题id获取选项列表
     */
    @GetMapping(value = "/getOptionListByPaperId")
    public CommonResponse<List<QOptions>> getOptionListByPaperId(@RequestParam String paperId) {
        Integer paperIdInt = Integer.parseInt(paperId.isEmpty()? "0" : paperId );
        List<QOptions> optionListResp = qOptionsService.getOptionListByPaperId(paperIdInt);
        if (optionListResp.size() == 0) {
            return CommonResponse.fail(1001, "未查询到当前题目所有的选项");
        } else return CommonResponse.ok(optionListResp);
    }
}
