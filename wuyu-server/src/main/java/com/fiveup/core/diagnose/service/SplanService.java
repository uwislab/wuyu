package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.bean.Student_plan;
import com.fiveup.core.diagnose.bean.student_pageBean;
import com.fiveup.core.diagnose.mapper.SplanMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SplanService {

    @Autowired
    SplanMapper splanMapper;

    public student_pageBean getallPlan(Integer page, Integer pageSize, String name, Long id){
        PageHelper.startPage(page,pageSize);
        List<Student_plan> list = splanMapper.getallPlan(name,id);
        Page<Student_plan> p = (Page<Student_plan>) list;
        return new student_pageBean(p.getTotal(), p.getResult());
    }
    public List<Student_plan> getPlanByid(String name,Long id){
        return splanMapper.getPlanByid(name,id);
    }
}
