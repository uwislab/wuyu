package com.fiveup.core.collection.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.collection.model.ColTeacher;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColTeacherService
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 10:48
 * @Version: V1.0
 * @Description: 对该类的描述
 */
public interface ColTeacherService extends IService<ColTeacher> {

    /**
     * 对方法的描述
     * 获取教师信息
     * @return com.elk.fiveup.core.collection.model.ColStudent
     * @throws
     */
    ColTeacher getTeacher(Long id);

}
