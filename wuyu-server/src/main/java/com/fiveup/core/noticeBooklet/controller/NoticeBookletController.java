package com.fiveup.core.noticeBooklet.controller;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.domain.vo.StudentVO;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知册相关接口
 *
 * @author huangxingcan
 * @date 2025/6/7 16:38
 */
@RestController
@CrossOrigin
@RequestMapping("/noticeBooklet")
public class NoticeBookletController {

    @Autowired
    private NoticeBookletService noticeBookletService;

    /**
     * 获取通知册内容
     *
     * @param studentNum 学号
     * @param classId    班级id
     * @param gradeId    年级id
     * @param isRemark   是否获取评价
     * @param findKey    模糊查询
     * @return 通知册列表
     */
    @GetMapping("/get")
    public CommonResponse<List<NoticeBooklet>> getNoticeBooklet(
            @RequestParam(required = false) Integer studentNum
            , @RequestParam(required = false) Integer classId
            , @RequestParam(required = false) Integer gradeId
            , @RequestParam(required = false) boolean isRemark,
            @RequestParam(required = false) String findKey) {
        // 调用服务层方法获取通知册内容
        List<NoticeBooklet> noticeBooklets = noticeBookletService.getNoticeBooklet(studentNum, classId, gradeId, isRemark, findKey);
        return CommonResponse.ok(noticeBooklets);
    }

    /**
     * 获取所有学生学号、(班级,年级)、年级
     *
     * @return 所有学生信息
     */
    @GetMapping("/getAllStudentAndClassAndGrade")
    public CommonResponse<StudentVO> getAllStudent() {
        StudentVO noticeBooklets = noticeBookletService.getAllStudent();
        return CommonResponse.ok(noticeBooklets);
    }

    /**
     * 修改通知册内容
     *
     * @param noticeBooklet 通知册内容
     * @return 修改成功
     */
    @PutMapping("/modify")
    public CommonResponse<String> modifyNoticeBooklet(@RequestBody @Validated NoticeBooklet noticeBooklet) {
        // 修改通知册内容，调用服务层方法
        // 会改动学生的成绩、假期要求等
        noticeBookletService.modifyNoticeBooklet(noticeBooklet);
        return CommonResponse.ok("修改成功");
    }
}