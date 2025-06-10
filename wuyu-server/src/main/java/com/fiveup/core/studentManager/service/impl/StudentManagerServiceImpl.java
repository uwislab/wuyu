package com.fiveup.core.studentManager.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.mapper.StudentManagerMapper;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.service.StudentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentManagerServiceImpl extends ServiceImpl<StudentManagerMapper, StudentManager> implements StudentManagerService {
    @Autowired
    private StudentManagerMapper studentManagerMapper;
    @Override
    public void addStudent(StudentManager studentManager) {
        studentManager.setDeleted(0);
        studentManager.setIsreview(0);
        studentManager.setIsenter(0);
        studentManagerMapper.insert(studentManager);
    }

    @Override
    public PageBean<StudentManager> getStudentPage(StudentManagerQuery studentManagerQuery) {
        return null;
    }


    public PageBean<StudentManager> getStudent1(StudentManagerQuery studentManagerQuery) {
        System.out.println("前端传来页码"+studentManagerQuery.getPage());
        System.out.println("前端传来每页显示的条数"+studentManagerQuery.getSizeOfPage());
        int sizeOfPage = studentManagerQuery.getSizeOfPage() == null ? 10 : studentManagerQuery.getSizeOfPage();
        if (studentManagerQuery.getPage() == null) {
            studentManagerQuery.setPage(1);
        }
        Page<StudentManager> newPage = new Page<>(studentManagerQuery.getPage(), sizeOfPage);
        QueryWrapper<StudentManager> queryWrapper = new QueryWrapper<>();
        //学生姓名
        if (studentManagerQuery.getStudentName() != null && !studentManagerQuery.getStudentName().isEmpty()) {
            queryWrapper.like("student_name", studentManagerQuery.getStudentName());
        }
        //学号
        if (studentManagerQuery.getStudentNum() != null && !studentManagerQuery.getStudentNum().isEmpty()) {
            queryWrapper.like("student_num", studentManagerQuery.getStudentNum());
        }
        //性别
        if (studentManagerQuery.getGender() != null) {
            queryWrapper.eq("gender", studentManagerQuery.getGender());
        }
        //学生班级
        if (studentManagerQuery.getClassId() != null) {
            queryWrapper.eq("class_id", studentManagerQuery.getClassId());
        }
        //年级
        if (studentManagerQuery.getGradeId() != null) {
            queryWrapper.eq("grade_id", studentManagerQuery.getGradeId());
        }
        //学校
        if (studentManagerQuery.getSchoolId() != null) {
            queryWrapper.eq("school_id", studentManagerQuery.getSchoolId());
        }
        queryWrapper.eq("deleted", 0);
        newPage = studentManagerMapper.selectPage(newPage, queryWrapper);

        PageBean<StudentManager> pageBean = new PageBean<>();
        pageBean.setData(newPage.getRecords());
        pageBean.setTotalNum((int) newPage.getTotal());
        pageBean.setTotalPage((int) newPage.getPages());
        pageBean.setSizeOfPage(sizeOfPage);
        pageBean.setSizeOfCurrPage(newPage.getRecords().size());
        pageBean.setPage(studentManagerQuery.getPage());
        System.out.println(pageBean);
        return pageBean;
    }

    @Override
    public void updateStudent(StudentManager studentManager) {
        studentManagerMapper.updateById(studentManager);
    }

    @Override
    public void removeStudent(Integer studentId) {
        QueryWrapper<StudentManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", studentId);
        StudentManager studentManager = studentManagerMapper.selectOne(queryWrapper);
        studentManager.setDeleted(1);
        studentManagerMapper.updateById(studentManager);
    }

    @Override
    public void export() {

    }


}
