package com.fiveup.core.fuScale.develop_09.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.fuScale.develop_09.common.R;
import com.fiveup.core.fuScale.mapper.FuScaleMapper;
import com.fiveup.core.fuScale.model.ScaleInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/Total")
public class TotalController {
    @Autowired
    @Lazy
    private FuScaleMapper fuScaleMapper;

    @GetMapping("/page")
    public R<Page<ScaleInfo>> page(int page, int pageSize) {
        //构造分页构造器
        Page<ScaleInfo> pageInfo = new Page<>(page,pageSize);
        PageHelper.clearPage();

        List<ScaleInfo> list = fuScaleMapper.getAllFuScale();
        list.forEach(s-> {
            s.setCreatorName(fuScaleMapper.getCreatorName(s.getCreatorId()));
            s.setHasChildren(fuScaleMapper.hasChildrenForScale(s.getScaleId())>0);
        });
        pageInfo.setRecords(list);
        pageInfo.setTotal(list.size());

        return R.success(pageInfo);
    }

}





