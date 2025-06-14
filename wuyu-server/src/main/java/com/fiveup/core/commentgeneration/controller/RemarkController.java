package com.fiveup.core.commentgeneration.controller;

import com.alibaba.fastjson.JSON;
import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.commentgeneration.bean.Subject;
import com.fiveup.core.commentgeneration.service.CorpusService;
import com.fiveup.core.commentgeneration.service.SubjectService;
import com.fiveup.core.commentgeneration.utils.RemarkUtil;
import com.fiveup.core.commentgeneration.utils.Result;
import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.service.StudentPerformanceService;
import com.fiveup.core.performanceevaluation.service.SubjectScoreWeightService;
import com.fiveup.core.performanceevaluation.utils.StudentPerformanceUtil;
import com.fiveup.core.performanceevaluation.vo.Average;
import com.fiveup.core.performanceevaluation.vo.StudentPerformanceVO;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 处理与评价的请求
 */
@CrossOrigin
@Controller
@RequestMapping("/remark")
public class RemarkController {
    @Autowired
    private StudentPerformanceService studentPerformanceService;

    @Autowired
    private SubjectScoreWeightService subjectScoreWeightService;

    @Autowired
    private CorpusService corpusService;

    @Autowired
    private SubjectService subjectService;

    private List<StudentPerformanceVO> list;

    /**
     *查询学生个人相关信息
     * @param sid
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    public String getPersonalRemark(Integer sid) {
        Result<V> result = new Result<V>();
        // 根据sid查询对应的学生表现
        StudentPerformanceVO studentPerformanceVO = studentPerformanceService.getBySid(sid);
        if(studentPerformanceVO != null) {
            result.setCode(600);
            result.setData(studentPerformanceVO);
            result.setMsg("查询成功！");
        } else {
            result.setCode(602);
            result.setMsg("学号不存在！");
        }
        return JSON.toJSON(result).toString();
    }

    /**
     * 查询教师对应的班级评语册
     * @param tid
     * @param page
     * @return
     */
    @ResponseBody
    @GetMapping("/class")
    public String getClassEvaluationBook(Integer tid,
                                         @RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "export", required = false) Integer export) {
        Result<V> result = new Result<V>();
        // 获取教师ID对应的班级平均分
        System.out.println("搜嘎预防使用它属于头发啊："+tid);
        System.out.println("大大大电视："+page);
        Average averageByTid = studentPerformanceService.getAverageByTid(tid);

        // 生成评语并计算总评成绩后装配排序
        List<Corpus> corpuses = corpusService.getList();
        List<Subject> subjects = subjectService.getAll();

        RemarkUtil remarkUtil = new RemarkUtil();
        remarkUtil.setCorpus(corpuses);
        remarkUtil.setSubjects(subjects);
        remarkUtil.setAverage(averageByTid);

        if(list == null || (list.size() > 0 &&
                list.get(0).getTeacher().getTeacherID() != tid)) {
            // 没有生成过或者查询的教师ID变化了
            // 获取班级所有学生并计算其总评成绩
            List<StudentPerformanceVO> byTId = studentPerformanceService.getByTId(tid);
            // 获取教师ID对应的权重
            SubjectScoreWeightVO weightById = subjectScoreWeightService.getByTId(tid);
            for (StudentPerformanceVO studentPerformanceVO : byTId) {
                // 计算
                // 方便计算和数据库更新
                StudentPerformance studentPerformance = new StudentPerformance();
                BeanUtils.copyProperties(studentPerformanceVO, studentPerformance);

                BigDecimal totalScore = StudentPerformanceUtil.computeTotalScore(studentPerformance, weightById);

                studentPerformanceVO.setTotalScore(totalScore);

                // 生成评语
                String remark = remarkUtil.generatePersonalRemark(studentPerformance);
                // 替换生成的评语
                studentPerformanceVO.setRemark(remark);

                // 设置评语并更新数据库
                studentPerformance.setRemark(remark);

                studentPerformanceService.updateById(studentPerformance);
            }

            // 根据总评成绩进行排序
            byTId.sort(Comparator.comparing(StudentPerformanceVO::getTotalScore).reversed());

            list = new ArrayList<>();
            for (StudentPerformanceVO studentPerformanceVO : byTId) {
                list.add(studentPerformanceVO);
            }
        }

        // 班级评语册
        if(export != null) {
            result.setCode(600);
            result.setData(list);
            result.setMsg("导出成功！");
        } else {
            if(page == 1) {
                result.setCode(600);
                result.setCount(list.size());
                String s = remarkUtil.generateClassRemark();
                result.setData(s);
            } else {
                result.setCode(600);
                result.setCount(list.size());
                result.setData(list.get(page - 2));
            }
        }
        return JSON.toJSON(result).toString();
    }

    /**
     * 更新评价
     * @param studentPerformance
     * @return
     */
    @ResponseBody
    @PutMapping("/update")
    public String update(@RequestBody StudentPerformance studentPerformance) {
        Result<V> result = new Result<V>();
        studentPerformanceService.updateById(studentPerformance);
        result.setCode(600);
        result.setMsg("更新成功！");
        return JSON.toJSON(result).toString();
    }


    /**
     *生成评语
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/generate")
    public String generate(Integer id) {
        Result<V> result = new Result<V>();
        // 查询当前的学生表现数据
        StudentPerformanceVO studentPerformanceVO = studentPerformanceService.getById(id);

        // 获取教师ID对应的班级平均分
        Average averageByTid = studentPerformanceService.getAverageByTid(studentPerformanceVO.getTeacher().getTeacherID());

        List<Corpus> corpuses = corpusService.getList();
        List<Subject> subjects = subjectService.getAll();

        RemarkUtil remarkUtil = new RemarkUtil();
        remarkUtil.setCorpus(corpuses);
        remarkUtil.setSubjects(subjects);
        remarkUtil.setAverage(averageByTid);

        StudentPerformance studentPerformance = new StudentPerformance();
        BeanUtils.copyProperties(studentPerformanceVO, studentPerformance);

        // 生成评语
        String remark = remarkUtil.generatePersonalRemark(studentPerformance);
        studentPerformance.setRemark(remark);

        // 更新到数据库
        studentPerformanceService.updateById(studentPerformance);

        result.setCode(600);
        result.setMsg("生成成功！");
        return JSON.toJSON(result).toString();
    }
}
