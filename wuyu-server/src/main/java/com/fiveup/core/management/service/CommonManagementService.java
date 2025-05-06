package com.fiveup.core.management.service;

import com.fiveup.core.management.model.topology.TopologyNode;

import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */


public interface CommonManagementService {


    Long getSchoolId();

    List<Long> getAllSchoolIds();
}
