package com.fiveup.core.miniapp.mapper;

import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
@Mapper
@Repository
public interface BMIScoreMapper {

    @Select("select * from si_bmi_score where student_num=#{studentNum}")
    ScoreSport isRecordExist(Long studentNum);

    @Insert("insert into si_bmi_score ( student_num, value_stature, value_weight, score, level ) " +
            "values (#{studentNum}, #{valueFirst}, #{valueSecond}, #{score}, #{level})")
    int insertSportScore(ScoreSport scoreSport);

    @Update("update si_bmi_score set value_stature=#{valueFirst}, value_weight=#{valueSecond},score=#{score}, level=#{level} where student_num=#{studentNum}")
    int updateSportScore(ScoreSport scoreSport);

    @Select("select si_bmi_score.student_num, student_name, score from si_bmi_score, basic_student where si_bmi_score.student_num = basic_student.student_num")
    List<StuScore> getBMIScoreList();
}
