package com.fiveup.core.fuScale.develop_09.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.fuScale.develop_09.Mapper.FindMapper;
import com.fiveup.core.fuScale.develop_09.entity.Fu_score;
import com.fiveup.core.fuScale.develop_09.service.FindService;
import org.springframework.stereotype.Service;

@Service
public class FindServiceImpl extends ServiceImpl<FindMapper, Fu_score> implements FindService {
}
