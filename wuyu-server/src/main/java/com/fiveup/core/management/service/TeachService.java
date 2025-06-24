package com.fiveup.core.management.service;

import com.fiveup.core.management.pojo.*;

import java.util.List;

/**
 * @Author 龙江威
 * @Date 2023/11/20
 */

public interface TeachService {
    List<TeacherVo> getAllTeacher();

    TeacherListVo getTeacherByPage(PageDto dto, long schoolId);

    List<TeacherExcel> searchTeacherList(PageDto dto, long schoolId);

    TeacherInfoVo getTeacherInfo(Long teacherId);

    FormVo getFormObject();

    List<ClassInfoVo> getClassInfo();
}