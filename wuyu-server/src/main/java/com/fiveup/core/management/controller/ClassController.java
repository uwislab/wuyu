package com.fiveup.core.management.controller;

import com.alibaba.excel.util.StringUtils;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import com.fiveup.core.management.common.exception.BizException;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.Req.ClassAddReq;
import com.fiveup.core.management.model.Req.ClassEditReq;
import com.fiveup.core.management.service.ClassService;
import com.fiveup.core.management.service.CommonManagementService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/24
 */

@Slf4j
@RestController
@RequestMapping("/api/class")
@CrossOrigin
public class ClassController {

    @Resource
    private ClassService clazzService;

    @Resource
    private CommonManagementService commonManagementService;

    @GetMapping("/getClassInfo")
    public CommonResponse<List<ClassDTO>> getSimpleClassInfo() {
        Long schoolId = commonManagementService.getSchoolId();
        List<ClassDTO> classDTOList = clazzService.getSimpleClassInfo(schoolId);
        return CommonResponse.ok(classDTOList);
    }


    //根据教师id(monitorId)查询班级信息 查询basic_class表
    @GetMapping("/getClassesByPage")
    public CommonResponse<PageInfo<ClassDTO>> getClassesByPage(@RequestParam(value = "grade", required = false) String grade,
                                                               @RequestParam(value = "monitorId", required = false) Long monitorId,
                                                               @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                                               @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        if (pageNum < 0 || pageSize < 0) {
            return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }
        PageInfo<ClassDTO> classDTOPageInfo;
        try {
            Long schoolId = commonManagementService.getSchoolId();
            classDTOPageInfo = clazzService.getClassesByPage(grade, monitorId, schoolId,pageNum, pageSize);
        } catch (Exception e) {
            log.error("getClassesByPage || UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
        return CommonResponse.ok(classDTOPageInfo);
    }


    // get class info by options
    @GetMapping("/getClassesBySelector")
    public CommonResponse<PageInfo<ClassDTO>> getClassesBySelector(@RequestParam(value = "grade", required = false) String grade,
                                                               @RequestParam(value = "monitorId", required = false) Long monitorId) {

        PageInfo<ClassDTO> classDTOPageInfo;
        try {
            Long schoolId = commonManagementService.getSchoolId();
            classDTOPageInfo = clazzService.getClassesByPage(grade, monitorId, schoolId,1, 10);
        } catch (Exception e) {
            log.error("getClassesBySelector || Error", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
        return CommonResponse.ok(classDTOPageInfo);
    }

    @DeleteMapping("/deleteBatch")
    public CommonResponse deleteBatch(@RequestBody List<Integer> ids){
        System.out.println(ids);
        int res = clazzService.deleteBatch(ids);
        return CommonResponse.ok(res);
    }

}
