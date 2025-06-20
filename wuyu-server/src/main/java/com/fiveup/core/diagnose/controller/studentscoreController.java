package com.fiveup.core.diagnose.controller;


import com.fiveup.core.demonstrate.entity.GradeScore;
import com.fiveup.core.diagnose.bean.*;
import com.fiveup.core.diagnose.mapper.SplanMapper;
import com.fiveup.core.diagnose.service.studentscoreService;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/diagnose")
public class studentscoreController {
    List<student_score> listGrade = new ArrayList<>();
    List<student_score> listClass = new ArrayList<>();
    List<student_score> listPreGrade = new ArrayList<>();
    List<student_score> listPreClass = new ArrayList<>();
    List<student_gradesScore> listone = new ArrayList<>();

    @Autowired
    studentscoreService stService;
    @Autowired
    SplanMapper splanMapper;


    @RequestMapping("/getGradeScoreByGrade")
    @ResponseBody
    public List<GradeScoreBean> getGradeScore(@RequestParam("grade") int grade) {

        return stService.getGradeScoreBean(grade);

    }

    /*返回全年级最近一次无成绩*/
    @RequestMapping("/allScore")
    @ResponseBody
    public List<student_gradesScore> getallScore() {
        List<student_gradesScore> listonegrade = new ArrayList<>();
        List<student_gradesScore> listtwograde = new ArrayList<>();
        List<student_gradesScore> listthreegrade = new ArrayList<>();
        List<student_gradesScore> listfourgrade = new ArrayList<>();
        List<student_gradesScore> listfivegrade = new ArrayList<>();
        List<student_gradesScore> listsixgrade = new ArrayList<>();
        listonegrade = stService.getgradeoneScore();
        listtwograde = stService.getgradetwoScore();
        listthreegrade = stService.getgradethreeScore();
        listfourgrade = stService.getgradefourScore();
        listfivegrade = stService.getgradefiveScore();
        listsixgrade = stService.getgradesixScore();
        listonegrade.addAll(listtwograde);
        listonegrade.addAll(listthreegrade);
        listonegrade.addAll(listfourgrade);
        listonegrade.addAll(listfivegrade);
        listonegrade.addAll(listsixgrade);
        student_gradesScore temp = new student_gradesScore();
        for (int i = 0; i < listonegrade.size() - 1; i++) {
            for (int j = 0; j < listonegrade.size() - 1 - i; j++) {
                if (listonegrade.get(j).getStudentId() < listonegrade.get(j + 1).getStudentId()) {
                    temp = listonegrade.get(j);
                    listonegrade.set(j, listonegrade.get(j + 1));
                    listonegrade.set(j + 1, temp);
                }
            }
        }
        return listonegrade;
    }

    @GetMapping("/classscore")
    @ResponseBody
    /*返回班级平均成绩与年级平均成绩的控制层接口*/
    public float[] SelectScoreByClass(@RequestParam("grade") int grade, @RequestParam("sclass") int sclass) {
        listGrade = stService.SelectScoreByGrade(grade);
        listClass = stService.SelectScoreByClass(grade, sclass);
        return stService.gradeClasssAvgcore(listGrade, listClass);
    }

    @RequestMapping("/studentscore")
    @ResponseBody
    /*返回学生个人成绩的控制层接口*/
    public List<student_score> SelectScoreByKey(@RequestParam("name") String name, @RequestParam("id") Long id) {
        return stService.SelectScoreByKey(name, id);
    }


    @GetMapping("/class/average/scores")
    @ResponseBody
    public Map<String, Float> getClassAverageScores(@RequestParam("grade") int grade,
                                                    @RequestParam("sclass") int sclass) {
        // 获取该班级所有学生的成绩列表
        List<student_score> scores = stService.SelectScoreByClass(grade, sclass);

        // 构造返回结果
        Map<String, Float> result = new HashMap<>();

        if (scores == null || scores.isEmpty()) {
            // 如果没有数据，返回默认值 0
            result.put("德育", 0.0f);
            result.put("智育", 0.0f);
            result.put("体育", 0.0f);
            result.put("美育", 0.0f);
            result.put("劳育", 0.0f);
            return result;
        }

        // 计算平均分
        float[] avgScores = studentscoreService.avaragescoreNew(scores);

        result.put("德育", avgScores[0]);
        result.put("智育", avgScores[1]);
        result.put("体育", avgScores[2]);
        result.put("美育", avgScores[3]);
        result.put("劳育", avgScores[4]);

        return result;
    }

    @GetMapping("/avgFiveScore")
    @ResponseBody
    public CommonResponse<Map<String, Float>> getFiveAverageScores(@RequestParam("grade") Integer grade, Integer clazz) {
        return Optional.ofNullable(clazz)
                .map(c -> {
                    List<student_score> student_scores = stService.SelectScoreByClass(grade, c);
                    float[] avgScores = studentscoreService.avaragescore(student_scores);

                    // 构造返回结果
                    Map<String, Float> result = new HashMap<>();
                    result.put("德育", avgScores[0]);
                    result.put("智育", avgScores[1]);
                    result.put("体育", avgScores[2]);
                    result.put("美育", avgScores[3]);
                    result.put("劳育", avgScores[4]);
                    return CommonResponse.ok(result);
                }).orElseGet(() -> {
                    // 获取该年级所有学生的成绩列表
                    List<student_score> scores = stService.SelectScoreByGrade(grade);

                    // 计算平均分
                    float[] avgScores = studentscoreService.avaragescore(scores);

                    // 构造返回结果
                    Map<String, Float> result = new HashMap<>();
                    result.put("德育", avgScores[0]);
                    result.put("智育", avgScores[1]);
                    result.put("体育", avgScores[2]);
                    result.put("美育", avgScores[3]);
                    result.put("劳育", avgScores[4]);
                    return CommonResponse.ok(result);
                });
    }


    @GetMapping("/grade/average/scores")
    @ResponseBody
    public Map<String, Float> getGradeAverageScores(@RequestParam("grade") int grade) {
        // 获取该年级所有学生的成绩列表
        List<student_score> scores = stService.SelectScoreByGrade(grade);

        // 计算平均分
        float[] avgScores = studentscoreService.avaragescore(scores);

        // 构造返回结果
        Map<String, Float> result = new HashMap<>();
        result.put("德育", avgScores[0]);
        result.put("智育", avgScores[1]);
        result.put("体育", avgScores[2]);
        result.put("美育", avgScores[3]);
        result.put("劳育", avgScores[4]);

        return result;
    }


    /*返回学生所在班级成绩的控制层接口*/
    @RequestMapping("/avgscore")
    @ResponseBody
    public float[] getAvgscoreofStudentClass(@RequestParam("name") String name, @RequestParam("id") Long id) {
        student_classgrade scg = new student_classgrade();
        scg = stService.getClass(name, id);
        listClass = stService.SelectScoreByClass(scg.getStudentGrade(), scg.getStudentClassNumber());
        return stService.classAvgscore(listClass);
    }

    /*返回班级分析*/
    @RequestMapping("/classanalyse")
    @ResponseBody
    public String classAnalyse(@RequestParam("grade") int grade, @RequestParam("sclass") int sclass) {
        float[] avgscore = new float[10];
        listGrade = stService.SelectScoreByGrade(grade);
        listClass = stService.SelectScoreByClass(grade, sclass);
        avgscore = stService.gradeClasssAvgcore(listGrade, listClass);
        return stService.getclassAnalyse(avgscore, listClass);
    }

    /*返回学生成绩分析*/
    @RequestMapping("/studentscoreanalyse")
    @ResponseBody
    public String classAnalyse(@RequestParam("name") String name, @RequestParam("id") Long id) {
        /*获取学生计划*/
        Student_plan splan = new Student_plan();
        splan = splanMapper.getPlanByidone(name, id);
        /*获取学生班级*/
        List<student_score> studentscore = new ArrayList<>();
        /*得到学生本次成绩与上次成绩*/
        studentscore = stService.SelectScoreByKey(name, id);
        /*计算学生本次成绩总分*/
        int sumScore = studentscore.get(0).getsDeyu() + studentscore.get(0).getsZhiyu() +
                studentscore.get(0).getsTiyu() + studentscore.get(0).getsMeiyu() +
                studentscore.get(0).getsLaoyu();
        student_classgrade scg = new student_classgrade();
        scg = stService.getClass(name, id);
        /*获取所在班级本次成绩*/
        listClass = stService.SelectScoreByClass(scg.getStudentGrade(), scg.getStudentClassNumber());
        /*获取所在年级本次成绩*/
        listGrade = stService.SelectScoreByGrade(scg.getStudentGrade());
        /*计算本次成绩班级年级排名*/
        int ClassScoreRank = stService.ScoreRank(listClass, sumScore);
        int GradeScoreRank = stService.ScoreRank(listGrade, sumScore);


        /*计算上次学生成绩总分*/
        int sumPreScore = studentscore.get(1).getsDeyu() + studentscore.get(1).getsZhiyu() +
                studentscore.get(1).getsTiyu()
                + studentscore.get(1).getsMeiyu() + studentscore.get(1).getsLaoyu();
        /*获取所在班级上次成绩*/
        listPreClass = stService.SelectPreScoreByClass(scg.getStudentGrade(), scg.getStudentClassNumber());
        /*获取所在年级上次成绩*/
        listPreGrade = stService.SelectPreScoreByGrade(scg.getStudentGrade());
        /*计算上次成绩班级年级排名*/
        int ClassPreScoreRank = stService.ScoreRank(listPreClass, sumPreScore);
        int GradePreScoreRank = stService.ScoreRank(listPreGrade, sumPreScore);

        /*计算班级排名，年级排名的上升下降*/
        int classRankCompare = ClassScoreRank - ClassPreScoreRank;
        int gradeRankCompare = GradeScoreRank - GradePreScoreRank;
        /*获取班级平均分*/
        float[] classAvgscore = new float[5];
        classAvgscore = stService.classAvgscore(listClass);
        /*计算计划完成度*/
        float[] planComplition = new float[5];
        planComplition = stService.studentPlanComplition(name, id, studentscore);
        /*分析学生成绩，计划*/
        return stService.studentscoreAnalyse(classRankCompare, gradeRankCompare, ClassScoreRank, GradeScoreRank, sumScore, studentscore, classAvgscore, planComplition, splan);
    }

    /*返回学生计划完成度*/
    @RequestMapping("/studentplancomplete")
    @ResponseBody
    public float[] studentPlanComplition(@RequestParam("name") String name, @RequestParam("id") Long id) {
        List<student_score> studentscore = new ArrayList<>();
        studentscore = stService.SelectScoreByKey(name, id);
        return stService.studentPlanComplition(name, id, studentscore);

    }

    /*获取所有的年级*/
    @RequestMapping("/GetGrades")
    @ResponseBody
    public int[] getGrades() {
        return stService.getGrades();
    }

    /*获取所有班级*/
    @RequestMapping("/GetClasses")
    @ResponseBody
    public int[] getClasses(@RequestParam("grade") int grade) {
        return stService.getClasses(grade);
    }
}
