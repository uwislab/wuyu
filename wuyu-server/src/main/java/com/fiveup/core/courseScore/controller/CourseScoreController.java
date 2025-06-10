package com.fiveup.core.courseScore.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.fiveup.core.commentgeneration.utils.Result;
import com.fiveup.core.courseScore.entity.CourseScore;
import com.fiveup.core.courseScore.entity.ResPage;
import com.fiveup.core.courseScore.service.CourseScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/coursescore")
@Slf4j
public class CourseScoreController {
    @Autowired
    private CourseScoreService courseScoreService;

    /**
     * 条件分页查询
     * @param
     */
    @PostMapping("/get")
    public Result<V> search(@RequestParam String courseName, @RequestParam(required = false) Integer courseType, @RequestParam String teacherName, @RequestParam String studentNum, @RequestParam String studentName, @RequestParam Integer page, @RequestParam Integer pageSize){

        log.debug("courseName:{},courseType:{},teacherName:{},studentNum:{},studentName:{},page:{},pageSize:{}",courseName,courseType,teacherName,studentNum,studentName,page,pageSize);
        ResPage<List<CourseScore>> resPage = courseScoreService.search(courseName,courseType,teacherName,studentNum,studentName,page,pageSize);
        Result<V> result = new Result<V>(200,"查询成功", Math.toIntExact(resPage.getTotal()),resPage);
        return result;
    }

    /**
     * 成绩录入
     * @param courseScore
     * @return
     */
    @PostMapping("/add")
        public Result<V> save(@RequestBody CourseScore courseScore){
        courseScoreService.save(courseScore);
        Result<V> result = new Result<V>();
        result.setCode(600);
        result.setMsg("增加成功！");
        return result;
    }

    /**
     * 根据ids数组删除成绩
     * @param ids
     * @return
     */
    @PostMapping("/deleteByIds")
    public Result<V> deleteByIds(@RequestBody String[] ids){
        if(ids.length==0)
            return new Result<V>(233,"选择数据不能为空!");
        boolean deleteSuccess = courseScoreService.deleteByIds(ids);
        Result<V> result = new Result<V>();
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
     * @param courseScore
     * @return
     */
    @PostMapping("/edit")
    public Result<V> edit(@RequestBody CourseScore courseScore){
        courseScoreService.edit(courseScore);
        Result<V> result = new Result<V>();
        result.setCode(600);
        result.setMsg("修改成功！");
        return result;
    }

    /**
     * 将全部成绩设置为85
     * @return
     */
    @PutMapping("/85")
    public String update85(){
        courseScoreService.update85();
        Result<V> result = new Result<V>();
        result.setCode(600);
        result.setMsg("修改成功！");
        return JSON.toJSON(result).toString();
    }

    @PostMapping("/upload")
    public Result<V> upload(@RequestBody MultipartFile file) throws IOException {
        Result<V> result = new Result<V>();
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
    private boolean validateCourseScore(CourseScore courseScore, Result<V> result) {
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
