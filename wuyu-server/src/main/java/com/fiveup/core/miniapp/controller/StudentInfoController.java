package com.fiveup.core.miniapp.controller;

import com.fiveup.core.fuScore.controller.FuScoreController;
import com.fiveup.core.fuScore.model.StudentFuScore;
import com.fiveup.core.fuScore.service.StudentFuScoreService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.miniapp.model.bo.UpdatePhoneBo;
import com.fiveup.core.miniapp.service.ClassInfoService;
import com.fiveup.core.miniapp.service.StuInfoService;
import com.fiveup.core.miniapp.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 史林
 * @date 2022/10/10
 * @function 学生相关信息查询--基本信息查询、体育得分查询
 */
@Slf4j
@RestController
@RequestMapping("/miniapp")
@CrossOrigin
public class StudentInfoController {


    @Resource
    private StuInfoService stuInfoService;

    @Resource
    private StudentFuScoreService studentFuScoreService;
    @Autowired
    private ClassInfoService classInfoService;

    @PostMapping("/getStudentInfo")
    public CommonResponse<StuDTO> getStudentInfo(@RequestBody Map<String, Object> map) {
        String studentNum = (String) map.get("studentNum");
        if (studentNum == null) {
            return CommonResponse.fail(1001, "服务端获取学生学号失败");
        } else {
            StuDTO stuDTO = stuInfoService.getStudentInfo(Long.valueOf(studentNum));
            return CommonResponse.ok(stuDTO);
        }
    }

    @GetMapping("/findStudentByGradeAndClass")
    public CommonResponse<List<StuDTO>> getAllStudent(@RequestParam String grade,
                                                      @RequestParam String className) {
        System.out.println("HEKK Qgrade :" + grade + "class" + className);
        List<StuDTO> studentList;
        if (grade == "" || className == "") {
            studentList = stuInfoService.getAllStudent();
        } else {
            Long classId = classInfoService.getIdByGradeAndClass(grade, className);
            studentList = stuInfoService.getStuListByClassId(classId);
        }
        return CommonResponse.ok(studentList);
    }

    //  根据学号修改学生的手机号
    @PutMapping("/updateStudentPhone")
    public CommonResponse updateStudentPhone(@RequestBody UpdatePhoneBo updatePhoneBo){
        stuInfoService.updateStudentPhone(updatePhoneBo.getStudentNum(), updatePhoneBo.getPhoneNum());
        return CommonResponse.ok();
    }

    // 根据学生姓名或学号查找成绩单
    @GetMapping("/getStudentScore")
    public CommonResponse getStudentScore(String studentName, String studentId) {
        Integer studentIdInt = Integer.parseInt(studentId.isEmpty()? "0" : studentId );
        List<StudentFuScore> studentFuScore = studentFuScoreService.getStudentsFuScore(studentName, studentIdInt);
        return CommonResponse.ok(studentFuScore);
    }

    // 根据姓名查找学生
    @GetMapping("/findStudentByName")
    public CommonResponse<List<StuDTO>> getStudentListByName(@RequestParam String studentName) {
        System.out.println("studentName :" + studentName);
        List<StuDTO> studentList = stuInfoService.getStudentListByName(studentName);
        return CommonResponse.ok(studentList);
    }

    //确认已阅
    @PutMapping("/confirmReview")
    public CommonResponse confirmReview(@RequestParam String studentNum){
        System.out.println("studentNum:"+ studentNum);
//        StuDTO stu=stuInfoService.getStudentbyStudentNum(studentNum);
//        int reviewStatus=stu.getIsreview();
//        if(reviewStatus==1)return reviewStatus
        stuInfoService.confirmReview(studentNum);
        return CommonResponse.ok();

    }

}
