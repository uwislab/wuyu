package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.bean.student_QTypeScore;
import com.fiveup.core.diagnose.mapper.QTypeScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QTypeScoreService {

    @Autowired
    QTypeScoreMapper qTypeScoreMapper;

    public student_QTypeScore slectQTypeS(Long id,String name, String subject){
        return qTypeScoreMapper.slectQTypeS(id,name,subject);
    };
}
