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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}