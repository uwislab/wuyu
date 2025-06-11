package com.fiveup.core.teacherworkspace.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.teacherworkspace.mapper.LessonMapper;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;
import com.fiveup.core.teacherworkspace.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements LessonService {
    private final LessonMapper lessonMapper;

    @Override
    public PageVo<Lesson> pageLesson(PageLessonDto dto) {
        Page<Lesson> page = this.lambdaQuery()
                .ge(dto.getMinGrade() != null, Lesson::getGrade, dto.getMinGrade())
                .le(dto.getMaxGrade() != null, Lesson::getGrade, dto.getMaxGrade())
                .eq(dto.getClassNum() != null, Lesson::getClassNum, dto.getClassNum())
                .like(CharSequenceUtil.isNotBlank(dto.getCourse()), Lesson::getCourse, dto.getCourse())
                .page(Page.of(dto.getPage(), dto.getSize()));
        return PageVo.of(page);
    }

    @Override
    public int deleteItems(List<Long> ids) {
        return lessonMapper.deleteByIds(ids);
    }

    @Override
    public int addLesson(Lesson lesson) {
        return lessonMapper.addLesson(lesson);
    }

    @Override
    public int updateLesson(Lesson lesson) {
        return lessonMapper.updateLesson(lesson);
    }

    @Override
    public Boolean copyLessonClassToClass(Lesson source, Lesson target, boolean isOverwrite) {
        validateLessonParams(source);
        validateLessonParams(target);

        return lessonMapper.copyLessonClassToClass(source, target, isOverwrite);
    }

    private void validateLessonParams(Lesson lesson) {
        if (lesson == null || StringUtils.isBlank(lesson.getAcademicYear())) {
            throw new IllegalArgumentException("课程参数不完整");
        }

        if (lesson.getGrade() <= 0 || lesson.getClassNum() <= 0) {
            throw new IllegalArgumentException("年级和班级必须为正数");
        }

        if (lesson.getSemester() < 1 || lesson.getSemester() > 2) {
            throw new IllegalArgumentException("学期必须为1或2");
        }
    }
}