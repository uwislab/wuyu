package com.fiveup.core.teacherworkspace.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface LessonMapper extends BaseMapper<Lesson> {

    void batchInsert(List<Lesson> lessons);

    List<Lesson> pageLessons(PageLessonDto dto);

    Long pageCount(PageLessonDto dto);

    int deleteByIds(List<Long> ids);

    int addLesson(Lesson lesson);

    int updateLesson(Lesson lesson);

    default Boolean copyLessonClassToClass(Lesson source, Lesson target, boolean isOverwrite) {
        LambdaQueryWrapper<Lesson> sourceWrapper = new LambdaQueryWrapper<>();
        sourceWrapper.eq(Lesson::getGrade, source.getGrade())
                .eq(Lesson::getClassNum, source.getClassNum())
                .eq(Lesson::getAcademicYear, source.getAcademicYear())
                .eq(Lesson::getSemester, source.getSemester());

        List<Lesson> sourceLessons = selectList(sourceWrapper);
        if (CollectionUtils.isEmpty(sourceLessons)) {
            throw new IllegalArgumentException("源班级的课程不存在");
        }

        LambdaQueryWrapper<Lesson> targetWrapper = new LambdaQueryWrapper<>();
        targetWrapper.eq(Lesson::getGrade, target.getGrade())
                .eq(Lesson::getClassNum, target.getClassNum())
                .eq(Lesson::getAcademicYear, target.getAcademicYear())
                .eq(Lesson::getSemester, target.getSemester());

        boolean targetExists = exists(targetWrapper);

        if (targetExists && !isOverwrite) {
            throw new IllegalArgumentException("目标班级的课程已存在");
        } else if (targetExists) {
            delete(targetWrapper);
        }

        List<Lesson> newLessons = sourceLessons.stream().map(lesson -> Lesson.builder()
                .id(null)
                .grade(target.getGrade())
                .className(target.getGrade() + "年级" + target.getClassNum() + "班")
                .classNum(target.getClassNum())
                .academicYear(target.getAcademicYear())
                .semester(target.getSemester())
                .course(lesson.getCourse())
                .teacherId(lesson.getTeacherId())
                .teacherName(lesson.getTeacherName())
                .isCurrent(target.getIsCurrent() || false)
                .build()
        ).collect(Collectors.toList());
        batchInsert(newLessons);
        return true;
    }

    @Select("select distinct academic_year from fiveup.basic_lesson")
    List<String> listAcademicYears();
}