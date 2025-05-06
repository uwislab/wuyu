package com.fiveup.core.questionnaire.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.questionnaire.entity.Class;
import com.fiveup.core.questionnaire.mapper.ClassMapper;
import com.fiveup.core.questionnaire.service.ClassService;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {
}
