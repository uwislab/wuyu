package com.fiveup.core.remark.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.remark.common.Result;
import com.fiveup.core.remark.entity.studentScore;
import com.fiveup.core.remark.mapper.MyscoreMapper;
import com.fiveup.core.remark.mapper.ClassScoreMapper;
import com.fiveup.core.remark.mapper.re_studentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/student")
//@RequestMapping("/api/student")
public class re_studentController {

    @Autowired
    re_studentMapper studentMapper;

    @Autowired
    private MyscoreMapper myscoreMapper;


    @Autowired
    private ClassScoreMapper classScoreMapper;

    @PostMapping
    public Result<?> save(@RequestBody studentScore student){
        studentMapper.insert(student);
        return Result.success();
    }


    @PutMapping
    public Result<?> update(@RequestBody studentScore student){
        studentMapper.updateById(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        studentMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<studentScore> wrapper= Wrappers.<studentScore>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(studentScore::getStudentName,search);
        }
        Page<studentScore> studentPage = studentMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(studentPage);
    }
    @GetMapping("/boy")
    public studentScore findmsg(String studentNum,int evaluateDate){
//        student student = studentMapper.selectById(Id);
//        增加sid
        studentScore student = myscoreMapper.selectOneScore(studentNum,evaluateDate);
        return student;
    }


    @GetMapping("/studentScore")
    public studentScore findmsgStudentScore(String studentNum,int evaluateDate){
        studentScore studentScore = myscoreMapper.selectOneScore2(studentNum,evaluateDate);
        return studentScore;
    }

    @GetMapping("/average")
    public HashMap<String, Object> findsum(@RequestParam("tid") Integer Tid){
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map1 = new HashMap<>();
        double v_sum = 0;
        double i_sum = 0;
        double s_sum = 0;
        double a_sum = 0;
        double l_sum = 0;
        map.put("tid",Tid);
        List<studentScore> student = studentMapper.selectByMap(map);
//        System.out.println(123);
//        student.forEach(System.out::println);
//        System.out.println(student.size());
        for (com.fiveup.core.remark.entity.studentScore value : student) {
            v_sum += value.getMoralityScore();
            i_sum += value.getIntelligenceScore();
            s_sum += value.getPhysicalScore();
            a_sum += value.getAestheticScore();
            l_sum += value.getLabourScore();
        }
        v_sum = v_sum/student.size();
        i_sum = i_sum/student.size();
        s_sum = s_sum/student.size();
        a_sum = a_sum/student.size();
        l_sum = l_sum/student.size();
        map1.put("v_sum",v_sum);
        map1.put("i_sum",i_sum);
        map1.put("s_sum",s_sum);
        map1.put("a_sum",a_sum);
        map1.put("l_sum",l_sum);
        return map1;
    }
    @GetMapping("/sum")
    public HashMap<Integer, Object> findall(@RequestParam("tid") Integer Tid){
        HashMap<String,Object> map = new HashMap<>();
        HashMap<Integer,Object> map1 = new HashMap<>();
        int sum = 0;
        int flag = 1;
        map.put("tid",Tid);
        List<studentScore> student = studentMapper.selectByMap(map);
        for (com.fiveup.core.remark.entity.studentScore value : student){
            sum = sum + value.getMoralityScore()+value.getIntelligenceScore()+value.getPhysicalScore()+value.getAestheticScore()+ value.getLabourScore();
            map1.put(flag,sum);
            flag += 1;
            sum = 0;
        }
//        System.out.println("size of map1");
//        System.out.println(map1.size());
        return map1;
    }
//    @GetMapping("/remark")
//    public studentScore findremark(String studentNum, int evaluateDate){
////        获得当前学生信息
////        增加sid
////        student student = studentMapper.selectById(Id);
//        studentScore student = myscoreMapper.selectOneScore(studentNum,evaluateDate);
//        HashMap<String,Object> map = new HashMap<>();
//        String remark = "";
//        double v_ave = 0;
//        double i_ave = 0;
//        double s_ave = 0;
//        double a_ave = 0;
//        double l_ave = 0;
////      获得当前班级各科平均分
//        map.put("tid",Tid);
//        List<studentScore> students = studentMapper.selectByMap(map);
//        for (com.fiveup.core.remark.entity.studentScore value : students) {
//            v_ave += value.getMoralityScore();
//            i_ave += value.getIntelligenceScore();
//            s_ave += value.getPhysicalScore();
//            a_ave += value.getAestheticScore();
//            l_ave += value.getLabourScore();
//        }
//        v_ave = v_ave/students.size();
//        i_ave = i_ave/students.size();
//        s_ave = s_ave/students.size();
//        a_ave = a_ave/students.size();
//        l_ave = l_ave/students.size();
////      开始生成成绩评价
////        remark = createremarkService.createremark(v_ave,i_ave,s_ave,a_ave,l_ave,student);
////        student.setRemark(remark);
////        studentMapper.updateById(student);
//        return student;
//    }
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
        List<studentScore> students = studentMapper.selectByMap(map);
        for (com.fiveup.core.remark.entity.studentScore value : students) {
            v_ave += value.getMoralityScore();
            i_ave += value.getIntelligenceScore();
            s_ave += value.getPhysicalScore();
            a_ave += value.getAestheticScore();
            l_ave += value.getLabourScore();
        }
//        v_ave = v_ave/students.size();
//        i_ave = i_ave/students.size();
//        s_ave = s_ave/students.size();
//        a_ave = a_ave/students.size();
//        l_ave = l_ave/students.size();
        System.out.println("开始生成全部学生评语");
/*        for (com.fiveup.core.remark.entity.student value : students) {
            remark = createremarkService.createremark(v_ave,i_ave,s_ave,a_ave,l_ave,value);
            value.setRemark(remark);
            studentMapper.updateById(value);
        }!!!*/
        System.out.println("生成成功！");
    }
}
