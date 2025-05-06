package com.fiveup.core.teacherworkspace.controller;

import com.fiveup.core.courseScore.controller.CourseScoreController;
import com.fiveup.core.courseScore.service.CourseScoreService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.teacherworkspace.model.*;
import com.fiveup.core.teacherworkspace.service.TeacherWorkspaceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/teacher")
public class TeacherWorkspaceController {

    @Resource
    private TeacherWorkspaceService teacherWorkspaceService;


    //获取所有教师信息
    @GetMapping(value = "/getAllTeacher")
    public CommonResponse getAllTeacher() {
        List<Teacher> teacherList = teacherWorkspaceService.getAllTeacher();

        return CommonResponse.ok(teacherList);
    }

    //获取对应id的教师信息
    @GetMapping(value = "/getTeacherInfo")
    public CommonResponse getTeacherInfo(@RequestParam Long teacher_id) {

        Teacher teacher = teacherWorkspaceService.getTeacherInfo(teacher_id);
        //System.out.println(teacher.getTeacherName());

        return CommonResponse.ok(teacher);
    }


    @GetMapping(value = "/getTeacherByIdentity")
    public CommonResponse getTeacherByIdentity(@RequestParam int identity) {

        List<String> teacherList;
        teacherList = teacherWorkspaceService.getTeacherByIdentity(identity);

        return CommonResponse.ok(teacherList);
    }

    @GetMapping(value = "/getTeacherByClass")
    public CommonResponse getTeacherByClass(int gradeId, int classId) {

        List<LessonTeacher> lessonTeacherList;
        lessonTeacherList = teacherWorkspaceService.getTeacherByClass(gradeId, classId);

        return CommonResponse.ok(lessonTeacherList);
    }

    @GetMapping(value = "/getMonitorByClass")
    public CommonResponse getMonitorByClass(int gradeId, int classId) {

        LessonTeacher lessonTeacherList;
        lessonTeacherList = teacherWorkspaceService.getMonitorByClass(gradeId, classId);

        return CommonResponse.ok(lessonTeacherList);
    }

    @GetMapping(value = "/getClassBasicInfo")
    public CommonResponse getClassBasicInfo(@RequestParam Long teacher_id) {
        // System.out.println(teacher_id);

        // ClassBasicInfo classBasicInfo = teacherWorkspaceService.getClassBasicInfo(teacher_id);

        return CommonResponse.ok();
    }

    //获取所有事项信息
//    @GetMapping("/getAllWork")
//    public CommonResponse getAllWork(){
//        List<Work> workList = teacherWorkspaceService.getAllWork();
//        return CommonResponse.ok(workList.size());
//    }

    //分页查询所有事项
    @GetMapping("/getWorkByPage")
    public CommonResponse getWorkByPage(@RequestParam int pageNum, int pageSize, String id, String title, String type) {
        pageNum = (pageNum - 1) * pageSize;
        id = "%" + id + "%";
        title = "%" + title + "%";
        type = "%" + type + "%";
        List<Work> workList = teacherWorkspaceService.getWorkByPage(pageNum, pageSize, id, title, type);
        int total = teacherWorkspaceService.getAllWork(id, title, type).size();
        Map<String, Object> res = new HashMap<>();
        res.put("data", workList);
        res.put("total", total);
        return CommonResponse.ok(res);
    }

    //获取教师课程信息
    @GetMapping(value = "/getLesson")
    public CommonResponse getLessonByTeacherId(@RequestParam Long teacherId) {
        List<Lesson> lesson = teacherWorkspaceService.getLessonByTeacherId(teacherId);
        return CommonResponse.ok(lesson);
    }

    //修改事项信息
    @PostMapping("/updateWork")
    public CommonResponse updateWork(@RequestBody Work work) {
        int res = teacherWorkspaceService.updateWork(work);
        return CommonResponse.ok(res);
    }

    //删除事项
    @DeleteMapping("/deleteWork")
    public CommonResponse deleteWork(@RequestParam Long id) {
        int res = teacherWorkspaceService.deleteWork(id);
        return CommonResponse.ok(res);
    }

    //修改教师信息
    @PostMapping("/updateTeacherInfo")
    public CommonResponse updateTeacherInfo(@RequestBody Teacher teacher) {
        int res = teacherWorkspaceService.updateTeacherInfo(teacher);

        return CommonResponse.ok(res);
    }

    @PostMapping("/updatePassword")
    public CommonResponse updatePassword(@RequestBody Teacher teacher){
        int res = teacherWorkspaceService.updatePassword(teacher);
        System.out.println(teacher);
        return CommonResponse.ok(res);
    }

    @DeleteMapping("/deleteBatch")
    public CommonResponse deleteBatch(@RequestBody List<Integer> ids) {
        System.out.println(ids);
        int res = teacherWorkspaceService.deleteBatch(ids);

        return CommonResponse.ok(res);
    }

    @Resource
    private CourseScoreService courseScoreService;

    @GetMapping("/getWorkList")
    public CommonResponse getWorkList(Long teacher_id) {
        //通过教师ID来获取教师对应课程
        List<Lesson> lesson = teacherWorkspaceService.getLessonByTeacherId(teacher_id);
        int[] d = new int[lesson.size()];
        //通过数组来记录课程是否完成成绩录入
        for (int i = 0; i < lesson.size(); i++) {
            if (courseScoreService.getList(lesson.get(i).getTeacherName(),lesson.get(i).getGrade(),lesson.get(i).getClassNum(),lesson.get(i).getCourse()).size() == 0)
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
}
