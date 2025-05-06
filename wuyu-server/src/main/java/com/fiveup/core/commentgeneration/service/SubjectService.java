package com.fiveup.core.commentgeneration.service;

import com.fiveup.core.commentgeneration.bean.Subject;

import java.util.List;

/**
 * 科目业务逻辑处理
 */
public interface SubjectService {
    /**
     * 获取所有已封装数据
     * @return
     */
    List<Subject> getAll();

    /**
     * 根据ID查询数据
     * @param id
     * @return
     */
    Subject getById(Integer id);
}
