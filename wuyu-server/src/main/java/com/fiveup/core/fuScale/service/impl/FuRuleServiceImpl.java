package com.fiveup.core.fuScale.service.impl;


import com.fiveup.core.fuScale.mapper.FuRuleMapper;
import com.fiveup.core.fuScale.mapper.FuScaleMapper;
import com.fiveup.core.fuScale.model.Detail;
import com.fiveup.core.fuScale.model.Domain;
import com.fiveup.core.fuScale.model.ScaleContent;
import com.fiveup.core.fuScale.model.ScaleInfo;
import com.fiveup.core.fuScale.service.FuRuleService;
import com.fiveup.core.fuScale.service.FuScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ParentServiceImpl
 * @Author: shilin
 * @Date: 2022/9/18 16:32
 */
@Service
public class FuRuleServiceImpl implements FuRuleService {

    @Autowired
    private FuRuleMapper fuRuleMapper;

    @Override
    public int insertDetail(Detail detail) {
        System.out.println(detail);
        return fuRuleMapper.insertDetail(detail);
    }
}
