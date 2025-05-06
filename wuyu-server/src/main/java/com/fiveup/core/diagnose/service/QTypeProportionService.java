package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.bean.student_QTypeProportion;
import com.fiveup.core.diagnose.mapper.QTypeProportionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QTypeProportionService {

    @Autowired
    QTypeProportionMapper qTypeProportionMapper;
    public student_QTypeProportion selectQTypeP(String subject, int grade){
        return qTypeProportionMapper.selectQTypeP(subject,grade);
    }
}
