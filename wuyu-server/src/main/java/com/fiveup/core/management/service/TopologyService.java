package com.fiveup.core.management.service;

import com.fiveup.core.management.model.topology.TopologyNode;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
public interface TopologyService {

    TopologyNode getTopology(Long schoolId);
}
