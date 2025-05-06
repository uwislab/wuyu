package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.miniapp.mapper.TchInfoMapper;
import com.fiveup.core.miniapp.model.TeacherMini;
import com.fiveup.core.miniapp.service.TchInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Slf4j
@Service
public class TchInfoInfoServiceImpl implements TchInfoService {


    @Resource
    private TchInfoMapper tchInfoMapper;

    @Override
    public TeacherMini getMonitorInfoByTchId(Long teacherId) {
        TeacherMini teacherMini;
        teacherMini = tchInfoMapper.getTeacherById(teacherId);
        return teacherMini;
    }

    @Override
    public TeacherMini getMonitorInfoByTchName(String teacherName) {
        TeacherMini teacherMini;
        teacherMini = tchInfoMapper.getTeacherByName(teacherName);
        return teacherMini;
    }

    @Override
    public List<TeacherMini> getTeacherInfoByCLass(int gradeNum,int classNum){
        List<TeacherMini> teacherList = tchInfoMapper.getTeacherInfoByCLass(gradeNum,classNum);
        return teacherList;
    }
}
