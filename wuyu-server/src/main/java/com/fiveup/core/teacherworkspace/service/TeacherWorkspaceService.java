package com.fiveup.core.teacherworkspace.service;

import com.fiveup.core.teacherworkspace.model.*;

import java.util.List;

public interface TeacherWorkspaceService {

    List<Teacher> getAllTeacher();

    Teacher getTeacherInfo(Long id);

    ClassBasicInfo getClassBasicInfo(Long teacher_id);

    List<String> getTeacherByIdentity(int identity);

    List<LessonTeacher> getTeacherByClass(int gradeId, int classId);

    LessonTeacher getMonitorByClass(int gradeId, int classId);

    List<Work> getAllWork(String id, String title, String type);

    List<Lesson> getLessonByTeacherId(Long teacherId);

    int updateWork(Work work);

    int updateTeacherInfo(Teacher teacher);

    int updatePassword(Teacher teacher);

    int deleteWork(Long id);

    List<Work> getWorkByPage(int pageNum, int pageSize, String id, String title, String type);

    int deleteBatch(List<Integer> ids);
}
