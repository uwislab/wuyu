package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.SportItemMapper;
import com.fiveup.core.miniapp.service.SportItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shilin
 * @date 2022/10/3
 */
@Slf4j
@Service
public class SportItemServiceImpl implements SportItemService {

    @Resource
    private SportItemMapper sportItemMapper;

    @Override
    public List<String> getItemList() {return sportItemMapper.getItemList();}

    @Override
    public String getClassLimit(String itemName) { return sportItemMapper.getClassLimit(itemName); }
}
