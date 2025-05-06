package com.fiveup.core.collection.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.collection.model.ColClass;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColClassService
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 11:14
 * @Version: V1.0
 * @Description: 对该类的描述
 */
public interface ColClassService  extends IService<ColClass> {

    /**
     * 对方法的描述
     * 获取班级信息
     * @return com.elk.fiveup.core.collection.model.ColStudent
     * @throws
     */
    ColClass getClass(Long id);
}
