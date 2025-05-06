package com.fiveup.core.management.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.pojo.*;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.TeachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author 付义程
 * @Date 2024/11/25
 */
@Slf4j
@RestController
@RequestMapping("/api/teacherQuery")
@CrossOrigin
public class TeachController {

    @Autowired
    TeachService service;

    @Resource
    private CommonManagementService commonManagementService;

    /**
     * 获取所有教师信息
     * @return 教师列表视图对象
     */
    @GetMapping("/getAllTeacher")
    public CommonResponse<TeacherListVo> getAll() {
        List<TeacherVo> list = service.getAllTeacher();
        TeacherListVo vo = new TeacherListVo(list);
        return CommonResponse.ok(vo);
    }

    /**
     * 根据教师ID获取教师详细信息
     * @param teacherId 教师ID
     * @return 教师信息视图对象
     */
    @GetMapping("/getTeacherInfo")
    public CommonResponse<TeacherInfoVo> getTeacherInfo(Long teacherId) {
        TeacherInfoVo vo= service.getTeacherInfo(teacherId );
        return CommonResponse.ok(vo);
    }

    /**
     * 获取所有班级信息
     * @return 班级信息
     */
    @GetMapping("/getClassInfo")
    public CommonResponse<List<ClassInfoVo>> getClassInfo() {
        List<ClassInfoVo> list = service.getClassInfo();
        return CommonResponse.ok(list);
    }

    /**
     * 获取表单对象
     * @return 表单视图对象
     */
    @GetMapping("/getFormObject")
    public CommonResponse<FormVo> getFormObject() {
//        TeacherVo vo= service.getTeacherInfo(teacherId    );
        FormVo vo =service.getFormObject();
        return CommonResponse.ok(vo);
    }


    /**
     * 分页查询教师信息
     * @param search 分页查询参数
     * @return 教师列表视图对象
     */
    @PostMapping("/getTeacherByPage")
    public CommonResponse<TeacherListVo> getTeacherByPage(PageDto search) {
        System.out.println("search = " + search);
//        dto.setSchoolId(1L);
        long schoolId = commonManagementService.getSchoolId();
        System.out.println("schoolId = " + schoolId);
        TeacherListVo vo = service.getTeacherByPage(search,schoolId);
        return CommonResponse.ok(vo);
    }

    /**
     * 导出教师信息到Excel
     * @param search 查询参数
     * @param request HTTP请求
     * @param response HTTP响应
     * @throws IOException IO异常
     */
    @GetMapping("downloadTeacherList")
    public void downloadTeacherList(PageDto search, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(search);
        long schoolId = commonManagementService.getSchoolId();
        List<TeacherExcel> list = service.searchTeacherList(search,schoolId);//获取list
        String fileName = "教师信息表" + ".xlsx";
        OutputStream os = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName*=utf-8'zh_cn'" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));
            response.setHeader("Access-Control-Allow-Origin", "*");
        } catch (Exception e) {
           e.printStackTrace();
        }
        //导出
        EasyExcel.write(response.getOutputStream())
                .head(TeacherExcel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet()
                .doWrite(list);
    }

}
