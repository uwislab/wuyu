package com.fiveup.core.diagnose.mapper;


import com.fiveup.core.diagnose.bean.student_kpointsScore;
import com.fiveup.core.diagnose.bean.student_sportsScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SportsScoreMapper {

    /*根据name,id查找成绩*/
    @Select("select s.student_id,s.student_name,dt.sports_morality,dt.health_knowledge,dt.mental_health,dt.sports_skills,dt.sports_activity FROM di_tiyuScore dt JOIN di_student s on dt.student_id=s.student_id where (s.student_id = #{id} or s.student_name = #{name})")
    public student_sportsScore getSportsScoreByKey(String name, int id);
}
