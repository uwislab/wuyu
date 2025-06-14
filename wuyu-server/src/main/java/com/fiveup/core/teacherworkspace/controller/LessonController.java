package com.fiveup.core.teacherworkspace.controller;

import com.fiveup.core.common.api.CommonResult;
import com.fiveup.core.teacherworkspace.config.ScheduleConfig;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import com.fiveup.core.teacherworkspace.model.request.CopyRequest;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;
import com.fiveup.core.teacherworkspace.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@Slf4j
@RequiredArgsConstructor
@Api(value = "排课管理")
public class LessonController {
    private final ScheduleConfig scheduleConfig;
    private final LessonService lessonService;

    @GetMapping("/academic-year")
    @ApiOperation("列出所有学年")
    public CommonResult<List<String>> page() {
        return CommonResult.success(lessonService.listAcademicYears());
    }

    @GetMapping("/page")
    @ApiOperation("分页查询排课")
    public CommonResult<PageVo<Lesson>> page(@Validated PageLessonDto dto) {
        return CommonResult.success(lessonService.pageLesson(dto));
    }

    @DeleteMapping
    @ApiOperation("删除排课信息")
    public CommonResult<Integer> deleteItems(@RequestBody List<Long> ids) {
        int deletedRows = lessonService.deleteItems(ids);
        return CommonResult.success(deletedRows);
    }

    @PostMapping("/add")
    @ApiOperation("添加排课信息")
    public CommonResult<Integer> addItems(@RequestBody Lesson lesson) {
        String status = lessonService.addLesson(lesson);
        if (status.equals("OK")) {
            return CommonResult.success();
        } else {
            return CommonResult.failed(status);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("修改排课信息")
    public CommonResult<Integer> updateLesson(@RequestBody Lesson lesson) {
        String status = lessonService.updateLesson(lesson);
        if (status.equals("OK")) {
            return CommonResult.success();
        } else {
            return CommonResult.failed(status);
        }
    }

    @PostMapping("/copy-class")
    @ApiOperation("复制排课信息")
    public CommonResult<Integer> copyLessonClassToClass(@RequestBody CopyRequest request) {
        log.info("复制排课信息: {}", request);
        try {
            Boolean copied = lessonService.copyLessonClassToClass(request.getSource(), request.getTarget(), request.isOverwrite());
            return copied ? CommonResult.success() : CommonResult.failed();
        } catch (Exception e) {
            log.error("复制排课信息失败", e);
            return CommonResult.failed(e.getMessage());
        }
    }

    @GetMapping("/copyLastSemester")
    @ApiOperation("复制上一学期排课信息")
    public CommonResult<Integer> copyLessonByLastSemester(@RequestParam String academicYear, @RequestParam int semester, @RequestParam boolean isOverwrite) {
        log.info("复制上一学期排课信息: {} {}", academicYear, semester);
        try {
            Boolean copied = lessonService.copyLessonByLastSemester(academicYear, semester, isOverwrite);
            return copied ? CommonResult.success() : CommonResult.failed();
        } catch (Exception e) {
            log.error("复制上一学期排课信息失败", e);
            return CommonResult.failed(e.getMessage());
        }
    }

    @GetMapping("/auto-copy")
    @ApiOperation("配置开关自动复制")
    public CommonResult<Integer> configAutoCopy(@RequestParam boolean enabled) {
        log.info("配置开关自动复制: {}", enabled);
        scheduleConfig.setAutoCopyEnabled(enabled);
        return CommonResult.success();
    }

    @GetMapping("/set-current")
    @ApiOperation("设置当前学期")
    public CommonResult<Integer> setCurrent(@RequestParam String academicYear, @RequestParam int semester) {
        log.info("设置当前学期: {} {}", academicYear, semester);
        try {
            int data = lessonService.setCurrentByAcademicAndSemester(academicYear, semester);
            return CommonResult.success(data);
        } catch (Exception e) {
            log.error("设置当前学期失败", e);
            return CommonResult.failed(e.getMessage());
        }
    }
}