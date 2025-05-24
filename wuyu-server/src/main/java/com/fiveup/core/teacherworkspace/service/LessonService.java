package com.fiveup.core.teacherworkspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;

public interface LessonService extends IService<Lesson> {
    /**
     * 分页查询课程
     * @param dto 查询条件
     * @return 查询结果
     */
    PageVo<Lesson> pageLesson(PageLessonDto dto);
}
