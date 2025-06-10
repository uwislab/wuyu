package com.fiveup.core.studentManager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;

public interface StudentManagerService extends IService<StudentManager> {
    void addStudent(StudentManager studentManager);

    PageBean<StudentManager> getStudent(StudentManagerQuery studentManagerQuery);

    void updateStudent(StudentManager studentManager);

    void removeStudent(Integer studentId);

    void export();
}
