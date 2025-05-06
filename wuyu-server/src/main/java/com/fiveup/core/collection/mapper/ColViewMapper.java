package com.fiveup.core.collection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.collection.model.ColView;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColViewMapper
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/5/29 17:35
 * @Version: V1.0
 * @Description: 对该类的描述
 */
public interface ColViewMapper extends BaseMapper<ColView> {

    List<ColView> selectAllView(ColView colView);

    int insertView(ColView colView);
}
