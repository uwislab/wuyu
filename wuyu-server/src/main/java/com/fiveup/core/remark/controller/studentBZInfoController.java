package com.fiveup.core.remark.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.remark.common.Result;
import com.fiveup.core.remark.entity.student;
import com.fiveup.core.remark.entity.studentInfo;
import com.fiveup.core.remark.entity.studentAllInfo;
import com.fiveup.core.remark.entity.studentScore;
import com.fiveup.core.remark.mapper.MyscoreMapper;
import com.fiveup.core.remark.mapper.StudentScoreALLMapper;
import com.fiveup.core.remark.mapper.studentBZinfoMapper;
import com.fiveup.core.remark.mapper.studentAllInfoMapper;
import com.fiveup.core.remark.service.studentAllInfoService;
import com.fiveup.core.remark.service.createremarkService;
import com.fiveup.core.remark.service.studentBZinfoService;
import com.fiveup.core.remark.service.studentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/studentInfo")
//@RequestMapping("/api/student")
public class studentBZInfoController {

    @Autowired
    studentBZinfoMapper studentMapper;

    @Autowired
    studentBZinfoService studentService;

    @Autowired
    studentAllInfoService studentAllInfoService;

    @Autowired
    studentScoreService studentScoreService;

    @Autowired
    studentAllInfoMapper studentAllMapper;

    @Autowired
    StudentScoreALLMapper studentScoreMapper;

    @Autowired
    private MyscoreMapper myscoreMapper;


    @PostMapping
    public Result<?> save(@RequestBody studentInfo student){
        studentMapper.insert(student);
        return Result.success();
    }


    @PutMapping
    public Result<?> update(@RequestBody studentInfo student){
        studentMapper.updateById(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        studentScoreMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/edit_get")
    public Result<?> edit_get(@RequestParam("id") Integer id,
                              @RequestParam("remark") String remark){
        studentScore student = new studentScore();
        student.setId(id);
        student.setRemark(remark);
        System.out.println("前端传来的修改后的学生信息是：");
        System.out.println(student);
        studentScoreMapper.updateById(student);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String value,
                              @RequestParam(defaultValue = "") String valueClass,
                              @RequestParam(defaultValue = "") String valueZT){
        LambdaQueryWrapper<studentInfo> wrapper= Wrappers.<studentInfo>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(studentInfo::getStudentName,search);
        }
        if(StrUtil.isNotBlank(value))
        {
            Integer num1 = Integer.parseInt(value);
            wrapper.like(studentInfo::getGradeId,num1);
        }


        if(StrUtil.isNotBlank(valueClass)){
            Integer num2 = Integer.parseInt(valueClass);
            wrapper.like(studentInfo::getClassId,num2);
        }
        if(StrUtil.isNotBlank(valueZT)){
            Integer num3 = Integer.parseInt(valueZT);
            wrapper.like(studentInfo::getIsreview,num3);
        }
        Page<studentInfo> studentPage = studentMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(studentPage);
    }
    @GetMapping("/boy")//根据学生学号查找，不需要使用，舍弃
    public studentScore findmsg( String studentNum, int evaluateDate){
//        student student = studentMapper.selectById(Id);
//        增加sid
        QueryWrapper<studentScore> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("studentNum",studentNum);
        List<studentScore>scoreList=myscoreMapper.selectList(queryWrapper);
//        studentScore list = myscoreMapper.selectOneScore(studentNum,evaluateDate);
        System.out.println(scoreList);
//        studentScore student = studentScoreMapper.selectByStudentNumAndDate(studentNum,evaluateDate);
        return scoreList.get(0);
    }
    @PostMapping("/batch")//批量通过审阅，使用的是数据库中isreview字段判断
    public Result ReviewBatch(@RequestBody List<Integer> ids) {
        if(ids.size()==0)
        {
            return Result.error(500,"为空");
        }
        List<studentScore> userList = new ArrayList<>();
        for (Integer id : ids) {
            studentScore student = new studentScore();
            student.setId(id);
            student.setIsreview(1);
            student.setTid(1);
            userList.add(student);
            studentScore stu = new studentScore();
            stu.setId(id);
            stu.setRemark("该学生品学兼优，学风良好，敢于拼搏，乐于助人");
            System.out.println("前端传来的修改后的学生信息是：");
            System.out.println(stu);
            studentScoreMapper.updateById(stu);
        }
        studentScoreService.updateBatchById(userList);
        return Result.success(true);
    }

    @GetMapping("/findAll")//分页查找所有符合条件的条目
    public Result<HashMap> findAllmsg(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search,
                                      @RequestParam(defaultValue = "") String value,
                                      @RequestParam(defaultValue = "") String valueClass,
                                      @RequestParam(defaultValue = "") String valueZT){
        Integer start = (pageNum-1) * pageSize;
        List<studentAllInfo> studentAll = studentAllMapper.findstuAllInfo(search,value,valueClass,valueZT,start,pageSize);
        HashMap<String,Object> map = new HashMap();
        Integer Count = studentAllMapper.findTotal(search,value,valueClass,valueZT);
        map.put("count",Count);
        map.put("list",studentAll);
        return new Result(map);
    }
    @GetMapping("/allremark")
    public void allremark(@RequestParam("tid") Integer Tid){
        HashMap<String,Object> map = new HashMap<>();
        String remark = "";
        double v_ave = 0;
        double i_ave = 0;
        double s_ave = 0;
        double a_ave = 0;
        double l_ave = 0;
        map.put("tid",Tid);
        List<studentScore> students = studentScoreMapper.selectByMap(map);
        for (com.fiveup.core.remark.entity.studentScore value : students) {
            v_ave += value.getMoralityScore();
            i_ave += value.getIntelligenceScore();
            s_ave += value.getPhysicalScore();
            a_ave += value.getAestheticScore();
            l_ave += value.getLabourScore();
        }
        v_ave = v_ave/students.size();
        i_ave = i_ave/students.size();
        s_ave = s_ave/students.size();
        a_ave = a_ave/students.size();
        l_ave = l_ave/students.size();
        System.out.println("开始生成全部学生评语");
        for (com.fiveup.core.remark.entity.studentScore value : students) {
            remark = createremarkService.createremark(v_ave,i_ave,s_ave,a_ave,l_ave,value);
            value.setRemark(remark);
            studentScoreMapper.updateById(value);
        }
        System.out.println("生成成功！");
    }

}
