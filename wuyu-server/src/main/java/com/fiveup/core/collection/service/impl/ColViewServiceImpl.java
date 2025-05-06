package com.fiveup.core.collection.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.collection.mapper.ColViewMapper;
import com.fiveup.core.collection.model.ColView;
import com.fiveup.core.collection.service.ColViewService;
import org.springframework.stereotype.Service;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColViewServiceImpl
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/5/29 17:37
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Service
public class ColViewServiceImpl extends ServiceImpl<ColViewMapper, ColView> implements ColViewService {


    @Override
    public ColView getView(Long id){return getById(id);}


    @Override
    public boolean deleteView(Long id){
        return removeById(id);
    }

    @Override
    public int updateView(ColView colView){

        System.out.println(colView.toString());
        return baseMapper.updateById(colView);
    }


}
