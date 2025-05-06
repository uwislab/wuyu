package com.fiveup.core.management.service;

import com.fiveup.core.management.model.Resp.PanelStatisticResp;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
public interface PanelService {
    PanelStatisticResp getStatisticData(Integer schoolId);
}
