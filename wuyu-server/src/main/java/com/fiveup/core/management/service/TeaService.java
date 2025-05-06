package com.fiveup.core.management.service;

import com.fiveup.core.management.model.DTO.TeaDTO;
import com.fiveup.core.management.model.Req.TeaAddReq;
import com.fiveup.core.management.model.Req.TeaEditReq;
import com.fiveup.core.management.model.Resp.TeaPageResp;
import com.fiveup.core.management.model.excel.TeaExt;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/31
 */
public interface TeaService {


    List<TeaDTO> getTeacherSimpleInfo(Long schoolId);

    List<TeaDTO> getTeacherSimpleInfoForEvents(Long schoolId);


    PageInfo<TeaPageResp> getTeacherListByPage(String teacherName,
                                               String title,
                                               String position,
                                               Long schoolId,
                                               Integer pageNum,
                                               Integer pageSize);

    List<TeaExt> getTeacherExtList(String teacherName, String title, String position,Long schoolId);

    void addOne(TeaAddReq teaAddReq);

    void edit(TeaEditReq teaEditReq);

    void softlyDeleteOne(Long teacherId);

    int deleteBatch(List<Integer> ids);
}
