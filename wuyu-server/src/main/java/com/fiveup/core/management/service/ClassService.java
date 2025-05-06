package com.fiveup.core.management.service;

import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.Req.ClassAddReq;
import com.fiveup.core.management.model.Req.ClassEditReq;
import com.github.pagehelper.PageInfo;


import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/24
 */
public interface ClassService {
    int deleteBatch(List<Integer> ids);

    List<ClassDTO> getSimpleClassInfo(Long schoolId);

    List<ClassDTO> getSimpleClassInfoByGrade(String grade, Long schoolId);

    PageInfo<ClassDTO> getClassesByPage(String grade, Long monitorId, Long schoolId,Integer pageNum, Integer pageSize);

    void addClass(ClassAddReq classAddReq,Long schoolId);

    void edit(ClassEditReq classEditReq);

    void delete(Long id);
}
