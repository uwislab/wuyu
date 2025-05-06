package com.fiveup.core.miniapp.service;

import com.fiveup.core.miniapp.model.ClassMini;

/**
 * @author shilin
 * @date 2022/9/19
 */
public interface ClassService {

    ClassMini getClassInfoByClassId(Long classId);

    Long getIdByGradeAndClass(String grade, String className);

}
