package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.bean.student_Comment;
import com.fiveup.core.diagnose.mapper.SPcommentMapper;
import com.fiveup.core.remark.entity.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SPcommentService {

    @Autowired
    SPcommentMapper sPcommentMapper;
    public void doComment(Long id,String comment){
        LocalDateTime updateTime = LocalDateTime.now();
        student_Comment sc = sPcommentMapper.getCommentByid(id);
        if(sc!=null){
            sPcommentMapper.updateComment(id,comment,updateTime);
            return;
        }
        sPcommentMapper.InsertComment(id,comment,updateTime);
    }

    public student_Comment getComment(Long id) {
        return sPcommentMapper.getComment(id);

    }
}
