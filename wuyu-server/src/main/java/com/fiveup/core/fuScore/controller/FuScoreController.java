package com.fiveup.core.fuScore.controller;

import com.fiveup.core.classManage.model.GradeInfo;
import com.fiveup.core.classManage.service.ClassManageService;
import com.fiveup.core.fuScore.entity.vo.FuClassAvgScoreVO;
import com.fiveup.core.fuScore.entity.vo.FuGradeAvgScoreVO;
import com.fiveup.core.fuScore.model.*;
import com.fiveup.core.fuScore.service.*;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.miniapp.service.StuInfoService;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.service.TeacherWorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.*;

import org.apache.ibatis.annotations.Param;
/**
 * @author shilin
 * @date 2022/9/19
 */

@Slf4j
@RestController
@RequestMapping("/fuScore")
@CrossOrigin
public class FuScoreController {

    @Resource
    private StudentFuScoreService studentFuScoreService;
    @Resource
    private ClassFuScoreService classFuScoreService;
    @Resource
    private GradeFuScoreService gradeFuScoreService;
    @Resource
    private ClassManageService classManageService;

    @Resource
    private StuInfoService stuInfoService;

    @Resource
    private TeacherWorkspaceService teacherWorkspaceService;
    
    @Autowired
    private FuClassScoreService fuClassScoreService;    
    
    @Autowired
    private FuGradeScoreService fuGradeScoreService;

    // ItemID -- 项目名对应数组
    private String[] fuItem = new String[]{"morality_score", "intelligence_score", "physical_score", "aesthetic_score", "labour_score"};

    // 获得某一班级所有学生的某一五育项目成绩
    // ！！！第二个页面table表中的内容gh
    @GetMapping("/getStudentsFuScoreList")
    public CommonResponse getStudentsFuScoreList( int gradeId, int classId, int date) {
        List<StudentFuScore> studentFuScore;
        studentFuScore = classFuScoreService.getStudentsFuScoreList(gradeId, classId, date);
        return CommonResponse.ok(studentFuScore);
    }


    //！！！添加的
    // 根据学生姓名或学号查询分数fdsafdafdasfasdfasdf
    @GetMapping("/getScoreByInfo")
    public List<StudentFuScore> getScoreByInfo(@RequestParam String info) {
        return studentFuScoreService.getScoreByInfo(info);
    }




    // 获得所有年级的所有五育项目成绩
    @GetMapping("/getGradeFuScore")
    public CommonResponse<List<GradeFuScore>> getGradeFuScore() {
        List<GradeFuScore> gradeScoreList = null;
        gradeScoreList = gradeFuScoreService.getAll();
        return CommonResponse.ok(gradeScoreList);
    }

    // 根据时间搜索年级五育成绩
    @GetMapping("/getGradeFuScoreByDate")
    public CommonResponse<List<GradeFuScore>> getGradeFuScoreByDate(int date) {
        List<GradeFuScore> gradeScoreList;
        gradeScoreList = gradeFuScoreService.getGradeFuScoreByDate(date);
        System.out.println(gradeScoreList);
        return CommonResponse.ok(gradeScoreList);
    }

    // 搜索某一年级五育成绩
    @GetMapping("/getGradeScore")
    public CommonResponse<List<GradeFuScore>> getGradeScore(int gradeId) {
        List<GradeFuScore> gradeScoreList;
        gradeScoreList = gradeFuScoreService.getGradeScore(gradeId);
        System.out.println(gradeScoreList);
        return CommonResponse.ok(gradeScoreList);
    }



    // 获得成绩表中的评分时间
    @GetMapping("/getDateList")
    public CommonResponse<List<Integer>> getDateList() {
        List<Integer> dateList;
        dateList = gradeFuScoreService.getDateList();
        return CommonResponse.ok(dateList);
    }

    // 获得某学生去年同期成绩
    @GetMapping("/getLastScore")
    public CommonResponse getLastScore(String studentNum, int date) {
        StudentFuScore studentFuScore;
        studentFuScore = classFuScoreService.getLastScore(studentNum, date);
        return CommonResponse.ok(studentFuScore);
    }

    @GetMapping("/getAllClassScore")
    public CommonResponse getAllClassScore(int gradeId) {
        List<Integer> classList;
        List<List<ClassScore>> allClassScoreList = new ArrayList<>();
        classList = classManageService.getAllClassByGrade(gradeId);
        for (int i = 0; i < classList.size(); i++) {
            int classId = classList.get(i);
            System.out.println(classId);
            allClassScoreList.add(classFuScoreService.getClassScore(gradeId, classId));
        }
        return CommonResponse.ok(allClassScoreList);
    }





    // 根据学号获得学生五育成绩
    @GetMapping("/getStudentsFuScore")
    public CommonResponse getStudentsFuScore(int studentNum) {
        List<StudentFuScore> studentScore;
        studentScore = studentFuScoreService.getStudentsFuScore(studentNum);
        return CommonResponse.ok(studentScore);
    }


    // 根据学生姓名或者学号查询所有学期的成绩
    @GetMapping("/getStudentScore")
    public CommonResponse getStudentScore(String studentName, String studentId) {
        Integer studentIdInt = Integer.parseInt(studentId.isEmpty() ? "0" : studentId);
        List<StudentFuScore> studentFuScore = studentFuScoreService.getStudentsFuScore(studentName, studentIdInt);
        return CommonResponse.ok(studentFuScore);
    }

    //查询学生学期的五育成绩数据
    @GetMapping("/getStudentSemesterScores")
    public StudentSemesterScore getStudentSemesterScores(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("studentName") String studentName,
            @RequestParam("semester") String semester) {
        return studentFuScoreService.getStudentSemesterScores(studentId, studentName, semester);
    }

    //查询学生学期的五育成绩数据
    @GetMapping("/studentScoreSemester")
    public List<StuSemesterTotalScore> getStuSemester(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("studentName") String studentName) {
        return studentFuScoreService.getStuSemester(studentId, studentName);
    }


    // 2.根据学号查询学生的详细信息
    @GetMapping("/getStudentInfo")
    public CommonResponse getStudentInfo(int studentNum) {
        HashMap<String, Object> studentInfoWithFuScore = stuInfoService.getStudentInfoWithFuScore(studentNum);
        return CommonResponse.ok(studentInfoWithFuScore);
    }

    //  根据年级、班级、指标查班级成绩
    @GetMapping("/getScoreByGradeAndClassAndItem")
    public CommonResponse getScoreByGradeAndClassAndItem(int gradeId, String className, int itemId) {
        List<ClassScore> classScoreList;
        classScoreList = classFuScoreService.getClassScoreByGradeAndClassAndItem(gradeId, className, itemId);
        return CommonResponse.ok(classScoreList);
    }

    //根据年级班级、指标查询学生成绩


    //  根据年级查询班级名称
    @GetMapping("/getClassByGrade")
    public CommonResponse getClassByGrade(int gradeId) {
        return CommonResponse.ok(classManageService.getClassNameByGrade(gradeId));
    }

    // 根据年级、班级查询学生成绩信息
    @GetMapping("/getStudentScoreByClassID")
    public CommonResponse getScoreByGradeAndClass(int classId) {
        List<StudentFuScore> studentFuScores;
        studentFuScores = classFuScoreService.getClassScoreByGradeAndClass(classId);
        return CommonResponse.ok(studentFuScores);
    }

    // 1、根据年级ID和班级ID查询所有学期的班级成绩

    @GetMapping("/getClassScoreByClassIdAndGradeId")
    public CommonResponse getClassScore(int classId, int gradeId) {
//        Integer classIdInt = Integer.parseInt(classId.isEmpty()? "0" : classId );
//        Integer gradeIdInt = Integer.parseInt(gradeId.isEmpty()? "0" : gradeId );
        List<ClassScore> studentFuScore = classFuScoreService.getClassScoreByGradeIdAndClassId(classId, gradeId);
        return CommonResponse.ok(studentFuScore);
    }

    @PostMapping("/insertFuScore")
    public CommonResponse InsertFuScore(@RequestBody FuScore fuScore){
        int result=classFuScoreService.insertFuScore(fuScore);
        return CommonResponse.ok(result);
    }

    @GetMapping("/getWorkList")
    public CommonResponse getWorkList(Long teacher_id) {
        //通过教师ID来获取教师对应课程
        List<Lesson> lesson = teacherWorkspaceService.getLessonByTeacherId(teacher_id);
        int d[] = new int[lesson.size()];
        //通过数组来记录课程是否完成成绩录入
        for (int i = 0; i < lesson.size(); i++) {
            System.out.println(lesson.get(i));
            if (classFuScoreService.getWorkList(lesson.get(i).getClassNum(), lesson.get(i).getGrade()).size() == 0)
             //数据不存在则为0
                d[i] = 0;
            else
                //数据存在则为1
                d[i] = 1;

        }
        //将lesson数据装入List中并返回至前端
        List<Lesson> lesson1 = new ArrayList<>();
        List<Lesson> lesson2 = new ArrayList<>();
        for (int i = 0; i < d.length; i++) {
            if (d[i] == 1)
                //已完成的工作
                lesson1.add(lesson.get(i));
            else
                //未完成的工作
                lesson2.add(lesson.get(i));
        }
        List<List<Lesson>> r = new ArrayList<>();
        r.add(lesson1);
        r.add(lesson2);
        return CommonResponse.ok(r);
    }

    //获取教师工作事项
    @PostMapping("/enterScore")
    public CommonResponse enterScore(@RequestBody FuClassScore fuClassScore) {
        Calendar calendar = Calendar.getInstance();
        //获取当前年月
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        //获取上次成绩录入时间
        if (month > 7) {
            fuClassScore.setData(year + "07");
        } else {
            fuClassScore.setData(year - 1 + "12");
        }
        //新增班级成绩
        classFuScoreService.addFuClassScore(fuClassScore);
        return CommonResponse.ok();
    }

    // 获得某年级的某一五育项目成绩
    @GetMapping("/getGradeFuScoreByGradeID")
    public CommonResponse<List<ClassFuItemScore>> getGradeFuScoreByGradeID(@RequestParam("gradeID") int gradeID,
                                                                           @RequestParam("selectedItemID") int itemID) {
        List<ClassFuItemScore> classFuItemScore = null;
        if (String.valueOf(gradeID).equals("") || String.valueOf(itemID).equals("")) {
            return CommonResponse.fail(1001, "服务端获取数据失败");
        } else {
            // 根据请求发送的ID判断需要哪一五育项目的成绩
            switch (itemID) {
                case 1:
                    classFuItemScore = gradeFuScoreService.getGradeMoScoreList(gradeID);
                    break;
                case 2:
                    classFuItemScore = gradeFuScoreService.getGradeInScoreList(gradeID);
                    break;
                case 3:
                    classFuItemScore = gradeFuScoreService.getGradePhScoreList(gradeID);
                    break;
                case 4:
                    classFuItemScore = gradeFuScoreService.getGradeAeScoreList(gradeID);
                    break;
                case 5:
                    classFuItemScore = gradeFuScoreService.getGradeLaScoreList(gradeID);
                    break;
            }
            return CommonResponse.ok(classFuItemScore);
        }
    }

    // 获得所有年级的某时间下某一五育项目成绩
    @GetMapping("/getGradeOneScoreByDate")
    public CommonResponse<List<GradeFuItemScore>> getGradeOneScoreByDate(int itemId, int date) {
        List<GradeFuItemScore> gradeFuItemScore = null;
        if (String.valueOf(itemId).equals("")) {
            return CommonResponse.fail(1001, "服务端获取项目ID失败");
        } else {
            // 根据请求发送的ID判断需要哪一五育项目的成绩
            switch (itemId) {
                case 1:
                    gradeFuItemScore = gradeFuScoreService.getGradeMoScoreByDate(date);
                    break;
                case 2:
                    gradeFuItemScore = gradeFuScoreService.getGradeInScoreByDate(date);
                    break;
                case 3:
                    gradeFuItemScore = gradeFuScoreService.getGradePhScoreByDate(date);
                    break;
                case 4:
                    gradeFuItemScore = gradeFuScoreService.getGradeAeScoreByDate(date);
                    break;
                case 5:
                    gradeFuItemScore = gradeFuScoreService.getGradeLaScoreByDate(date);
                    break;
            }
            return CommonResponse.ok(gradeFuItemScore);
        }
    }

    // 获得所有年级的某一五育项目成绩
    @GetMapping("/getGradeFuScoreByItem")
    public CommonResponse<List<GradeFuItemScore>> getGradeFuScoreByItem(@RequestParam("selectedItemID") int itemID) {
        List<GradeFuItemScore> gradeFuItemScore = null;
        if (String.valueOf(itemID).equals("")) {
            return CommonResponse.fail(1001, "服务端获取班级ID失败");
        } else {
            // 根据请求发送的ID判断需要哪一五育项目的成绩
            switch (itemID) {
                case 1:
                    gradeFuItemScore = gradeFuScoreService.getGradeMoScoreList();
                    break;
                case 2:
                    gradeFuItemScore = gradeFuScoreService.getGradeInScoreList();
                    break;
                case 3:
                    gradeFuItemScore = gradeFuScoreService.getGradePhScoreList();
                    break;
                case 4:
                    gradeFuItemScore = gradeFuScoreService.getGradeAeScoreList();
                    break;
                case 5:
                    gradeFuItemScore = gradeFuScoreService.getGradeLaScoreList();
                    break;
            }
            return CommonResponse.ok(gradeFuItemScore);
        }
    }

    @GetMapping("/student/search")
    public List<StudentInfo> searchStudent(@RequestParam("keyword") String keyword) {
        return studentFuScoreService.searchStudents(keyword);
    }

    @GetMapping("/student/semesters")
    public List<StudentSemesterDto> getStudentSemesters(@RequestParam("studentId") Integer studentId) {
        return studentFuScoreService.getStudentSemesters(studentId);
    }

    /**
     * 获取年级五育成绩
     * @param semester
     * @return
     */
    @GetMapping("/grade/avgScore")
    public CommonResponse<List<FuGradeAvgScoreVO>> getGradeAvgScore(@NotNull(message = "请选择学期") Integer semester) {
        return CommonResponse.ok(fuGradeScoreService.getGradeAvgScore(semester));
    }

    /**
     * 获取年级映射关系
     * @return
     */
    @GetMapping("/gradeInfo")
    public CommonResponse<List<Map<String, Object>>> getGradeInfo() {
        return CommonResponse.ok(fuGradeScoreService.getGradeInfo());
    }
    
    @GetMapping("/class/avgScore")
    public CommonResponse<List<FuClassAvgScoreVO>> getClassAvgScore(@NotNull(message = "请选择学期") Integer semester, 
                                                                    @NotNull(message = "请选择班级") String clazz) {
        return CommonResponse.ok(fuClassScoreService.getClassAvgScore(semester, clazz));
    }

}
