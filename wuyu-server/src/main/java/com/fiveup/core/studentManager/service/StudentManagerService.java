package com.fiveup.core.studentManager.service;

import com.baomidou.mybatisplus.extension.service.IService;
<<<<<<< HEAD
=======
import com.fiveup.core.management.model.School;
>>>>>>> bce9104829d01d0a3e3e5a9af9940f0ecd6fcf4c
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentInsertDTO;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;

public interface StudentManagerService extends IService<StudentManager> {
    void addStudent(StudentInsertDTO studentInsertDTO);

    PageBean<StudentManager> getStudent(StudentManagerQuery studentManagerQuery);

    void updateStudent(StudentInsertDTO studentInsertDTO);

    void removeStudent(Integer studentId);
}
