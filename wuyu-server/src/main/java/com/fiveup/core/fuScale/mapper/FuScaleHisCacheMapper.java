package com.fiveup.core.fuScale.mapper;

import com.fiveup.core.fuScale.model.ScaleHisInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuScaleHisCacheMapper {
    List<ScaleHisInfo> list = new ArrayList<>();


    // 新增评价量表表头并返回自增id
    public int insertFuScale(ScaleHisInfo scaleInfo) {
        list.add(scaleInfo);
        return 1;
    }

    public List<ScaleHisInfo> getScaleByState(Integer stateId) {
        return list.stream().filter(s -> s.getState().equals(stateId)).collect(Collectors.toList());
    }

    public List<ScaleHisInfo> getScaleHisListById(Integer id) {
        return list.stream().filter(s -> s.getScaleId().equals(id)).collect(Collectors.toList());
    }

}
