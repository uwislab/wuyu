package com.fiveup.core.management.service.impl;

import com.fiveup.core.management.mapper.SchoolMapper;
import com.fiveup.core.management.model.topology.TopologyNode;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.StuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */

@Slf4j
@Service
public class CommonManagementServiceImpl implements CommonManagementService {

    @Autowired
    SchoolMapper schoolMapper;

    @Override
    public Long getSchoolId() {

        // TODO 与负责用户管理的同学协调，完成对用户所属学校的识别

        // 1 从cookie或session或redis中获取用户信息

        // 2 从用户信息中提取出用户所属学校Id

        // 3 返回用户所属学校Id

        return 1L;
    }

    @Override
    public List<Long> getAllSchoolIds() {
        List<Long> ids = schoolMapper.getAllSchoolIds();
        return ids;
    }


}
