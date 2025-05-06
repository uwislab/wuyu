package com.fiveup.core.diagnose.mapper;


import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface FiveupManagementMapper {

    @Insert("insert into di_studentscore(student_id,s_deyu,s_zhiyu,s_tiyu,s_meiyu,s_laoyu,s_exdate) values(#{id},#{deyu},#{zhiyu},#{tiyu},#{meiyu},#{laoyu},#{sdate})")
    public void insertstudentscore(int id, int deyu, int zhiyu, int tiyu, int meiyu, int laoyu, Long sdate);

    @Update("update di_studentscore set s_deyu=#{deyu},s_zhiyu=#{zhiyu},s_tiyu=#{tiyu},s_meiyu=#{meiyu},s_laoyu=#{laoyu} where student_id=#{id}")
    public void updatestudentscore(int id, int deyu, int zhiyu, int tiyu, int meiyu, int laoyu);
    @Delete("delete  from di_studentscore where student_id=#{id}")
     void deletestudentscore(int id);

    @Select("select count(*) from di_student where student_id=#{id};")
    int getCount(int id);
}
