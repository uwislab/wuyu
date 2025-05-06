package com.fiveup.core.management.controller;

import com.alibaba.excel.util.StringUtils;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import com.fiveup.core.management.model.DTO.TeaDTO;

import com.fiveup.core.management.model.Req.TeaAddReq;
import com.fiveup.core.management.model.Req.TeaEditReq;
import com.fiveup.core.management.model.Resp.TeaPageResp;
import com.fiveup.core.management.model.Resp.TeaSimpleResp;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.TeaService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/25
 */
@Slf4j
@RestController
@RequestMapping("/api/teacher")
@CrossOrigin
public class TeaController {


    @Resource
    private TeaService teaService;


    @Resource
    private CommonManagementService commonManagementService;

    @GetMapping("/getTeacherInfo")
    public CommonResponse<TeaSimpleResp> getTeacherInfo() {
        Long schoolId = commonManagementService.getSchoolId();
        List<TeaDTO> teaDTOList = teaService.getTeacherSimpleInfo(schoolId);
        return CommonResponse.ok(new TeaSimpleResp(teaDTOList));
    }


    //根据教师名字、教师职称、教师职位 获取教师信息 查询basic_teacher表
    @GetMapping("/getTeacherListByPage")
    public CommonResponse<PageInfo<TeaPageResp>> getTeacherListByPage(@RequestParam(value = "teacherName", required = false) String teacherName,
                                                                      @RequestParam(value = "title", required = false) String title,
                                                                      @RequestParam(value = "position", required = false) String position,
                                                                      @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                                                      @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        if (pageNum < 0 || pageSize < 0) {
            return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }
        try {
            Long schoolId = commonManagementService.getSchoolId();
            PageInfo<TeaPageResp> teaPageRespPageInfo = teaService.getTeacherListByPage(teacherName, title, position, schoolId,pageNum, pageSize);
            return CommonResponse.ok(teaPageRespPageInfo);
        } catch (Exception e) {
            log.error("getTeacherListByPage || __UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }

    @PostMapping("/addTeacher")
    public CommonResponse<Void> addTeacher(@RequestBody TeaAddReq teaAddReq) {
        if (teaAddReq.getTeacherName() == null) {
            return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }
        try {
            Long schoolId = commonManagementService.getSchoolId();
            teaAddReq.setSchoolId(schoolId);

            teaService.addOne(teaAddReq);
            return CommonResponse.ok();
        } catch (Exception e) {
            log.error("addTeacher || __UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }

    @PutMapping("/editTeacher")
    public CommonResponse<Void> edit(@RequestBody TeaEditReq teaEditReq) {
        if (StringUtils.isEmpty(teaEditReq.getTeacherId())) {
            return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }

        try {
            teaService.edit(teaEditReq);
            return CommonResponse.ok();
        } catch (Exception e) {
            log.error("edit || __UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }

    @DeleteMapping("/deleteTeacher")
    public CommonResponse<Void> deleteOne(Long teacherId) {
        try {
            teaService.softlyDeleteOne(teacherId);
            return CommonResponse.ok();
        } catch (Exception e) {
            log.error("deleteOne || __UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }

    @DeleteMapping("/deleteBatch")
    public CommonResponse deleteBatch(@RequestBody List<Integer> ids){
        System.out.println(ids);
        int res = teaService.deleteBatch(ids);
        return CommonResponse.ok(res);
    }

}
