package com.fiveup.core.questionnaire.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.questionnaire.entity.Activity;
import com.fiveup.core.questionnaire.mapper.ActivityMapper;
import com.fiveup.core.questionnaire.service.ActivityService;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
}
