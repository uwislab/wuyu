package com.fiveup.core.management.service.impl;

import com.fiveup.core.management.mapper.PanelMapper;
import com.fiveup.core.management.model.Resp.Panel;
import com.fiveup.core.management.model.Resp.PanelStatisticResp;
import com.fiveup.core.management.service.PanelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
@Slf4j
@Service
public class PanelServiceImpl implements PanelService {


    @Resource
    private PanelMapper panelMapper;

    @Override
    public PanelStatisticResp getStatisticData(Integer schoolId) {
        Integer classCount = panelMapper.getClassCount(schoolId);
        Integer stuCount = panelMapper.getStuCount(schoolId);
        Integer gradeCount = panelMapper.getGradeCount(schoolId);
        Integer teacherCount = panelMapper.getTeacherCount(schoolId);

        Panel panel = new Panel(stuCount,teacherCount,classCount,gradeCount);
        PanelStatisticResp panelStatisticResp = new PanelStatisticResp(panel);
        return panelStatisticResp;
    }
}
