package com.fiveup.core.teacherworkspace.service;

import com.fiveup.core.teacherworkspace.model.Teacher;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;


public interface TeacherSearchService {
    PageVo<Teacher> searchTeacher(String teacherName, Long size, Long page);
}