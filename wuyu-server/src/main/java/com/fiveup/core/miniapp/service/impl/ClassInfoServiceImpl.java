package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.ClassInfoMapper;
import com.fiveup.core.miniapp.model.ClassMini;
import com.fiveup.core.miniapp.service.ClassInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Slf4j
@Service
public class ClassInfoServiceImpl implements ClassInfoService {


    @Resource
    private ClassInfoMapper classInfoMapper;

    @Override
    public ClassMini getClassInfoByClassId(Long classId) {
        ClassMini classMini;
        classMini = classInfoMapper.getClassInfoByClassId(classId);
        return classMini;
    }

    @Override
    public ClassMini getStudentClass(Long studentNum){
        ClassMini classMini;
        classMini = classInfoMapper.getStudentClass(studentNum);
        return classMini;
    }

    @Override
    public Long getIdByGradeAndClass(String grade, String className) {
        return classInfoMapper.getIdByGradeAndClass(grade, className);
    }

    @Override
    public int getClassIdByStudentNum(int studentNum){
        return classInfoMapper.getClassIdByStudentNum(studentNum);
    }
}
