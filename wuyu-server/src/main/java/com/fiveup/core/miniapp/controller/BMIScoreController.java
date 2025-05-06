package com.fiveup.core.miniapp.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.miniapp.common.BMIStandard;
import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import com.fiveup.core.miniapp.service.BMIScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


/**
 * @author 史林
 * @date 2022/10/10
 * @function 体重指标测量值接收，按照年级、性别的不同标准，得出分数与等级
 */
@RestController
@RequestMapping("/minapp")
public class BMIScoreController {

    @Autowired
    private BMIScoreService bmiScoreService;

    @GetMapping("/getBMIScoreList")
    public CommonResponse<List<StuScore>> getBMIScoreList(@RequestParam String grade,
                                                          @RequestParam String className) {
        System.out.println("grade :" + grade + "class" + className);
        List<StuScore> stuScoreList;
//        if (grade == "" || className == "") {
//            studentList = miniappStuService.getAllStudent();
//        } else {
//            Long classId = miniappClassService.getIdByGradeAndClass(grade, className);
//            studentList = miniappStuService.getStuListByClassId(classId);
//        }
        stuScoreList = bmiScoreService.getBMIScoreList();
        return CommonResponse.ok(stuScoreList);
    }

    //肺活量
    @PostMapping(value="/addBMIScore")
    public CommonResponse addStudentSportScore(@RequestBody ScoreSport<Double> scoreSportFront) {
        System.out.println(scoreSportFront);

        ScoreSport<Double> scoreSport = setScore(scoreSportFront);
//      System.out.println(scoreSport);

        // 判断表中是否包含当前学生的成绩记录，若有则为更新；若无则为插入
        boolean flag;
        if (bmiScoreService.isRecordExist(scoreSport.getStudentNum())) {
            flag = bmiScoreService.updateSportScore(scoreSport);
            return  flag ? CommonResponse.ok():CommonResponse.fail(10001,"更新数据失败");
        }
        else{
            flag = bmiScoreService.insertSportScore(scoreSport);
            return  flag ? CommonResponse.ok():CommonResponse.fail(10002,"插入数据失败");
        }
    }

    public ScoreSport setScore(ScoreSport<Double> scoreSport) {

        // 测量数据，数据库记录元素之一
        Double value_stature = scoreSport.getValueFirst();
        Double value_weight = scoreSport.getValueSecond();

        Double value = value_weight / (value_stature * value_stature);
        // 年级，用于转换标准（不同年级标准不同）
        String grade=scoreSport.getClassId().substring(0,1);
        // 得分，数据库记录元素之一
        Integer score = 0;
        // 等级，数据库记录元素之一
        String level = "";

        // 每个等级下分的得分阶段数
        int[] stageNum = new int[]{5, 10, 2, 3};
        // 学生性别，用于转换标准（男女生标准不同）
        int gender = scoreSport.getGender();
        // 系数n，是计算得分的重要参数，根据“测量数据 与 得分标准 的差值对 小阶段增值 取余”得到当前成绩出于哪一个小阶段，进而得出分数
        int n = 0;
        // 用于存放得分标准
        HashMap<String, List<Double>> standard;

        // 根据年级获得对应的得分标准
        switch (grade) {
            case "1": standard = BMIStandard.getOne(); break;
            case "2": standard = BMIStandard.getTwo(); break;
            case "3": standard = BMIStandard.getThree(); break;
            case "4": standard = BMIStandard.getFour(); break;
            case "5": standard = BMIStandard.getFive(); break;
            case "6": standard = BMIStandard.getSix(); break;
            default: return null;
        }

        // 根据不同年级在该项体育项目上的分段标准，计算得分和得出等级
        if(value >= (standard.get("正常").get(gender)) &&
                value <= (standard.get("正常").get(gender+2))) {
            level = "正常";
            score = 100;
        } else if(value <= (standard.get("低体重").get(gender))) {
            level = "低体重";
            score = 80;
        } else if(value >= (standard.get("超重").get(gender)) &&
                value <= (standard.get("超重").get(gender+2))) {
            level = "超重";
            score = 80;
        } else if(value >= (standard.get("肥胖").get(gender))) {
            level = "肥胖";
            score = 60;
        }

        scoreSport.setScore(score);
        scoreSport.setLevel(level);
        return scoreSport;
    }

}
