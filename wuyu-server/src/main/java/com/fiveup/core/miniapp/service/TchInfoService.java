package com.fiveup.core.miniapp.service;

import com.fiveup.core.miniapp.model.TeacherMini;

import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
public interface TchInfoService {

    TeacherMini getMonitorInfoByTchId(Long teacherId);

    TeacherMini getMonitorInfoByTchName(String teacherName);

    List<TeacherMini> getTeacherInfoByCLass(int gradeNum, int classNum);
}
