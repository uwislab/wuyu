package com.fiveup.core.management.service;

import com.fiveup.core.management.model.Teacher;


import java.util.List;

public interface TeacherEditService {

    void add(List<Teacher> data);

    void edit(Teacher teacher, List<Long> classes);

    void deleteById(Long id);
}
