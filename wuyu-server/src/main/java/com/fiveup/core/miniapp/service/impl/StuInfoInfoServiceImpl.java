package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.management.mapper.StuMapper;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.miniapp.service.StuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Slf4j
@Service
public class StuInfoInfoServiceImpl implements StuInfoService {

    @Resource
    private StuMapper stuMapper;

    @Override
    public StuDTO getStudentInfo(Long studentNum) {
        return stuMapper.getStuInfoByStudentNum(studentNum);
    }

    @Override
    public HashMap<String, Object> getStudentInfoWithFuScore(int studentId) {
        return stuMapper.getStudentInfoWithFuScore(studentId);
    }

    @Override
    public List<StuDTO> getStudentListByName(String studentName){
        List<StuDTO> studentList = stuMapper.getStudentListByName(studentName);
        return studentList;
    }


    @Override
    public List<StuDTO> getAllStudent() {
        List<StuDTO> studentList = stuMapper.getAllStudent();
        return studentList;
    }

    @Override
    public List<StuDTO> getStuListByClassId(Long classId) {
        System.out.println(classId);
        List<StuDTO> studentList = stuMapper.getStuListByClassId(classId);
        return studentList;
    }

    @Override
    public void updateStudentPhone(int studentNum, String phoneNum) {
        stuMapper.updateStudentByNum(studentNum,phoneNum);
    }

    @Override
    public StuDTO getStudentbyStudentNum(String studentNum) {
        return stuMapper.getStuInfoByStudentNum(Long.valueOf(studentNum));
    }

    @Override
    public String getStuNumByStudentName(String studentName) {
        return stuMapper.getStuNumByStudentName(studentName);
    }

    @Override
    public void confirmReview(String studentNum) {
        stuMapper.confirmReview(studentNum);
    }


}
