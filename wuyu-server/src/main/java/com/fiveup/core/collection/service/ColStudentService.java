package com.fiveup.core.collection.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.collection.model.ColStudent;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColStudentService
 * @Author: zhouz
 * @E-mail: 邮箱
 * @Date: 2021/4/20 19:16
 * @Version: V1.0
 * @Description: 对该类的描述
 */
public interface ColStudentService extends IService<ColStudent> {


    /**
     * 添加学生信息
     * @return int
     * @throws
     */
    int addStudent(ColStudent colStudent);
    /**
     * 获取学生信息
     * @return com.elk.fiveup.core.collection.model.ColStudent
     * @throws
     */
    ColStudent getStudent(Long id);

    /**
     * 删除学生信息
     * @return
     * @throws
     */
    boolean deleteStudent(Long id);

    /**
     * 批量删除学生
     * @param ids
     * @return
     */
    boolean deleteStudent(List<Long> ids);

     /**
     * 对方法的描述
     * 修改学生信息
     * @return int
     * @throws
     */
    int updateStudent( ColStudent colStudent);

    /**
     * 分页查询学生
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<ColStudent> listStudent(String keyword, int pageNum, int pageSize);
}
