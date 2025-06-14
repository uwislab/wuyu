package com.fiveup.core.commentgeneration.controller;

import com.alibaba.fastjson.JSON;
import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.commentgeneration.service.CorpusService;
import com.fiveup.core.commentgeneration.utils.Result;
import com.fiveup.core.commentgeneration.vo.CorpusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * 处理与语料库相关的请求
 */
@CrossOrigin
@Controller
@RequestMapping("/corpus")
public class CorpusController {

    @Autowired
    private CorpusService corpusService;

    /**
     * 获取所有语料库信息
     * @return
     */
    @ResponseBody
    @GetMapping("/all")
    public String getAll() {
        Result result = new Result();

        // 查询所有语料库数据
        List<CorpusVO> all = corpusService.getAll();
        result.setCode(600);
        result.setData(all);
        result.setCount(all.size());
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }

    /**
     * 增加数据
     * @param corpus
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public String add(@RequestBody Corpus corpus){
        Result result = new Result();

        // 增加
        corpusService.add(corpus);
        result.setCode(600);
        result.setMsg("增加成功！");
        return JSON.toJSON(result).toString();
    }

    /**
     * 删除数据 使用逻辑删除
     * @param corpus
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delete")
    public String delete(@RequestBody Corpus corpus){
        Result result = new Result();

        // 删除
        corpusService.deleteById(corpus.getId());
        result.setCode(600);
        result.setMsg("删除成功！");
        return JSON.toJSON(result).toString();
    }

    /**
     * 更新数据
     * @param corpus
     * @return
     */
    @ResponseBody
    @PutMapping("/update")
    public String update(@RequestBody Corpus corpus){
        Result result = new Result();

        // 更新
        corpusService.updateById(corpus);
        result.setCode(600);
        result.setMsg("更新成功！");
        return JSON.toJSON(result).toString();
    }

    /**
     * 批量删除数据，使用逻辑删除
     * @param ids 待删除的ID列表
     * @return 删除结果
     */
    @ResponseBody
    @PostMapping("/deleteAll")
    public Result deleteAll(@RequestBody Integer[] ids) {
        Result result = new Result();
        try {
            int rowsAffected = corpusService.deleteAll(ids);
            if (rowsAffected == ids.length) {
                result.setCode(600);
                result.setMsg("批量删除成功！");
            } else {
                result.setCode(500);
                result.setMsg("部分删除失败，请重试！");
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("删除时出现错误：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/list")
    public List<Corpus> getCorpusByPage(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
    ){

        List<Corpus> corpuses=corpusService.getCorpusByPage(pageNum,pageSize);

        return corpuses;
    }



    /**
     * 条件查询语料库
     * @return
     */
    @ResponseBody
    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") Integer subjectId,
                         @RequestParam(defaultValue = "") String comment) {
        Result result = new Result();
//        System.out.println("-------------subject:"+subjectId);
//        System.out.println("----------------comment:"+comment);
        List<CorpusVO> corpusVOS = corpusService.search(subjectId,comment);
        System.out.println(corpusVOS);
        result.setCode(600);
        result.setData(corpusVOS);
//        result.setCount(corpusVOS.size());
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }


}
