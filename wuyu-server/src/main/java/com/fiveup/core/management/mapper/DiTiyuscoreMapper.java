package com.fiveup.core.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.DiTiyuscore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XXX
 * @since 2024-11-25
 */
@Mapper
public interface DiTiyuscoreMapper extends BaseMapper<DiTiyuscore> {

    @Select(
        "<script>" +
            "SELECT COUNT(*) " +
            "FROM di_tiyuScore tiyu " +
            "JOIN basic_student s ON tiyu.student_id = s.student_num " +
            "JOIN basic_class c ON s.class_id = c.id " +
            "<where>" +
            "  <if test='classId != null'> AND s.class_id = #{classId} </if>" +
            "  <if test='gradeId != null'> AND s.grade_id = #{gradeId} </if>" +
            "</where>" +
            "</script>"
    )
    long countNum(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("classId")Integer classId,
        @Param("gradeId") Integer gradeId);

    @Select(
        "<script>" +
            "SELECT tiyu.*, s.gender, s.grade_id gradeId, c.class_name className " +
            "FROM di_tiyuScore tiyu " +
            "JOIN basic_student s ON tiyu.student_id = s.student_num " +
            "JOIN basic_class c ON s.class_id = c.id " +
            "<where>" +
            "  <if test='classId != null'> AND s.class_id = #{classId} </if>" +
            "  <if test='gradeId != null'> AND s.grade_id = #{gradeId} </if>" +
            "</where> " +
            "LIMIT #{start}, #{pageSize}" +
            "</script>"
    )
    List<DiTiyuscore> list(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("classId")Integer classId,
        @Param("gradeId") Integer gradeId);

    @Select("select id ,class_name as  className from basic_class where  grade = #{gradeId}")
    List<ClassDTO> selectListById(@Param("gradeId") long gradeId);
}
