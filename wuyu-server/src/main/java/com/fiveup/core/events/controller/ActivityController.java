package com.fiveup.core.events.controller;

import com.fiveup.core.events.model.request.ActivityAddReq;
import com.fiveup.core.events.model.request.ActivityEditReq;
import com.fiveup.core.events.model.request.ActivityPageReq;
import com.fiveup.core.events.model.response.*;
import com.fiveup.core.events.service.ActivityService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.DTO.TeaDTO;
import com.fiveup.core.management.service.ClassService;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.TeaService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/3/28
 *
 *
 */

@Slf4j
@RestController("actController")
@RequestMapping("/api/manageAct")
@CrossOrigin
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private ClassService clazzService;

    @Resource
    private TeaService teaService;

    @Resource
    private CommonManagementService commonManagementService;

    /**
     * 添加活动接口
     *
     * @param activityAddReq 请求体注入对象形参
     * @return
     */
    @PostMapping("/addAct")
    public CommonResponse<Void> addActivity(@RequestBody ActivityAddReq activityAddReq) {

        // TODO 参数校验
        System.out.println(activityAddReq);

        try {
            if (activityAddReq.getClassId() == null && activityAddReq.getGrade() == null) {
                return CommonResponse.fail(01, "暂无此能力");
            } else {
                activityService.addOne(activityAddReq);
            }
        } catch (Exception e) {
            log.error("addActivity failed", e.getMessage(), e);

            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
        log.info("add activity success");
        return CommonResponse.ok();
    }


    /**
     * 获取班级对应Id信息接口
     * @return
     */
    @GetMapping("/getClassInfo")
    public CommonResponse<SimpleClassDataResp> getClassInfo(@RequestParam(name = "grade", required = false) String grade) {
        Long schoolId = commonManagementService.getSchoolId();
        List<ClassDTO> classDTOList = clazzService.getSimpleClassInfoByGrade(grade, schoolId); // TODO
        List<SimpleClassResp> simpleClassRespList = new ArrayList<>();
        for (ClassDTO classDTO : classDTOList) {
            SimpleClassResp simpleClassResp = new SimpleClassResp();
            simpleClassResp.setClassId(classDTO.getId());
            // classDTO.getGrade() +
            simpleClassResp.setClassName(classDTO.getClassName());
            simpleClassRespList.add(simpleClassResp);
        }
        SimpleClassDataResp simpleClassDataResp = new SimpleClassDataResp(simpleClassRespList);

        return CommonResponse.ok(simpleClassDataResp);
    }

    @GetMapping("/getTeacherInfo")
    public CommonResponse<TeacherSimpleResp> getTeacherInfo() {
        Long schoolId = commonManagementService.getSchoolId();
        List<TeaDTO> teaDTOList = teaService.getTeacherSimpleInfoForEvents(schoolId);
        TeacherSimpleResp teacherSimpleResp = new TeacherSimpleResp(teaDTOList);
        return CommonResponse.ok(teacherSimpleResp);
    }

    //查询活动列表
    @PostMapping("/getActivityListByPage")
    public CommonResponse<PageInfo<ActivityPageResp>> getActivityListByPage(@RequestBody ActivityPageReq activityPageReq) {

        Integer pageNum = activityPageReq.getPageNum();
        Integer pageSize = activityPageReq.getPageSize();
        String activityName = activityPageReq.getActivityName();
        Integer activityState = activityPageReq.getActivityState();
        String grade = activityPageReq.getGrade();
        Integer activityType = activityPageReq.getActivityType();
        Integer activityAspect = activityPageReq.getActivityAspect();
        List<Integer> classList = activityPageReq.getClassList();
        if (pageNum <= 0 || pageSize < 0) {
            return CommonResponse.fail(1001, "页码参数校验异常");
        }
        Long schoolId = commonManagementService.getSchoolId();
        PageInfo<ActivityPageResp> activityPageRespPageInfo = activityService.getActivityListByPage(activityName,
                activityState,
                grade,
                activityType,
                activityAspect,
                schoolId,
                classList,
                pageNum,
                pageSize);
        return CommonResponse.ok(activityPageRespPageInfo);
    }

    //具体活动列表
    @GetMapping("/getActivityDetail")
    public CommonResponse<ActivityDetailResp> getActivityDetail(@RequestParam("activityId") Long activityId,
                                                                @RequestParam(value = "activityAspect", required = false) Integer activityAspect) {
        ActivityDetailResp activityDetailResp;
        try {
            activityDetailResp = activityService.getActivityDetail(activityId,activityAspect);
        } catch (Exception e) {
            log.error("error={}",e.getMessage(),e);
            return CommonResponse.fail(1001, "|| _UNSPECIFIC");
        }
        return CommonResponse.ok(activityDetailResp);
    }

    @PutMapping("/editActivityInfo")
    public CommonResponse<Void> edit(@RequestBody ActivityEditReq activityEditReq) {
        if (activityEditReq.getActivityId() == null) {
            return CommonResponse.fail(1001, "参数校验异常");
        }
        try {
            activityService.edit(activityEditReq);
        } catch (Exception e) {
            log.error("activity_edit_error||__unspecific", e.getMessage(), e);
            CommonResponse.fail(1002, "activity_edit_error");
        }
        return CommonResponse.ok();
    }

    @DeleteMapping("/deleteActivity")
    public CommonResponse deleteActivity(@RequestParam("activityId") Long activityId) {
        activityService.deleteOne(activityId);
        return CommonResponse.ok();
    }
}
