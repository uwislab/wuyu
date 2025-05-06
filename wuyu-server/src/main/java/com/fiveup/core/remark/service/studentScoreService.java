package com.fiveup.core.remark.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.remark.entity.studentScore;
import com.fiveup.core.remark.mapper.StudentScoreALLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class studentScoreService extends ServiceImpl<StudentScoreALLMapper, studentScore> {

}
