package com.fiveup.core.collection.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.collection.mapper.ColTeacherMapper;
import com.fiveup.core.collection.model.ColTeacher;
import com.fiveup.core.collection.service.ColTeacherService;
import org.springframework.stereotype.Service;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColTeacherServiceImpl
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 10:48
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Service
public class ColTeacherServiceImpl extends ServiceImpl<ColTeacherMapper, ColTeacher> implements ColTeacherService {


    @Override
    public ColTeacher getTeacher(Long id){
        return  getById(id);
    }
}
