package com.fiveup.core.management.mapper;

import com.fiveup.core.management.model.DTO.TeaDTO;
import com.fiveup.core.management.model.Req.TeaAddReq;
import com.fiveup.core.management.model.Req.TeaEditReq;
import com.fiveup.core.management.model.Resp.TeaPageResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;


@Mapper
public interface TeaMapper {

   
    TeaDTO getTeacherById(@Param("teacherId") Long teacherId);

    List<TeaDTO> getTeacherSimpleInfo(@Param("schoolId") Long schoolId);

    List<TeaPageResp> getTeaByCondition(@Param("teacherName") String teacherName, @Param("title") String title, @Param("position") String position, @Param("schoolId")Long schoolId);

    void addOne(TeaAddReq teaAddReq);

    void edit(TeaEditReq teaEditReq);

    void softlyDeleteOne(Long teacherId);

    int deleteBatch(List<Integer> ids);
}
