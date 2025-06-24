package com.fiveup.core.performanceevaluation.service;


import com.fiveup.core.cultivation.entity.BasicTeacher;
import com.fiveup.core.performanceevaluation.bean.Teacher;

import java.util.List;

/**
 * 老师的业务逻辑处理
 */
public interface TeacherService {

    /**
     * 查询所有数据
     * @return
     */
    List<Teacher> getAll();


    /**
     * 查询未设置权重的老师
     * @return
     */
    List<Teacher> getNotInWeight(List<Integer> list);

    List<Teacher> getInWeight(List<Integer> list);

    /**
     * 根据教师ID查询教师信息
     * @param teacherID
     * @return
     */
    Teacher getByTId(Integer teacherID);

    Teacher getByName(String name);

    List<Integer> selectOtherTeacher(String name);

    List<BasicTeacher> getTeacherByName(String realName);
}
