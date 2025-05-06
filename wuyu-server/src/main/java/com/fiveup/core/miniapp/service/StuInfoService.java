package com.fiveup.core.miniapp.service;

import com.fiveup.core.management.model.DTO.StuDTO;

import java.util.HashMap;
import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
public interface StuInfoService {

    StuDTO getStudentInfo(Long studentNum);

    HashMap<String, Object> getStudentInfoWithFuScore(int studentId);


    List<StuDTO> getStudentListByName(String studentName);

    List<StuDTO> getAllStudent();

    List<StuDTO> getStuListByClassId(Long classId);

    void updateStudentPhone(int studentNum, String phoneNum);

    StuDTO getStudentbyStudentNum(String studentNum);

    String getStuNumByStudentName(String studentName);
    void confirmReview(String studentNum);
}
