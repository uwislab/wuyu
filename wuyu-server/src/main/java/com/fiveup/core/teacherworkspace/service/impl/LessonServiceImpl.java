package com.fiveup.core.teacherworkspace.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.common.exception.ApiException;
import com.fiveup.core.teacherworkspace.common.utils.RegexVerifyUtils;
import com.fiveup.core.teacherworkspace.mapper.LessonMapper;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;
import com.fiveup.core.teacherworkspace.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements LessonService {
    private final LessonMapper lessonMapper;

    @Override
    public List<String> listAcademicYears() {
        return lessonMapper.listAcademicYears();
    }

    @Override
    public PageVo<Lesson> pageLesson(PageLessonDto dto) {
        String academicYear = dto.getAcademicYear();
        if (CharSequenceUtil.isBlank(academicYear) && !RegexVerifyUtils.validAcademicYear(academicYear)) {
            throw new ApiException("学年格式错误, 格式为xxxx-xxxx");
        }
        Page<Lesson> page = this.lambdaQuery()
                .ge(dto.getMinGrade() != null, Lesson::getGrade, dto.getMinGrade())
                .le(dto.getMaxGrade() != null, Lesson::getGrade, dto.getMaxGrade())
                .eq(dto.getClassNum() != null, Lesson::getClassNum, dto.getClassNum())
                .like(CharSequenceUtil.isNotBlank(dto.getCourse()), Lesson::getCourse, dto.getCourse())
                .eq(dto.getSemester() != null, Lesson::getSemester, dto.getSemester())
                .eq(!CharSequenceUtil.isBlank(academicYear), Lesson::getAcademicYear, academicYear)
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

    @Override
    public Boolean copyLessonByLastSemester(String academicYear, int semester, boolean isOverwrite) throws RuntimeException {
        // 参数校验
        if (StringUtils.isBlank(academicYear)) {
            throw new RuntimeException("请选择学年");
        }
        if (semester < 1 || semester > 2) {
            throw new RuntimeException("请选择正确的学期");
        }

        // 查询目标
        LambdaQueryWrapper<Lesson> targetQuery = new LambdaQueryWrapper<>();
        targetQuery.eq(Lesson::getAcademicYear, academicYear).eq(Lesson::getSemester, semester);

        // 查询源
        String sourceYear;
        int sourceSemester;
        boolean isFirstSemester = semester == 1;
        if (isFirstSemester) {
            int year = Integer.parseInt(academicYear.split("-")[0]);
            sourceYear = year - 1 + "-" + year;
            sourceSemester = 2;
        } else {
            sourceYear = academicYear;
            sourceSemester = 1;
        }

        // 源不存在
        LambdaQueryWrapper<Lesson> sourceQuery = new LambdaQueryWrapper<>();
        sourceQuery.eq(Lesson::getAcademicYear, sourceYear).eq(Lesson::getSemester, sourceSemester);
        if (!lessonMapper.exists(sourceQuery)) {
            throw new RuntimeException("没有可复制的课程");
        }

        List<Lesson> sourceLessons = lessonMapper.selectList(sourceQuery);
        List<Lesson> targetLessons = lessonMapper.selectList(targetQuery);
        List<Lesson> insertLessons;

        /*
         * 1. 如果是覆盖，则删除目标，再插入源
         * 2. 如果不是覆盖，则只插入源中不存在的课程
         * */
        if (isOverwrite) {
            lessonMapper.delete(targetQuery);
            insertLessons = sourceLessons.stream()
                    .map(lesson -> {
                        Lesson copy = Lesson.builder()
                                .grade(isFirstSemester ? lesson.getGrade() + 1 : lesson.getGrade())
                                .classNum(lesson.getClassNum())
                                .course(lesson.getCourse())
                                .teacherName(lesson.getTeacherName())
                                .teacherId(lesson.getTeacherId())
                                .academicYear(academicYear)
                                .semester(semester)
                                .isCurrent(false)
                                .build();
                        copy.setClassName(copy.getGrade() + "年级" + copy.getClassNum() + "班");
                        return copy;
                    }).collect(Collectors.toList());
        } else {
            insertLessons = sourceLessons.stream()
                    .filter(sourceLesson -> {
                        return targetLessons.stream()
                                .noneMatch(targetLesson ->
                                        Objects.equals(targetLesson.getCourse(), sourceLesson.getCourse()) &&
                                                Objects.equals(targetLesson.getTeacherId(), sourceLesson.getTeacherId())
                                );
                    }).map(sourceLesson -> {
                        Lesson copy = Lesson.builder()
                                .grade(isFirstSemester ? sourceLesson.getGrade() + 1 : sourceLesson.getGrade())
                                .classNum(sourceLesson.getClassNum())
                                .course(sourceLesson.getCourse())
                                .teacherName(sourceLesson.getTeacherName())
                                .teacherId(sourceLesson.getTeacherId())
                                .academicYear(academicYear)
                                .semester(semester)
                                .isCurrent(false)
                                .build();
                        copy.setClassName(copy.getGrade() + "年级" + copy.getClassNum() + "班");
                        return copy;
                    }).collect(Collectors.toList());
        }

        if (insertLessons.isEmpty()) {
            return true;
        }
        lessonMapper.batchInsert(insertLessons);
        return true;
    }

    @Override
    public int setCurrentByAcademicAndSemester(String academicYear, int semester) {
        // 如果有性能问题，可以改成流式查询
        LambdaUpdateWrapper<Lesson> setWrapper = new LambdaUpdateWrapper<>();
        setWrapper
                .eq(Lesson::getAcademicYear, academicYear)
                .eq(Lesson::getSemester, semester)
                .set(Lesson::getIsCurrent, true);
        if (!lessonMapper.exists(setWrapper)) {
            throw new RuntimeException("不存在该学年课程");
        }

        LambdaUpdateWrapper<Lesson> resetWrapper = new LambdaUpdateWrapper<>();
        resetWrapper.set(Lesson::getIsCurrent, false);
        lessonMapper.update(null, resetWrapper);

        return lessonMapper.update(null, setWrapper);
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