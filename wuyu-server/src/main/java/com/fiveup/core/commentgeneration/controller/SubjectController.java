package com.fiveup.core.commentgeneration.controller;

import com.alibaba.fastjson.JSON;
import com.fiveup.core.commentgeneration.bean.Subject;
import com.fiveup.core.commentgeneration.service.SubjectService;
import com.fiveup.core.commentgeneration.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理与科目相关的请求
 */
@CrossOrigin
@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 查询所有的科目数据
     * @return
     */
    @ResponseBody
    @GetMapping("/all")
    public String getAll(){
        Result<V> result = new Result<V>();

        // 查询所有科目信息
        List<Subject> all = subjectService.getAll();
        result.setCode(600);
        result.setData(all);
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }
}
