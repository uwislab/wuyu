package com.fiveup.core.demonstrate.service;


import com.fiveup.core.demonstrate.entity.Gcsj4;
import com.fiveup.core.demonstrate.mapper.Gcsj4Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Gcsj4Service {


    @Autowired
    private Gcsj4Mapper gcsj4Mapper;
    public List<Gcsj4> getGcsj4(){
        return gcsj4Mapper.getGcsj4();
    }



}


