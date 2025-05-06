package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.mapper.teachactivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class teachactivityService {
    @Autowired
    teachactivityMapper teachactivityMapper;
    public void Insertteachactivity(String activity, int sclass, int grades, String  date) throws ParseException {
        /*转换时间*/
        date = date.replace("Z", " UTC");
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = utcFormat.parse(date);
        teachactivityMapper.Insertteachactivity(activity,sclass,grades,date1);
    };
}
