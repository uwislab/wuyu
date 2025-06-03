package com.fiveup.core.teacherworkspace.mapper;



import com.fiveup.core.teacherworkspace.model.Teacher;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;



@Mapper
public interface TeacherSearchMapper extends BaseMapper<Teacher> {

    @Select("SELECT * FROM basic_teacher WHERE teacher_name like concat(#{pattern}, '%')")
    Cursor<Teacher> selectTeacherByChinese(String pattern);
}

