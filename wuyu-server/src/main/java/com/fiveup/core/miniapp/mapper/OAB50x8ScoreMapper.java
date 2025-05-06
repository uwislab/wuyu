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
public interface OAB50x8ScoreMapper {

    @Select("select * from si_50x8oab_score where student_num=#{studentNum}")
    ScoreSport isRecordExist(Long studentNum);

    @Insert("insert into si_50x8oab_score ( student_num, value_minute, value_second, score, level ) " +
            "values (#{studentNum}, #{valueFirst}, #{valueSecond}, #{score}, #{level})")
    int insertSportScore(ScoreSport scoreSport);

    @Update("update si_50x8oab_score set value_minute=#{valueFirst}, value_second=#{valueSecond}, score=#{score}, level=#{level} where student_num=#{studentNum}")
    int updateSportScore(ScoreSport scoreSport);

    @Select("select si_50x8oab_score.student_num, student_name, score from si_50x8oab_score, basic_student where si_50x8oab_score.student_num = basic_student.student_num")
    List<StuScore> getOAB50x8ScoreList();
}
