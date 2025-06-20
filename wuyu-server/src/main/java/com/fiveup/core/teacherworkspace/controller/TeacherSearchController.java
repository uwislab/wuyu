package com.fiveup.core.teacherworkspace.controller;


import com.fiveup.core.common.api.CommonResult;
import com.fiveup.core.teacherworkspace.model.Teacher;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;
import com.fiveup.core.teacherworkspace.service.TeacherSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherSearchController {

    @Autowired
    private TeacherSearchService teacherSearchService;
    /**
     * 模糊搜索教师信息
     *
     * @param teacherName
     * @return
     */
    @GetMapping("/search")
    public CommonResult<PageVo<Teacher>> getTeacherInfo(@RequestParam String teacherName,
                                                        @RequestParam(defaultValue = "10") Long size,
                                                        @RequestParam(defaultValue = "1") Long page) {

        return CommonResult.success(teacherSearchService.searchTeacher(teacherName, size, page));
    }
}
