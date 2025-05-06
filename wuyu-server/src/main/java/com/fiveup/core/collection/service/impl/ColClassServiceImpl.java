package com.fiveup.core.collection.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.collection.mapper.ColClassMapper;
import com.fiveup.core.collection.model.ColClass;
import com.fiveup.core.collection.service.ColClassService;
import org.springframework.stereotype.Service;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColClassServiceImpl
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 11:14
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Service
public class ColClassServiceImpl extends ServiceImpl<ColClassMapper, ColClass> implements ColClassService {


    @Override
    public ColClass getClass(Long id){
        return getById(id);
    }
}
