package com.fiveup.core.studentManager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import com.fiveup.core.studentManager.pojo.StudentVO;

public interface StudentManagerService extends IService<StudentManager> {
    void addStudent(StudentManager studentManager);

    //分页查询学生
    PageBean<StudentVO> getStudentPage(StudentManagerQuery studentManagerQuery);

    void updateStudent(StudentManager studentManager);

    void removeStudent(Integer studentId);

    void export(HttpServletResponse response);

    void importstudent(MultipartFile file);
}
