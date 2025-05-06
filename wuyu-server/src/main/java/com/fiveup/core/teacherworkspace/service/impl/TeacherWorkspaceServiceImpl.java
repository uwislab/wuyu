package com.fiveup.core.teacherworkspace.service.impl;


import com.fiveup.core.teacherworkspace.mapper.TeacherWorkspaceMapper;
import com.fiveup.core.teacherworkspace.model.*;
import com.fiveup.core.teacherworkspace.service.TeacherWorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ParentServiceImpl
 * @Author: shilin
 * @Date: 2022/9/18 16:32
 */
@Service
public class TeacherWorkspaceServiceImpl implements TeacherWorkspaceService {

    @Autowired
    TeacherWorkspaceMapper teacherWorkspaceMapper;


    @Override
    public List<Teacher> getAllTeacher() {
        return teacherWorkspaceMapper.getAllTeacher();
    }

    @Override
    public Teacher getTeacherInfo(Long id) {

        return teacherWorkspaceMapper.getTeacherInfo(id);
    }

    @Override
    public ClassBasicInfo getClassBasicInfo(Long teacher_id) {
        return teacherWorkspaceMapper.getClassBasicInfo(teacher_id);
    }

    @Override
    public List<String> getTeacherByIdentity(int identity) {
        List<String> teacherList;
        teacherList = teacherWorkspaceMapper.getTeacherByIdentity(identity);
        return teacherList;
    }

    @Override
    public List<LessonTeacher> getTeacherByClass(int gradeId, int classId) {
        List<LessonTeacher> lessonTeacherList;
        lessonTeacherList = teacherWorkspaceMapper.getTeacherByClass(gradeId, classId);
        return lessonTeacherList;
    }

    @Override
    public LessonTeacher getMonitorByClass(int gradeId, int classId) {
        LessonTeacher lessonTeacherList;
        lessonTeacherList = teacherWorkspaceMapper.getMonitorByClass(gradeId, classId);
        return lessonTeacherList;
    }

    @Override
    public List<Lesson> getLessonByTeacherId(Long teacherId) {
        return teacherWorkspaceMapper.getLessoByTeacherId(teacherId);
    }

    @Override
    public int updateWork(Work work) {
        System.out.println(work);
        if (work.getId() == null) {
            return teacherWorkspaceMapper.insertWork(work);
        } else {
            return teacherWorkspaceMapper.updateWork(work);
        }
//        return teacherWorkspaceMapper.updateWork(work);

    }

    @Override
    public int updateTeacherInfo(Teacher teacher) {

        return teacherWorkspaceMapper.updateTeacherInfo(teacher);
    }

    @Override
    public int updatePassword(Teacher teacher) {
        System.out.println("hdsajdlaskja"+teacher);
        int res = teacherWorkspaceMapper.updateUserPassword(teacher);
        res = teacherWorkspaceMapper.updatePassword(teacher);
        return res;
    }

    @Override
    public int deleteWork(Long id) {
        return teacherWorkspaceMapper.deleteWork(id);
    }


    @Override
    public int deleteBatch(List<Integer> ids) {
//        String str = String.join(",",ids);
        return teacherWorkspaceMapper.deleteBatch(ids);
    }

    //工程实践4 古旭
    @Override
    public List<Work> getAllWork(String id, String title, String type) {
        return teacherWorkspaceMapper.getAllWork(id, title, type);
    }

    @Override
    public List<Work> getWorkByPage(int pageNum, int pageSize, String id, String title, String type) {
        return teacherWorkspaceMapper.getWorkByPage(pageNum, pageSize, id, title, type);
    }
}
