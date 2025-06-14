package com.fiveup.core;

import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.service.StudentManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentManagerTest {
    @Autowired
    private StudentManagerService studentManagerService;

    @Test
    public void testAdd() {
        StudentManager studentManager = new StudentManager();
        studentManager.setStudentName("小王");
        studentManager.setStudentNum("20210001");
        studentManager.setGender(1);
        studentManager.setClassId(1);
        studentManager.setParentPhoneNum("12345678901");
        studentManager.setDeleted(0);
        studentManager.setIsreview(1);
        studentManager.setIsenter(0);
        studentManager.setSchoolId(1);
        studentManagerService.addStudent(studentManager);
    }
    @Test
    public void testGet() {
        studentManagerService.getStudent(new StudentManagerQuery());
    }

    @Test
    public void testUpdate() {
        StudentManager studentManager = new StudentManager();
        studentManager.setId(1936);
        studentManager.setStudentName("小王");
        studentManager.setStudentNum("20210001");
        studentManagerService.updateStudent(studentManager);
    }
    @Test
    public void testRemove() {
     studentManagerService.removeStudent(1106);
    }
}
