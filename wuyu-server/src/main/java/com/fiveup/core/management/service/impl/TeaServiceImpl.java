package com.fiveup.core.management.service.impl;

import com.fiveup.core.management.mapper.TeaMapper;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.model.DTO.TeaDTO;
import com.fiveup.core.management.model.Req.TeaAddReq;
import com.fiveup.core.management.model.Req.TeaEditReq;
import com.fiveup.core.management.model.Resp.TeaPageResp;
import com.fiveup.core.management.model.excel.StuDownloadExt;
import com.fiveup.core.management.model.excel.TeaExt;
import com.fiveup.core.management.service.TeaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/31
 */
@Slf4j
@Service
public class TeaServiceImpl implements TeaService {


    @Resource
    private TeaMapper teaMapper;

    @Override
    public List<TeaDTO> getTeacherSimpleInfo(Long schoolId) {

        List<TeaDTO> teaDTOList = teaMapper.getTeacherSimpleInfo(schoolId);
        for (TeaDTO teaDTO : teaDTOList) {
            teaDTO.setMonitorName(teaDTO.getTeacherName());
            teaDTO.setTeacherName(null);
        }
        return teaDTOList;
    }

    @Override
    public List<TeaDTO> getTeacherSimpleInfoForEvents(Long schoolId) {

        List<TeaDTO> teaDTOList = teaMapper.getTeacherSimpleInfo(schoolId);
        for (TeaDTO teaDTO : teaDTOList) {
            teaDTO.setTeacherId(teaDTO.getId());
            teaDTO.setId(null);
        }
        return teaDTOList;
    }

    @Override
    public PageInfo<TeaPageResp> getTeacherListByPage(String teacherName, String title, String position, Long schoolId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TeaPageResp> teaPageRespList = teaMapper.getTeaByCondition(teacherName, title, position, schoolId);
        PageInfo<TeaPageResp> pageInfo = new PageInfo<>(teaPageRespList);
        return pageInfo;
    }

    @Override
    public List<TeaExt> getTeacherExtList(String teacherName, String title, String position, Long schoolId) {
        List<TeaPageResp> teaPageRespList = teaMapper.getTeaByCondition(teacherName, title, position, schoolId);
        List<TeaExt> teaExtList = new ArrayList<>();
        for (TeaPageResp teaPageResp : teaPageRespList) {
            TeaExt teaExt = new TeaExt();

            BeanUtils.copyProperties(teaPageResp, teaExt);
            if (teaExt.getGender() != null) {
                teaExt.setGenderChineseCharacter(teaExt.getGender() == 0 ? "女" : "男");
            }
            teaExtList.add(teaExt);
        }
        return teaExtList;
    }

    @Override
    public void addOne(TeaAddReq teaAddReq) {

        teaMapper.addOne(teaAddReq);
    }

    @Override
    public void edit(TeaEditReq teaEditReq) {
        teaMapper.edit(teaEditReq);
    }

    @Override
    public void softlyDeleteOne(Long teacherId) {
        teaMapper.softlyDeleteOne(teacherId);
    }

    @Override
    public int deleteBatch(List<Integer> ids) {
        return teaMapper.deleteBatch(ids);
    }


}
