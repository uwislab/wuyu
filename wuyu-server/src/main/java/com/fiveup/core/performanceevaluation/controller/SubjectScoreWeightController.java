package com.fiveup.core.performanceevaluation.controller;

import com.alibaba.fastjson.JSON;
import com.fiveup.core.performanceevaluation.bean.SubjectScoreWeight;
import com.fiveup.core.performanceevaluation.dto.PageDto;
import com.fiveup.core.performanceevaluation.service.SubjectScoreWeightService;
import com.fiveup.core.performanceevaluation.utils.Result;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理与学生表现相关的请求
 */
@Controller
@CrossOrigin
@RequestMapping("/weight")
public class SubjectScoreWeightController {

    @Autowired
    private SubjectScoreWeightService subjectScoreWeightService;

    /**
     * 获取所有记录
     * @return
     */
    @ResponseBody
    @RequestMapping("/all")
    public String getAllList() {
        Result result = new Result();
        List<SubjectScoreWeightVO> all = subjectScoreWeightService.getAll();
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(all);
        return JSON.toJSON(result).toString();
    }

    /**
     * 根据分页获取
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public String getAllList(@RequestBody PageDto pageDto) {
        Result result = new Result();
        // 获取记录数
        String sql = "select count(id) from subject_score_weight";
        Integer count = subjectScoreWeightService.getCount(sql);

        // 根据分页数据查询值
        List<SubjectScoreWeightVO> list = subjectScoreWeightService.getPagination(
                (pageDto.getCurrentPage() - 1) * pageDto.getPageSize(),
                        pageDto.getPageSize());
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(list);
        result.setCount(count);
        return JSON.toJSON(result).toString();
    }

    /**
     * 验证老师的权重是否已设定
     * @return
     */
    @ResponseBody
    @GetMapping("/exist")
    public String getAllList(Integer tid) {
        Result result = new Result();
        // 查询是否存在
        SubjectScoreWeightVO bySid = subjectScoreWeightService.getByTId(tid);
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(bySid);
        return JSON.toJSON(result).toString();
    }


    /**
     * 增加数据
     * @param subjectScoreWeight
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public String add(@RequestBody SubjectScoreWeight subjectScoreWeight){
        Result result = new Result();

        // 增加
        subjectScoreWeightService.add(subjectScoreWeight);

        // 查询封装后的数据
        SubjectScoreWeightVO bySid = subjectScoreWeightService.getById(subjectScoreWeight.getId());
        result.setCode(600);
        result.setData(bySid);
        result.setMsg("增加成功");
        return JSON.toJSON(result).toString();
    }

    /**
     * 更新数据
     * @param subjectScoreWeight
     * @return
     */
    @ResponseBody
    @PutMapping("/update")
    public String update(@RequestBody SubjectScoreWeight subjectScoreWeight){
        Result result = new Result();

        // 更新数据
        subjectScoreWeightService.updateById(subjectScoreWeight);

        // 查询更新后的数据
        SubjectScoreWeightVO byId = subjectScoreWeightService.getById(subjectScoreWeight.getId());
        result.setCode(600);
        result.setData(byId);
        result.setMsg("更新成功！");
        return JSON.toJSON(result).toString();
    }


    /**
     * 删除数据
     * @param subjectScoreWeight
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delete")
    public String delete(@RequestBody SubjectScoreWeight subjectScoreWeight) {
        Result result =  new Result();

        // 删除
        subjectScoreWeightService.deleteById(subjectScoreWeight.getId());

        result.setCode(600);
        result.setMsg("删除成功！");
        return JSON.toJSON(result).toString();
    }


}
