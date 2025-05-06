package com.fiveup.core.management.model.topology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopologyNode {
    private String name;
    private List<TopologyNode> children;
}
