package com.fiveup.core.diagnose.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface teachactivityMapper {

    @Insert("insert into di_teachactivity(teachactivity,sclass,grades,plantime) values(#{activity},#{sclass},#{grades},#{date})")
    public void Insertteachactivity(String activity, int sclass, int grades, Date date);
}
