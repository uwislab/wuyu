package com.fiveup.core.fuScale.mapper;

import com.fiveup.core.fuScale.model.ScaleHisInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuScaleHisCacheMapper {
    List<ScaleHisInfo> list = new ArrayList<>();


//    // 新增评价量表表头并返回自增id
//    public int insertFuScale(ScaleHisInfo scaleInfo) {
//        list.add(scaleInfo);
//        return 1;
//    }

    // 新增评价量表表头并返回自增id
    public int insertFuScale(ScaleHisInfo scaleInfo) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 格式化日期
        String formattedDate = currentDate.format(formatter);
        // 输出结果
        System.out.println(formattedDate);
        scaleInfo.setUpdateDate(formattedDate);

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
