package com.fiveup.core.commentgeneration.service.Impl;

import com.fiveup.core.commentgeneration.bean.Subject;
import com.fiveup.core.commentgeneration.mapper.SubjectMapper;
import com.fiveup.core.commentgeneration.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科目业务逻辑处理实现
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> getAll() {
        List<Subject> subjects = subjectMapper.selectAll();
        return subjects;
    }

    @Override
    public Subject getById(Integer id) {
        Subject subject = subjectMapper.selectById(id);
        return subject;
    }
}
