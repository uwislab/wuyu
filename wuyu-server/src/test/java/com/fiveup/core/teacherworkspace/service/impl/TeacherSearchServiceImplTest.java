package com.fiveup.core.teacherworkspace.service.impl;

import cn.hutool.jwt.signers.AlgorithmUtil;
import com.fiveup.core.teacherworkspace.common.utils.AlgorithmUtils;
import com.fiveup.core.teacherworkspace.model.Teacher;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;
import com.fiveup.core.teacherworkspace.service.TeacherSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherSearchServiceImplTest {
    @Autowired
    private TeacherSearchService teacherSearchService;

    @Test
    public void test() {
        TeacherSearchServiceImpl teacherSearchService1 = (TeacherSearchServiceImpl) teacherSearchService;
        System.out.println(teacherSearchService1.searchTeacher("张", 10L, 1L));
    }

}