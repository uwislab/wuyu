package com.fiveup.core.teacherworkspace.controller;

import com.fiveup.core.common.api.CommonResult;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;
import com.fiveup.core.teacherworkspace.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@Slf4j
@RequiredArgsConstructor
@Api(value = "排课管理")
public class LessonController {
    private final LessonService lessonService;
    
    @GetMapping("/page")
    @ApiOperation("分页查询排课")
    public CommonResult<PageVo<Lesson>> page(PageLessonDto dto){
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
        int addRow = lessonService.addLesson(lesson);
        if (addRow > 0) {
            return CommonResult.success(addRow);
        } else {
            return CommonResult.failed("添加信息失败");
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("修改排课信息")
    public CommonResult<Integer> updateLesson(@RequestBody Lesson lesson) {
        int updateRow = lessonService.updateLesson(lesson);
        if (updateRow > 0) {
            return CommonResult.success(updateRow);
        } else {
            return CommonResult.failed("修改信息失败");
        }
    }
}