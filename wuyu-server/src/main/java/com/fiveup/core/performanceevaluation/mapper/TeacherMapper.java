package com.fiveup.core.performanceevaluation.mapper;

import com.fiveup.core.cultivation.entity.BasicTeacher;
import com.fiveup.core.performanceevaluation.bean.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
/**
 * 对教师的数据操作
 */
public interface TeacherMapper {
    /**
     * 根据教师ID查询数据
     * @param teacherID
     * @return
     */
    Teacher selectByTId(Integer teacherID);

    /**
     * 查询所有数据
     * @return
     */
    @Select("select id as teacherId,username,password from basic_teacher")
    List<Teacher> selectAll();

    /**
     * 查询还未为课程设置权重的老师
     * @return
     */
    List<Teacher> selectNotInWeight(List<Integer> list);

    List<Teacher> selectInWeight(List<Integer> list);

    Teacher selectByName(String name);

    List<Integer> selectOtherTeacher(String name);

    @Select("SELECT * FROM basic_teacher WHERE teacher_name = #{name} and id = #{id}")
    Teacher getTeach(Long id, String name);
    @Select("SELECT * FROM basic_teacher WHERE teacher_name = #{realName} ")

    List<BasicTeacher> getTeacherByName(String realName);
}
