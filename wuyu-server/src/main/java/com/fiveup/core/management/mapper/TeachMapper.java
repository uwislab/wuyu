package com.fiveup.core.management.mapper;

import com.fiveup.core.management.pojo.*;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import java.util.List;

/**
 * @Author 龙江威
 * @Date 2023/11/20
 */
@Mapper
public interface TeachMapper {

    /**
     * 获取所有未删除的教师
     * @return List of TeacherVo
     */
    @Select("select * from basic_teacher where deleted = 0")
    List<TeacherVo> getAll();

    /**
     * 根据教师ID查询教师信息
     * @param teacherId 教师ID
     * @return TeacherVo
     */
    @Select("select * from basic_teacher where id = #{teacherId} and deleted = 0")
    TeacherVo getTeacherById(long teacherId);

    /**
     * 获取教师列表
     * @param schoolId 学校ID
     * @param teacherName 教师姓名
     * @param title 职称
     * @param position 职位
     * @return List of TeacherExcel
     */
    List<TeacherExcel> getTeachList(long schoolId, String teacherName, String title, String position);

    /**
     * 分页查询教师
     * @param schoolId 学校ID
     * @param dto 分页参数
     * @param classList 班级列表
     * @return List of TeacherVo
     */
    List<TeacherVo> getTeacherByPage(long schoolId, PageDto dto, List<Integer> classList);

    /**
     * 获取教师总数
     * @return Integer
     */
    List<Integer> getTotalTeacherCount();

    /**
     * 获取教师总数
     * @return Integer
     */
    @Select("select count(*) as total from basic_teacher where deleted = 0")
    Integer getCount();

    /**
     * 获取所有等级
     * @return List of Integer (Grade)
     */
    @Select("select distinct grade from basic_class where deleted = 0")
    List<Integer> getGradeList();

    /**
     * 获取所有职位
     * @return List of String (Position)
     */
    @Select("select distinct position from basic_teacher where deleted = 0")
    List<String> getPositionList();

    /**
     * 根据年级获取班级ID
     * @param grade 年级
     * @return List of Long (Class ID)
     */
    @Select("select id from basic_class where grade = #{grade} and deleted = 0")
    List<Integer> getClassByGrade(String grade);

    /**
     * 获取班级信息
     * @return List of ClassInfoVo
     */
    @Select("select a.class_id, a.grade, a.class_name, a.class " +
            "from teacher_class a " +
            "left join basic_class b on a.class_id = b.id " +
            "where a.deleted = 0 and b.deleted = 0 " +
            "order by a.grade, a.class")
    List<ClassInfoVo> getClassInfo();


    /**
     * 根据教师ID获取教师详细信息
     * @param teacherId 教师ID
     * @return TeacherInfoVo
     */
    TeacherInfoVo getTeacherInfoById(Long teacherId);
}
