package com.fiveup.core.teacherworkspace.mapper;


import com.fiveup.core.teacherworkspace.common.utils.AlgorithmUtils;
import com.fiveup.core.teacherworkspace.model.Teacher;
import com.mybatisflex.core.query.QueryWrapper;
import org.apache.ibatis.cursor.Cursor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.fiveup.core.teacherworkspace.model.table.TeacherTableDef.TEACHER;

@SpringBootTest
class TeacherSearchMapperTest {
    @Resource
    private TeacherSearchMapper teacherSearchMapper;

    @Test
    @Transactional
    public void getAllTeacher() {
//        List<String> chinese = AlgorithmUtils.splitChineseAndNonChinese("张123");
//        System.out.println(chinese);
//        Cursor<Teacher> teachers = teacherSearchMapper.selectTeacherByChinese(chinese);
//        for (Teacher teacher : teachers) {
//            System.out.println(teacher);
//        }


    }

    @Test
    @Transactional
    public void selectAllTeacher() {
        List<String> chinese = AlgorithmUtils.splitChineseAndNonChinese("张123");
        System.out.println(chinese);
        String regex = "";
        for (String s : chinese) {
            char c = s.charAt(0);
            // 如果是中文，则直接添加到正则表达式中
            if ('\u4e00' <= c && c <= '\u9fff') {
                regex += s;
            } else {
                // 如果是数字或字母，则添加百分号
                regex += "%";
            }
        }
//        Cursor<Teacher> teachers = teacherSearchMapper.selectTeacherByChinese("6%");
//        for (Teacher teacher : teachers) {
//            System.out.println(teacher);
//        }
    }
}