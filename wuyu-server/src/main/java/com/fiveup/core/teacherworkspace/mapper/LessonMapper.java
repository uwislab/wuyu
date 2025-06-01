package com.fiveup.core.teacherworkspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LessonMapper extends BaseMapper<Lesson> {

    void batchInsert(List<Lesson> lessons);

    List<Lesson> pageLessons(PageLessonDto dto);

    Long pageCount(PageLessonDto dto);

    int deleteByIds(List<Long> ids);

    int addLesson(Lesson lesson);

    int updateLesson(Lesson lesson);
}
