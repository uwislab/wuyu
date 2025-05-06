package com.fiveup.core.questionnaire.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.questionnaire.entity.Student;
import com.fiveup.core.questionnaire.mapper.StudentMapper;
import com.fiveup.core.questionnaire.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

}
