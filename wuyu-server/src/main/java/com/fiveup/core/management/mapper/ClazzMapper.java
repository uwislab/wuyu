package com.fiveup.core.management.mapper;

import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.Req.ClassAddReq;
import com.fiveup.core.management.model.Req.ClassEditReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/23
 */
@Mapper
public interface ClazzMapper {

    ClassDTO selectOneById(@Param("id") Long id);

    List<ClassDTO> getAllSimpleClassList(@Param("schoolId") Long schoolId);
    List<ClassDTO> getAllSimpleClassListByGrade(@Param("grade") String grade, @Param("schoolId") Long schoolId);

    List<ClassDTO> getClassListByConditions(@Param("grade") String grade, @Param("monitorId") Long monitorId, @Param("schoolId") Long schoolId);

    void addClass(ClassAddReq classAddReq);

    void DynamicallyUpdateOne(ClassEditReq classEditReq);

    void softlyDelete(@Param("id") Long id);


    List<String> getGradesBySchoolId(Long schoolId);


    List<ClassDTO> getClassListByGradeAndSchoolId(String grade, Long schoolId);

    int deleteBatch(List<Integer> ids);

    int getClassByGradeIdAndClassId(Integer gradeId,Integer classId);
}
