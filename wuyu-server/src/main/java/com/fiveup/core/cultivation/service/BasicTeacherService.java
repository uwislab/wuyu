package com.fiveup.core.cultivation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.cultivation.entity.BasicTeacher;

import java.util.List;

/**
 * @author Harvi
 */
public interface BasicTeacherService extends IService<BasicTeacher> {
    /**
     * 获取基础教师信息表中所有的教师信息（用于在目标设定时从列表中选定编辑人和审核人）
     * @return TODO
     */
    List<BasicTeacher> getList();
}
