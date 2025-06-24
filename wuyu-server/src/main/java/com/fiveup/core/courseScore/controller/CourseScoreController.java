package com.fiveup.core.courseScore.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.fiveup.core.commentgeneration.utils.Result;
import com.fiveup.core.common.controller.BaseController;
import com.fiveup.core.courseScore.entity.CourseScore;
import com.fiveup.core.courseScore.entity.ResPage;
import com.fiveup.core.courseScore.service.CourseScoreService;
import com.fiveup.core.mainpage.domain.po.WebUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/coursescore")
@Slf4j
public class CourseScoreController extends BaseController {
    @Autowired
    private CourseScoreService courseScoreService;

    /**
     * 条件分页查询
     * @param courseName 课程名称
     * @param courseType 课程类型
     * @param teacherName 教师名称
     * @param studentNum 学生学号
     * @param studentName 学生姓名
     * @param page 页码
     * @param pageSize 每页大小
     * @param username 用户名
     * @param userId 用户ID
     * @param request HTTP请求对象
     * @return 查询结果
     */
    @PostMapping("/get")
    public Result search(@RequestParam String courseName,
                         @RequestParam(required = false) Integer courseType,
                         @RequestParam String teacherName,
                         @RequestParam String studentNum,
                         @RequestParam String studentName,
                         @RequestParam Integer page,
                         @RequestParam Integer pageSize,
                         @RequestParam(required = false) String username,
                         @RequestParam(required = false) Integer userId,
                         HttpServletRequest request){

        // 使用BaseController方法获取当前登录用户信息
        Integer currentUserId = getCurrentUserId(request);
        String realName = getRealName(request);
        Integer userIdentity = getCurrentUserIdentity(request);
        
        // 记录用户信息
        log.info("当前操作用户: ID={}, 用户名={}, 身份={}", currentUserId, realName, userIdentity);
        

        // 根据用户身份进行权限控制
        if (userIdentity == 3) {
            log.info("普通教师访问，限制只能查看自己的课程");
            teacherName = realName; // 限制只能查看自己的课程
        }
        
        // 将当前用户ID传递给服务层
        ResPage<List<CourseScore>> resPage = courseScoreService.search(courseName, courseType, teacherName, studentNum, studentName, page, pageSize);
        Result result = new Result(200,"查询成功", Math.toIntExact(resPage.getTotal()), resPage);
        return result;
    }

    /**
     * 成绩录入
     * @param courseScore 成绩信息
     * @param request HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result save(@RequestBody CourseScore courseScore, HttpServletRequest request){
        // 获取当前用户信息并记录
        Integer currentUserId = getCurrentUserId(request);
        String currentUsername = getCurrentUsername(request);
        log.info("成绩录入操作 - 用户: {}, ID: {}", currentUsername, currentUserId);
        
        courseScoreService.save(courseScore);
        Result result = new Result();
        result.setCode(600);
        result.setMsg("增加成功！");
        return result;
    }

    /**
     * 根据ids数组删除成绩
     * @param ids 成绩ID数组
     * @param request HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/deleteByIds")
    public Result deleteByIds(@RequestBody String[] ids, HttpServletRequest request){
        // 获取当前用户信息并记录
        Integer currentUserId = getCurrentUserId(request);
        String currentUsername = getCurrentUsername(request);
        log.info("删除成绩操作 - 用户: {}, ID: {}", currentUsername, currentUserId);
        
        if(ids.length==0)
            return new Result(233,"选择数据不能为空!");
        boolean deleteSuccess = courseScoreService.deleteByIds(ids);
        Result result = new Result();
        if(deleteSuccess) {
            result.setCode(200);
            result.setMsg("删除成功！");
        }else{
            result.setCode(233);
            result.setMsg("删除失败!");
        }
        return result;
    }

    /**
     * 修改成绩
     * @param courseScore 成绩信息
     * @param request HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody CourseScore courseScore, HttpServletRequest request){
        // 获取当前用户信息并记录
        Integer currentUserId = getCurrentUserId(request);
        String currentUsername = getCurrentUsername(request);
        log.info("修改成绩操作 - 用户: {}, ID: {}", currentUsername, currentUserId);
        
        courseScoreService.edit(courseScore);
        Result result = new Result();
        result.setCode(600);
        result.setMsg("修改成功！");
        return result;
    }

    /**
     * 将全部成绩设置为85
     * @param request HTTP请求对象
     * @return 操作结果
     */
    @PutMapping("/85")
    public String update85(HttpServletRequest request){
        // 获取当前用户信息并记录
        Integer currentUserId = getCurrentUserId(request);
        String currentUsername = getCurrentUsername(request);
        log.info("设置全部成绩为85操作 - 用户: {}, ID: {}", currentUsername, currentUserId);
        
        // 检查权限，只有校长和教务主任可以执行此操作
        if (!isPrincipal(request) && !isAcademicDirector(request)) {
            Result result = new Result();
            result.setCode(403);
            result.setMsg("权限不足，无法执行此操作！");
            return JSON.toJSON(result).toString();
        }
        
        courseScoreService.update85();
        Result result = new Result();
        result.setCode(600);
        result.setMsg("修改成功！");
        return JSON.toJSON(result).toString();
    }

    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file, HttpServletRequest request) throws IOException {
        // 获取当前用户信息并记录
        Integer currentUserId = getCurrentUserId(request);
        String currentUsername = getCurrentUsername(request);
        log.info("上传成绩文件操作 - 用户: {}, ID: {}", currentUsername, currentUserId);
        
        Result result = new Result();
        try{
            List<CourseScore> list = ExcelUtil.getReader(file.getInputStream()).readAll(CourseScore.class);
            if(!CollectionUtil.isEmpty(list)){

                for (CourseScore courseScore : list) {
                    // 校验逻辑
                    if (!validateCourseScore(courseScore, result)) {
                        result.setCode(0); // 校验失败，设置错误代码
                        return result;    // 终止处理并返回结果
                    }
                }

                // 如果所有数据都校验通过，保存数据
                for(CourseScore courseScore : list){
                    courseScoreService.save(courseScore);
                }
                result.setCode(600);

            }else {
                result.setCode(0);
                result.setMsg("上传的文件内容为空");
            }
        } catch (Exception e){
            result.setCode(0);
            result.setMsg("文件处理异常：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 校验单个 CourseScore 对象
     */
    private boolean validateCourseScore(CourseScore courseScore, Result result) {
        // 校验除教师评语外的字段是否为空
        if (courseScore.getCourseName() == null || courseScore.getCourseName().trim().isEmpty()) {
            result.setMsg("课程名称不能为空");
            return false;
        }
        if (courseScore.getCourseType() < 0 || courseScore.getCourseType() > 4) {
            result.setMsg("课程类型必须为数字0到4");
            return false;
        }
        if (courseScore.getTestNumber() < 1 || courseScore.getTestNumber() > 55) {
            result.setMsg("考试序号必须在1到55之间");
            return false;
        }
        if (courseScore.getTeacherName() == null || courseScore.getTeacherName().trim().isEmpty()) {
            result.setMsg("课程教师不能为空");
            return false;
        }
        if (courseScore.getStudentNum() == null || !courseScore.getStudentNum().matches("\\d{10}")) {
            result.setMsg("学生学号必须为10位数字");
            return false;
        }
        if (courseScore.getStudentName() == null || courseScore.getStudentName().trim().isEmpty()) {
            result.setMsg("学生姓名不能为空");
            return false;
        }
        if (courseScore.getInputTime() == null || courseScore.getInputTime().trim().isEmpty()) {
            result.setMsg("录入日期不能为空");
            return false;
        }
        if (courseScore.getScore() < 0 || courseScore.getScore() > 100) {
            result.setMsg("学生成绩必须为0-100的区间");
            return false;
        }
        return true; // 校验通过
    }
}
