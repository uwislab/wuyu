package com.fiveup.core.teacherworkspace.service.impl;


import com.fiveup.core.teacherworkspace.common.utils.AlgorithmUtils;
import com.fiveup.core.teacherworkspace.mapper.TeacherSearchMapper;
import com.fiveup.core.teacherworkspace.model.Teacher;
import com.fiveup.core.teacherworkspace.model.vo.PageVo;
import com.fiveup.core.teacherworkspace.service.TeacherSearchService;
import com.mybatisflex.core.query.QueryWrapper;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
public class TeacherSearchServiceImpl implements TeacherSearchService {
    @Autowired
    private TeacherSearchMapper teacherSearchMapper;

    @Override
    @Transactional
    public PageVo<Teacher> searchTeacher(String teacherName, Long size, Long page) {
        Cursor<Teacher> teachers = null;
        teacherName = teacherName.trim().toLowerCase();
        List<Teacher> teacherList = new ArrayList<>();

        PageVo<Teacher> pageVo = new PageVo<>();
        if (AlgorithmUtils.containsChinese(teacherName)) {
            teachers = teacherSearchMapper.selectTeacherByChinese(getPattern(teacherName));
        }
        else {
            teachers = teacherSearchMapper.selectCursorByQuery(QueryWrapper.create());
        }
        Long recordCnt = 0l;
        for (Teacher t : teachers) {
            t.setPassword("");
            if (AlgorithmUtils.isSubsequenceContainChinese(teacherName, t.getTeacherName())
                && t.getDeleted() == 0) {
                recordCnt++;
                if (recordCnt > (page - 1) * size && recordCnt <= page * size) {
                    teacherList.add(t);
                }
            }
        }
        pageVo.setTotal(recordCnt);
        pageVo.setRecords(teacherList);
        pageVo.setRecords(teacherList);
        pageVo.setPages(pageVo.getTotal() / size + 1);
        pageVo.setSize(size);
        pageVo.setPage(page);
        return pageVo;
    }

    private String getPattern(String str) {
        String pattern = "";
        List<String> chinese = AlgorithmUtils.splitChineseAndNonChinese(str);
        for (String s : chinese) {
            char c = s.charAt(0);
            // 如果是中文，则直接添加到正则表达式中
            if ('\u4e00' <= c && c <= '\u9fff') {
                pattern += s;
            } else {
                // 如果是数字或字母，则添加百分号
                pattern += "%";
            }
        }
        return pattern;
    }
}
