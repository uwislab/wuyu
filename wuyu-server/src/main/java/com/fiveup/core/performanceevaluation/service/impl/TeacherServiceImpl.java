package com.fiveup.core.performanceevaluation.service.impl;

import com.fiveup.core.cultivation.entity.BasicTeacher;
import com.fiveup.core.performanceevaluation.bean.Teacher;
import com.fiveup.core.performanceevaluation.mapper.TeacherMapper;
import com.fiveup.core.performanceevaluation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 老师的业务逻辑处理实现
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teachers = teacherMapper.selectAll();
        return teachers;
    }

    @Override
    public List<Teacher> getNotInWeight(List<Integer> list) {
        List<Teacher> teachers = teacherMapper.selectNotInWeight(list);
        return teachers;
    }

    @Override
    public List<Teacher> getInWeight(List<Integer> list) {
        return teacherMapper.selectInWeight(list);
    }

    @Override
    public Teacher getByTId(Integer teacherID) {
        Teacher teacher = teacherMapper.selectByTId(teacherID);
        return teacher;
    }

    @Override
    public Teacher getByName(String name) {
        return teacherMapper.selectByName(name);
    }

    @Override
    public List<Integer> selectOtherTeacher(String name) {
        return teacherMapper.selectOtherTeacher(name);
    }

    @Override
    public List<BasicTeacher> getTeacherByName(String realName) {
        return teacherMapper.getTeacherByName(realName);
    }
}
