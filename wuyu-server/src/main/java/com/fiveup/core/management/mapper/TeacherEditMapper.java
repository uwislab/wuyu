package com.fiveup.core.management.mapper;

import com.fiveup.core.management.model.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface TeacherEditMapper {

    @Insert("insert into basic_teacher(" +
            "teacher_name, gender, phone_num, position, title, role, deleted, school_id, username) values(" +
            "#{teacherName}, #{gender}, #{phoneNum}, #{position}, #{title}, #{role}, 0, #{schoolId}, #{teacherName}" +
            ")")
    void add(Teacher teacher);

    @Update("update basic_teacher set teacher_name = #{teacherName}, gender = #{gender}, phone_num = #{phoneNum}, position = #{position}, title = #{title}, role = #{role} where id = #{id}")
    void edit(Teacher teacher);

    @Update("insert into teacher_class(" +
            "teacher_id, class_id, teacherName, deleted) values(" +
            "#{teacherId}, #{classId}, #{teacherName}, 0" +
            ")" +
            "on duplicate key update deleted = 0;")
    void _edit(Long teacherId, Long classId, String teacherName);


    @Update("update basic_teacher set deleted = 1 where id = #{id}")
    void deleteById(Long id);

    @Update("update teacher_class set deleted = 1 where teacher_id = #{id}")
    void _deleteById(Long id);

    @Update("update basic_class set monitor_id = NULL where monitor_id = #{id}")
    void _deleteMonitorById(Long id);
}
