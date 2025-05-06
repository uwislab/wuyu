package com.fiveup.core.management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import com.fiveup.core.management.common.exception.BizException;
import com.fiveup.core.management.mapper.StuMapper;
import com.fiveup.core.management.model.DTO.BasicStudent;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.StuService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lth
 * @date 2023/11/14
 */

@Slf4j
@RestController
@RequestMapping("/api/student")
@CrossOrigin
public class StuController {

    @Resource
    private CommonManagementService commonManagementService;

    @Resource
    private StuService stuService;

    @Resource
    private StuMapper stuMapper;

    /**
     * @param keyword     非必需 关键词
     * @param gender      非必需 性别
     * @param inclination 非必需 成绩趋势
     * @param pageNum     必需 页号
     * @param pageSize    必需 页容量
     * @return pageInfo类型
     */
    //    1.获取学生信息分页接口（包含动态条件查询）
    @GetMapping("/getStudentListByPage")
    public CommonResponse<PageInfo<StuDTO>> getStudentListByPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "gender", required = false) Integer gender,
            @RequestParam(value = "inclination", required = false) Integer inclination,
            @RequestParam(value = "classId", required = false) Long classId,
            @RequestParam(value = "pageNum", required = true) Integer pageNum, @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        if (pageNum <= 0 || pageSize < 0) {
            CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }
        PageInfo<StuDTO> stuDTOPageInfo = null;
        try {
            // 获取schoolId
            Long schoolId = commonManagementService.getSchoolId();
            stuDTOPageInfo = stuService.getStudentListByPage(keyword, gender, inclination, classId, schoolId, pageNum, pageSize);
        } catch (Exception e) {
            log.error("getStudentListByPage || UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
        return CommonResponse.ok(stuDTOPageInfo);
    }

    /**
     * 获取学生信息分页
     *
     * @param keyword
     * @param gender
     * @param inclination
     * @param gradeId
     * @param classId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getStudentList")
    public CommonResponse<PageInfo<StuDTO>> getStudentList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "gender", required = false) Integer gender,
            @RequestParam(value = "inclination", required = false) Integer inclination,
            @RequestParam(value = "gradeId", required = false) Integer gradeId,
            @RequestParam(value = "classId", required = false) Integer classId,
            @RequestParam(value = "pageNum", required = true) Integer pageNum, @RequestParam(value = "pageSize", required = true) Integer pageSize) {
//        System.out.println(keyword);
        System.out.println("classId = " + classId);
        if (pageNum <= 0 || pageSize < 0) {
            CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }
        PageInfo<StuDTO> stuDTOPageInfo = null;
        try {
            // 获取schoolId
            Long schoolId = commonManagementService.getSchoolId();
            stuDTOPageInfo = stuService.getStudentList(keyword, gender, inclination, gradeId, classId, schoolId, pageNum, pageSize);
        } catch (Exception e) {
            log.error("getStudentListByPage || UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
        System.out.println(stuDTOPageInfo);
        return CommonResponse.ok(stuDTOPageInfo);
    }

    //    2.添加学生
    @PostMapping("/addStudent")
    public CommonResponse<Void> addStudent(@RequestBody BasicStudent stuDTO) {
        System.out.println(stuDTO);
        // 参数校验，姓名不能为空，学号为空自动生成,classId默认为41
        if (stuDTO.getStudentName() == null) {
            return CommonResponse.fail(50001, "参数不能为空");
        }
        if (stuDTO.getClassId() == 0) {
            stuDTO.setClassId(1);
        }
        if (stuDTO.getIsreview() == null) {
//            isreview没有默认值，所以用0作为默认值
            stuDTO.setIsreview(0);
        }if(stuDTO.getIsenter()==null){
            stuDTO.setIsenter(0);
        }
        // 学生是否存在
        String stunum = stuDTO.getStudentNum();
        Long count = stuService.selectCount(stunum);
        if (count > 0) {
            return CommonResponse.fail(50000, "数据已存在");
        }

        try {
            //调用Service接口
//            stuService.addStudent(stuDTO);
//            调用mapper接口
            stuMapper.insert(stuDTO);
        } catch (Exception e) {
            if (e instanceof BizException) {
                log.error("addStudent || BizExcetion", e.getMessage(), e);
                return CommonResponse.fail(30002, "班级Id不存在！请联系管理员！");
            } else {
                log.error("addStudent || UNSPECIFIC", e.getMessage(), e);
                return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
            }
        }
        return CommonResponse.ok();
    }

    //3.修改学生
    @PutMapping("/alterStudent")
    public CommonResponse<Void> alterStudent(@RequestBody StuDTO stuDTO) {
        // 参数校验--学号数据不能为空、学生姓名不能为空
        if (stuDTO.getStudentNum() == null || stuDTO.getStudentName() == null) {
            return CommonResponse.fail(50001, "参数不能为空");
        }

        // 学号是否存在,前端完成
        try {
            stuService.alterStudent(stuDTO);
        } catch (Exception e) {
            log.error("alterStudent || UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
        return CommonResponse.ok();
    }

    //4.删除学生
    @DeleteMapping("/deleteStudent")
    public CommonResponse<Void> deleteStudent(@RequestParam("studentId") String studentId) {
        try {
            stuService.deleteStudent(studentId);
        } catch (Exception e) {
            log.error("deleteStudent || UNSPECIFIC", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
        return CommonResponse.ok();
    }

    //    5.查询学号是否存在
    @GetMapping("/queryStuNum/{stunum}")
    public CommonResponse<Void> queryStuNum(@PathVariable("stunum") String stunum) {
        Long count = stuService.selectCount(stunum);
        if (count > 0) {
            return CommonResponse.ok();
        } else {
            return CommonResponse.fail(50000, "该数据不存在");
        }
    }
    // 6.批量删除学生
    @DeleteMapping("/deleteBatch")
    public CommonResponse deleteBatch(@RequestBody List<Integer> ids){
        System.out.println(ids);
        int res = stuService.deleteBatch(ids);
        return CommonResponse.ok(res);
    }
    // 7.获取所有学生信息
    @GetMapping("/getAllStudent")
    public CommonResponse getAllStudent(){
        Long schoolId = commonManagementService.getSchoolId();
        List<StuDTO> stuDTOList = stuService.getAllStudent(schoolId);
        System.out.println("这里的大小:"+stuDTOList.size());
        return CommonResponse.ok(stuDTOList);
    }
}
