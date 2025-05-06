package com.fiveup.core.cultivation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.cultivation.entity.AspectActivity;
import com.fiveup.core.cultivation.mapper.AspectActivityMapper;
import com.fiveup.core.cultivation.service.AspectActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvi
 */
@Service
public class AspectActivityServiceImpl extends ServiceImpl<AspectActivityMapper, AspectActivity> implements AspectActivityService {
    private final AspectActivityMapper aspectActivityMapper;

    @Autowired
    public AspectActivityServiceImpl(AspectActivityMapper aspectActivityMapper) {
        this.aspectActivityMapper = aspectActivityMapper;
    }

    @Override
    public List<AspectActivity> getListByAid(AspectActivity activity) {
        LambdaQueryWrapper<AspectActivity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(AspectActivity::getAid, activity.getAid());
        aspectActivityMapper.selectList(lambdaQueryWrapper);
        return aspectActivityMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void deleteByGid(Integer gid) {
        LambdaQueryWrapper<AspectActivity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(AspectActivity::getGid, gid) ;
        aspectActivityMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void deleteById(Integer id) {
        LambdaQueryWrapper<AspectActivity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(AspectActivity::getId, id) ;
        aspectActivityMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void deleteByAid(Integer aid){
        LambdaQueryWrapper<AspectActivity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AspectActivity::getAid,aid);
        aspectActivityMapper.delete((lambdaQueryWrapper));
    }
}
