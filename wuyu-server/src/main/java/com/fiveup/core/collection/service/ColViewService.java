package com.fiveup.core.collection.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.collection.model.ColView;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColViewService
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/5/29 17:36
 * @Version: V1.0
 * @Description: 对该类的描述
 */

public interface ColViewService extends IService<ColView> {

    ColView getView(Long id);

    boolean deleteView(Long id);

    int updateView(ColView colView);


}
