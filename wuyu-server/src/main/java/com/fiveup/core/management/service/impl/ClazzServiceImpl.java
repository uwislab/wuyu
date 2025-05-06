package com.fiveup.core.management.service.impl;

import com.fiveup.core.management.common.exception.BizException;
import com.fiveup.core.management.mapper.ClazzMapper;
import com.fiveup.core.management.mapper.TeaMapper;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.DTO.TeaDTO;
import com.fiveup.core.management.model.Req.ClassAddReq;
import com.fiveup.core.management.model.Req.ClassEditReq;
import com.fiveup.core.management.service.ClassService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/24
 */
@Slf4j
@Service
public class ClazzServiceImpl implements ClassService {

    @Resource
    private ClazzMapper clazzMapper;

    @Resource
    private TeaMapper teaMapper;

    @Override
    public List<ClassDTO> getSimpleClassInfo(Long schoolId) {
        List<ClassDTO> classDTOList = new ArrayList<>();
        classDTOList = clazzMapper.getAllSimpleClassList(schoolId);
        return classDTOList;
    }

    @Override
    public List<ClassDTO> getSimpleClassInfoByGrade(String grade, Long schoolId) {
        List<ClassDTO> classDTOList = new ArrayList<>();
        if (grade == null || "".equals(grade))
            classDTOList = clazzMapper.getAllSimpleClassList(schoolId);
        else
            classDTOList = clazzMapper.getAllSimpleClassListByGrade(grade,schoolId);
        return classDTOList;
    }

    @Override
    public PageInfo<ClassDTO> getClassesByPage(String grade, Long monitorId, Long schoolId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ClassDTO> classDTOList = clazzMapper.getClassListByConditions(grade, monitorId, schoolId);
        populateMonitorObjectForClassDTOList(classDTOList);
        PageInfo<ClassDTO> classDTOPageInfo = new PageInfo<>(classDTOList);
        return classDTOPageInfo;
    }


    public void populateMonitorObjectForClassDTOList(List<ClassDTO> classDTOList) {
        if (classDTOList == null || classDTOList.size() == 0) {
            return;
        }
        for (int i = 0; i < classDTOList.size(); i++) {
            ClassDTO classDTO = classDTOList.get(i);
            Long monitorId = classDTO.getMonitorId();
            TeaDTO teaDTO = teaMapper.getTeacherById(monitorId);
            classDTO.setMonitor(teaDTO);
        }
    }

    @Override
    public void addClass(ClassAddReq classAddReq, Long schoolId) throws BizException{
        Long monitorId = classAddReq.getMonitorId();
        if(monitorId==null) {
            return;
        }
        TeaDTO teaDTO = teaMapper.getTeacherById(monitorId);
        if(teaDTO==null) {
            throw new BizException("班主任不存在！请联系工作人员，monitorId=" + monitorId);
        }
        classAddReq.setSchoolId(schoolId);
        clazzMapper.addClass(classAddReq);
    }

    @Override
    public void edit(ClassEditReq classEditReq) {
        clazzMapper.DynamicallyUpdateOne(classEditReq);
    }

    @Override
    public void delete(Long id) {
        clazzMapper.softlyDelete(id);

        // TODO 链路删除绑定的学生信息
    }

    @Override
    public int deleteBatch(List<Integer> ids){

        return clazzMapper.deleteBatch(ids);
    }
}
