package com.fiveup.core.studentManager.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.management.model.School;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.mapper.StudentManagerMapper;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.pojo.StudentVO;
import com.fiveup.core.studentManager.service.StudentManagerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    /**
     * 学生分页查询
     * @param studentManagerQuery
     * @return
     */
    @Override
    public PageBean<StudentVO> getStudentPage(StudentManagerQuery studentManagerQuery) {
        System.out.println("前端传来页码"+studentManagerQuery.getPage());
        System.out.println("前端传来每页显示的条数"+studentManagerQuery.getSizeOfPage());
        int sizeOfPage = studentManagerQuery.getSizeOfPage() == null ? 10 : studentManagerQuery.getSizeOfPage();

        if (studentManagerQuery.getPage() == null) {
            studentManagerQuery.setPage(1);
        }

        PageHelper.startPage(studentManagerQuery.getPage(), sizeOfPage);

        //开始查询
        List<StudentVO> list = studentManagerMapper.getStudentPage(studentManagerQuery);
        Page<StudentVO> newPage = (Page<StudentVO>) list;

        //为pagebean赋值
        PageBean<StudentVO> pageBean = new PageBean<>();
        pageBean.setData(newPage.getResult());
        pageBean.setTotalNum((int) newPage.getTotal());
        pageBean.setTotalPage(newPage.getPages());
        pageBean.setSizeOfPage(sizeOfPage);
        pageBean.setSizeOfCurrPage(newPage.getResult().size());
        pageBean.setPage(studentManagerQuery.getPage());

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
    public List<School> getSchool() {
        return studentManagerMapper.getSchool();
    }

    @Override
    public List<String> getClassName() {
        return studentManagerMapper.getClassName();
    }

    @Override
    public List<Integer> getGrade() {
        return studentManagerMapper.getGrade();
    }
}
