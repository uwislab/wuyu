package com.fiveup.core.collection.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.collection.mapper.ColStudentMapper;
import com.fiveup.core.collection.model.ColStudent;
import com.fiveup.core.collection.service.ColStudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColStudentServiceImpl
 * @Author: zhouz
 * @E-mail: 邮箱
 * @Date: 2021/4/20 19:16
 * @Version: V1.0
 * @Description: 学生信息服务
 */
@Service
public class ColStudentServiceImpl extends ServiceImpl<ColStudentMapper, ColStudent> implements ColStudentService {

    @Override
    public ColStudent getStudent(Long id) {
        return getById(id);
    }

    @Override
    public boolean deleteStudent(Long id){
        return removeById(id);
    }

    @Override
    public boolean deleteStudent(List<Long> ids) {
        return removeByIds(ids);
    }

    @Override
    public int updateStudent(ColStudent colStudent){
        //ColStudent colStudent1 = new ColStudent();
        //BeanUtils.copyProperties(colStudent,colStudent1);
        //colStudent1.setId(colStudent.getId());
        //
        //baseMapper.u
        System.out.println(colStudent.toString());
        return baseMapper.updateById(colStudent);
    }

    @Override
    public int addStudent(ColStudent colStudent){
        ColStudent colStudent1 = new ColStudent();
        BeanUtils.copyProperties(colStudent,colStudent1);
        return  baseMapper.insert(colStudent1);
    }

    @Override
    public Page<ColStudent> listStudent(String keyword, int pageNum, int pageSize) {
        Page<ColStudent> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ColStudent> wrapper = new QueryWrapper<>();
        if (keyword != null) wrapper.lambda().like(ColStudent::getName, keyword);
        return page(page, wrapper);
    }
}
