package com.fiveup.core.fuScore.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.fiveup.core.fuScore.mapper.StudentFuScoreMapper;
import com.fiveup.core.fuScore.model.*;
import com.fiveup.core.fuScore.service.StudentFuScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Slf4j
@Service
public class StudentFuScoreServiceImpl implements StudentFuScoreService {


    @Resource
    private StudentFuScoreMapper studentFuScoreMapper;


    @Override
    public List<StudentFuScore> getStudentsFuScore(int studentId) {
        List<StudentFuScore> studentScore;
        studentScore = studentFuScoreMapper.getStudentsFuScore(studentId);
        return studentScore;
    }

    //通过学号或者姓名获取学生所有五育成绩以及期末评语
    @Override
    public List<StudentFuScore> getStudentsFuScore(String studentName, int studentId) {
        List<StudentFuScore> studentFuScore = null;
        if (studentId != 0) {
            studentFuScore = studentFuScoreMapper.getStudentFuScoreById(studentId);
        } else if (studentName != null) {
            {
                studentFuScore = studentFuScoreMapper.getStudentFuScoreByName(studentName);
            }
        }
        return studentFuScore;
    }

    @Override
    public List<StudentFuScore> getScoreByInfo(String info) {
        return studentFuScoreMapper.getScoreByInfo(info);
    }


    @Override
    public StudentSemesterScore getStudentSemesterScores(Integer studentId, String studentName, String semester) {
        return studentFuScoreMapper.getStudentSemesterScores(studentId, studentName, semester);
    }

    @Override
    public List<StuSemesterTotalScore> getStuSemester(Integer studentId, String studentName) {
        return studentFuScoreMapper.getStudentSemesterTotalScores(studentId, studentName);
    }

    public List<StudentInfo> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String keywordLower = keyword.toLowerCase();

        // 获取全部学生数据（可考虑缓存）
        List<StudentInfo> allStudents = studentFuScoreMapper.getAllStudents();

        return allStudents.stream()
                .filter(student -> {
                    String name = student.getStudentName();
                    String pinyin = PinyinUtil.getPinyin(name, "").toLowerCase(); // zhangsan
                    String initials = name.chars()
                            .mapToObj(c -> PinyinUtil.getFirstLetter(String.valueOf((char) c), ""))
                            .collect(Collectors.joining())
                            .toLowerCase(); // ZS -> zs

                    // 条件一：姓名相关匹配
                    boolean nameMatch = name.contains(keyword) ||
                            pinyin.contains(keywordLower) ||
                            initials.contains(keywordLower);

                    // 条件二：学号匹配（字符串模糊匹配，避免类型问题）
                    boolean idMatch = student.getStudentId().toString().contains(keyword);

                    return nameMatch || idMatch;
                })
                .limit(20)
                .collect(Collectors.toList());
    }

    // 解析字符串为 long 类型的辅助方法
    private Optional<Long> parseStudentId(String keyword) {
        try {
            return Optional.of(Long.parseLong(keyword));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
//    public List<StudentInfo> searchStudents(String keyword) {
//        if (keyword == null || keyword.trim().isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        String keywordLower = keyword.toLowerCase();
//
//        return studentFuScoreMapper.getAllStudents().stream()
//                .filter(student -> {
//                    String name = student.getStudentName();
//                    String pinyin = PinyinUtil.getPinyin(name, "").toLowerCase(); // zhangsan
//                    String initials = name.chars()
//                            .mapToObj(c -> PinyinUtil.getFirstLetter(String.valueOf((char) c), ""))
//                            .collect(Collectors.joining())
//                            .toLowerCase(); // ZS -> zs
//
//                    // 匹配条件：姓名包含关键字、拼音包含关键字、拼音首字母包含关键字
//                    return name.contains(keyword) ||
//                            pinyin.contains(keywordLower) ||
//                            initials.contains(keywordLower);
//                })
//                .limit(20)
//                .collect(Collectors.toList());
//    }

    public List<StudentSemesterDto> getStudentSemesters(Integer studentId) {
        return studentFuScoreMapper.selectStudentSemesters(studentId);
    }

    @Override
    public ClassAndGradeScoreResponse getClassAndGradeAvgScores(Integer studentId, String semesterName , int gradeLevel) {
        return studentFuScoreMapper.getClassAndGradeScores(studentId, semesterName, gradeLevel);
    }
}
