package com.fiveup.core.management.controller;

import com.alibaba.excel.util.StringUtils;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;

import com.fiveup.core.management.model.Teacher;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.TeacherEditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师信息编辑控制器，用于处理教师信息的添加、编辑和删除请求。
 */
@Slf4j
@RestController
@RequestMapping("/teacherEdit")
@CrossOrigin
public class TeacherEditController {

    @Resource
    private TeacherEditService teaService; // 教师编辑服务

    @Resource
    private CommonManagementService commonManagementService; // 通用管理服务

    /**
     * 添加教师信息。
     * @param data 教师信息列表
     * @return 通用响应对象
     */
    @RequestMapping("/add")
    public CommonResponse<Void> add(@RequestBody List<Teacher> data) {
        if(data == null || data.size() == 0){
            return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }
        try {
            Long schoolId = commonManagementService.getSchoolId(); // 获取学校ID
            try{
                data.stream().forEach(p -> {
                    if(p.checkData())
                        p.setSchoolId(schoolId);
                    else
                        throw new RuntimeException();});
            }catch (Exception e){
                return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
            }
            teaService.add(data); // 添加教师信息
            return CommonResponse.ok();
        } catch (Exception e) {
            log.error("addTeacher || __UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }

    /**
     * 编辑教师信息。
     * @param teacher 教师信息
     * @param classId 班级ID列表
     * @return 通用响应对象
     */
    @RequestMapping("/edit")
    public CommonResponse<Void> edit(@RequestBody Teacher teacher, @RequestParam List<Long> classId) {
        if (StringUtils.isEmpty(teacher.getId()) || !teacher.checkData()) {
            return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }
        try {
            log.info("!!!!!Teacher: " + teacher);
            teaService.edit(teacher, classId);
            return CommonResponse.ok();
        } catch (Exception e) {
            log.error("edit || __UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }

    /**
     * 删除教师信息。
     * @param teacherId 教师ID
     * @return 通用响应对象
     */
    @RequestMapping("/delete")
    public CommonResponse<Void> delete(Long teacherId){
        try {
            teaService.deleteById(teacherId); // 根据ID删除教师信息
            return CommonResponse.ok();
        } catch (Exception e) {
            log.error("deleteOne || __UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }

}
