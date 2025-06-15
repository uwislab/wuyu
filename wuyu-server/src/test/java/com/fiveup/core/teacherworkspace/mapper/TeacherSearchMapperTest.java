package com.fiveup.core.teacherworkspace.mapper;


import com.fiveup.core.teacherworkspace.common.utils.AlgorithmUtils;
import com.fiveup.core.teacherworkspace.model.Teacher;
import org.apache.ibatis.cursor.Cursor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class TeacherSearchMapperTest {
    @Resource
    private TeacherSearchMapper teacherSearchMapper;

    @Test
    public void getAllTeacher() {

    }

    @Test
    public void selectAllTeacher() {
    }
}