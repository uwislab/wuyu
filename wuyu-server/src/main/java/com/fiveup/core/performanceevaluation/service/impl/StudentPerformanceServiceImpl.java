package com.fiveup.core.performanceevaluation.service.impl;

import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.bean.Teacher;
import com.fiveup.core.performanceevaluation.dto.PageDto;
import com.fiveup.core.performanceevaluation.mapper.StudentPerformanceMapper;
import com.fiveup.core.performanceevaluation.service.StudentPerformanceService;
import com.fiveup.core.performanceevaluation.vo.Average;
import com.fiveup.core.performanceevaluation.vo.StudentPerformanceVO;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;
import jnr.ffi.annotations.In;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 学生表现业务逻辑实现
 */
@Service
public class StudentPerformanceServiceImpl implements StudentPerformanceService {

    @Autowired
    private StudentPerformanceMapper studentPerformanceMapper;

    @Override
    public void add(StudentPerformance studentPerformance) {
        studentPerformanceMapper.insert(studentPerformance);
    }

    @Override
    public void deleteById(Integer id) {
        studentPerformanceMapper.deleteById(id);
    }

    @Override
    public Integer[] getAverageGrade(Integer tid) {
        List<StudentPerformanceVO> studentsList = studentPerformanceMapper.selectPaginationByTeacherId(tid,0,1000);
        Integer[] list = new Integer[5];
        for (int i = 0; i < 5; i++) {
            list[i] = 0;
        }
        for (StudentPerformanceVO studentPerformanceVO:studentsList){
            list[0] = list[0]+studentPerformanceVO.getVirtue();
            list[1] = list[1]+studentPerformanceVO.getIntelligence();
            list[2] = list[2]+studentPerformanceVO.getSports();
            list[3] = list[3]+studentPerformanceVO.getArt();
            list[4] = list[4]+studentPerformanceVO.getLabor();
        }
        Integer count = studentPerformanceMapper.getCountByTeacher(tid);
        for (int i = 0; i < 5; i++) {
            list[i] = list[i]==0?0:list[i]*10/count;
        }
        return list;
    }

    @Override
    public void updateById(StudentPerformance studentPerformance) {
        studentPerformanceMapper.updateById(studentPerformance);
    }

    @Override
    public List<StudentPerformanceVO> getAll() {
        List<StudentPerformanceVO> studentPerformanceVOS = studentPerformanceMapper.selectAll();
        return studentPerformanceVOS;
    }

    @Override
    public List<StudentPerformanceVO> getAllByTidChange(Integer tid) {
        List<StudentPerformanceVO> studentPerformanceVOS =null;
        if(tid==-1)
        {
            studentPerformanceVOS = studentPerformanceMapper.selectAll();
        }
        else {
            studentPerformanceVOS = studentPerformanceMapper.selectByTid2(tid);

        }
        return studentPerformanceVOS;
    }


    @Override
    public List<StudentPerformanceVO> getByTId(Integer tid) {
        List<StudentPerformance> studentPerformances = studentPerformanceMapper.selectByOwnTId(tid);
        if(studentPerformances.size()==0){
            studentPerformances = studentPerformanceMapper.selectByTId(tid);
        }
        List<StudentPerformanceVO> studentPerformanceVOS = new ArrayList<>();
        for (StudentPerformance studentPerformance:studentPerformances){
            StudentPerformanceVO studentPerformanceVO = new StudentPerformanceVO();
            studentPerformanceVO.setId(studentPerformance.getId());
            studentPerformanceVO.setName(studentPerformance.getName());
            studentPerformanceVO.setVirtue(studentPerformance.getVirtue());
            studentPerformanceVO.setIntelligence(studentPerformance.getIntelligence());
            studentPerformanceVO.setArt(studentPerformance.getArt());
            studentPerformanceVO.setRemark(studentPerformance.getRemark());
            studentPerformanceVO.setLabor(studentPerformance.getLabor());
            studentPerformanceVO.setSports(studentPerformance.getSports());
            studentPerformanceVO.setSid(studentPerformance.getSid());
            studentPerformanceVO.setTeacher(studentPerformanceMapper.selectTeacherByTid(tid));
            studentPerformanceMapper.updateTid(studentPerformance.getId(),tid);
            studentPerformanceVOS.add(studentPerformanceVO);
        }

        return studentPerformanceVOS;
    }

    @Override
    public List<StudentPerformanceVO> AutoRemark(Integer tid, Integer year_month) {
        List<StudentPerformanceVO> studentPerformanceVOS = studentPerformanceMapper.AutoRemark(tid, year_month);
        return studentPerformanceVOS;
    }

    @Override
    public Integer getCount(String sql) {
        Integer count = studentPerformanceMapper.selectCountBySql(sql);
        return count;
    }

    @Override
    public Integer getCountByTeacher(Integer tid) {
        return studentPerformanceMapper.getCountByTeacher(tid);
    }

    @Override
    public List<StudentPerformanceVO> getPagination(Integer start, Integer pageSize) {
        List<StudentPerformanceVO> studentPerformanceVOS = studentPerformanceMapper.selectPagination(start, pageSize);
        return studentPerformanceVOS;
    }

    @Override
    public List<StudentPerformanceVO> getPaginationByTeacherId(Integer tid, Integer start, Integer pageSize) {
        List<StudentPerformanceVO> studentPerformanceVOS = studentPerformanceMapper.selectPaginationByTeacherId(tid,start, pageSize);
        return studentPerformanceVOS;
    }

    @Override
    public StudentPerformanceVO getBySid(Integer sid) {
        StudentPerformanceVO studentPerformanceVO = null;
        StudentPerformance studentPerformance = null;
        StudentPerformance[] kk = studentPerformanceMapper.selectBySid(sid);
        if(kk.length!=0){
            studentPerformance = kk[0];
        }
        if (studentPerformance!=null){
            studentPerformanceVO = new StudentPerformanceVO();
            studentPerformanceVO.setId(studentPerformance.getId());
            studentPerformanceVO.setName(studentPerformance.getName());
            studentPerformanceVO.setVirtue(studentPerformance.getVirtue());
            studentPerformanceVO.setIntelligence(studentPerformance.getIntelligence());
            studentPerformanceVO.setArt(studentPerformance.getArt());
            studentPerformanceVO.setRemark(studentPerformance.getRemark()==null?"暂时没有评语":studentPerformance.getRemark());
            studentPerformanceVO.setLabor(studentPerformance.getLabor());
            studentPerformanceVO.setSports(studentPerformance.getSports());
            studentPerformanceVO.setSid(studentPerformance.getSid());
            Teacher teacher = studentPerformanceMapper.selectTeacherById(studentPerformance.getSid());
            if (teacher!=null){
                studentPerformanceVO.setTeacher(teacher);
                studentPerformanceMapper.updateTid(studentPerformance.getId(),studentPerformanceVO.getTeacher().getTeacherID());
            }
            else {
                studentPerformanceVO.setTeacher(studentPerformanceMapper.selectTeacherById(1));
            }
        }
        return studentPerformanceVO;
    }

    @Override
    public StudentPerformanceVO getById(Integer id) {
        StudentPerformanceVO studentPerformanceVO = new StudentPerformanceVO();
        StudentPerformance studentPerformance = studentPerformanceMapper.selectById(id);
        studentPerformanceVO.setId(studentPerformance.getId());
        studentPerformanceVO.setName(studentPerformance.getName());
        studentPerformanceVO.setVirtue(studentPerformance.getVirtue());
        studentPerformanceVO.setIntelligence(studentPerformance.getIntelligence());
        studentPerformanceVO.setArt(studentPerformance.getArt());
        studentPerformanceVO.setRemark(studentPerformance.getRemark());
        studentPerformanceVO.setLabor(studentPerformance.getLabor());
        studentPerformanceVO.setSports(studentPerformance.getSports());
        studentPerformanceVO.setSid(studentPerformance.getSid());
        studentPerformanceVO.setTeacher(studentPerformanceMapper.selectTeacherById(studentPerformance.getSid()));
        studentPerformanceMapper.updateTid(id,studentPerformanceVO.getTeacher().getTeacherID());
        return studentPerformanceVO;
    }

    @Override
    public Average getAverageByTid(Integer tid) {
        Average average = studentPerformanceMapper.selectAverageByTid(tid);
        return average;
    }

    @Override
    public List<Integer> getSubjectScoreSum(Integer tid, Integer sid) {
        List<Integer> sums = studentPerformanceMapper.selectSubjectScoreSum(tid, sid);
        return sums;
    }

    @Override
    public List<Corpus> getCorpusBySubject(Integer id) {
        List<Corpus> cor = studentPerformanceMapper.selectCorById(id);
        return cor;
    }

    @Override
    public List<String> getItem() {
        List<Integer> time = studentPerformanceMapper.selectItem();
        Set<String> academicYears = new LinkedHashSet<>();
        for(Integer timing: time){
            String academicYear = getAcademicYear(timing);
            academicYears.add(academicYear);
        }
        List<String> list = academicYears.stream().collect(Collectors.toList());
        return list;
    }


//    @Override
//    public List<StudentPerformanceVO> getAll() {
//        List<StudentPerformanceVO> studentPerformanceVOS = studentPerformanceMapper.selectAll();
//        return studentPerformanceVOS;
//    }
    @Override
    public List<StudentPerformanceVO> getAllByItem(Integer tid, Integer year_month) {
        List<StudentPerformanceVO> studentPerformanceVOS =null;
        if(tid==-1)
        {
            studentPerformanceVOS = studentPerformanceMapper.selectAllByItemWithoutTid(tid, year_month);
        }
        else {
             studentPerformanceVOS = studentPerformanceMapper.selectAllByItem(tid, year_month);

        }
     return studentPerformanceVOS;
    }

    public static String getAcademicYear(int semesterCode) {
        int year = semesterCode / 100;
        int month = semesterCode % 100;

        String semester = (month >= 1 && month <= 6) ? "上学期" : "下学期";
        return year + semester;
    }
}
