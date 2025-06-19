package com.fiveup.core.teacherworkspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;

import java.util.List;

public interface LessonService extends IService<Lesson> {
    /**
     * 分页查询课程
     *
     * @param dto 查询条件
     * @return 查询结果
     */
    PageVo<Lesson> pageLesson(PageLessonDto dto);

    int deleteItems(List<Long> ids);

    String addLesson(Lesson lesson);

    String updateLesson(Lesson lesson);

    Boolean copyLessonClassToClass(Lesson source, Lesson target, boolean isOverwrite);

    Boolean copyLessonByLastSemester(String academicYear, int semester, boolean isOverwrite) throws RuntimeException;

    int setCurrentByAcademicAndSemester(String academicYear, int semester);

    List<String> listAcademicYears();
}
