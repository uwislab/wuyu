package com.fiveup.core.fuScore.mapper;

import com.fiveup.core.fuScore.model.ClassFuItemScore;
import com.fiveup.core.fuScore.model.ClassScore;
import com.fiveup.core.fuScore.model.GradeFuItemScore;
import com.fiveup.core.fuScore.model.GradeFuScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradeFuScoreMapper {

    @Select("select grade_ID, " +
            "grade_name, " +
            "morality_score, " +
            "intelligence_score, " +
            "physical_score, " +
            "aesthetic_score, " +
            "labour_score from fu_grade_score")
    List<GradeFuScore> getAll();

    @Select("SELECT grade_id, FLOOR(AVG(morality_score)) as morality_score, " +
            "FLOOR(AVG(intelligence_score)) as intelligence_score, " +
            "FLOOR(AVG(physical_score)) as physical_score, " +
            "FLOOR(AVG(aesthetic_score)) as aesthetic_score, " +
            "FLOOR(AVG(labour_score)) as labour_score " +
            "FROM fu_student_score, basic_student " +
            "WHERE evaluate_date=#{evaluateDate} AND fu_student_score.student_num=basic_student.student_num " +
            "GROUP BY grade_id")
    List<GradeFuScore> getGradeFuScoreByDate(int evaluateDate);

    @Select("SELECT FLOOR(AVG(morality_score)) as morality_score, " +
            "FLOOR(AVG(intelligence_score)) as intelligence_score, " +
            "FLOOR(AVG(physical_score)) as physical_score, " +
            "FLOOR(AVG(aesthetic_score)) as aesthetic_score, " +
            "FLOOR(AVG(labour_score)) as labour_score, " +
            "evaluate_date " +
            "from fu_student_score join basic_student on fu_student_score.student_num=basic_student.student_num where grade_id=#{gradeId} GROUP BY evaluate_date")
    List<GradeFuScore> getGradeScore(int gradeId);

    @Select("select grade_ID, " +
            "grade_name, " +
            "morality_score as score from fu_grade_score")
    List<GradeFuItemScore> getGradeMoScoreList();

    @Select("SELECT grade_id, FLOOR(AVG(morality_score)) as score " +
            "FROM fu_student_score, basic_student " +
            "WHERE evaluate_date=#{date} AND fu_student_score.student_num=basic_student.student_id " +
            "GROUP BY grade_id")
    List<GradeFuItemScore> getGradeMoScoreByDate(int date);

    @Select("select class_ID, " +
            "class_name, " +
            "morality_score as score from fu_class_score where grade_ID = #{gradeID}")
    List<ClassFuItemScore> getGradeMoScoreByGradeID(int gradeID);

    @Select("select grade_ID, " +
            "grade_name, " +
            "intelligence_score as score from fu_grade_score")
    List<GradeFuItemScore> getGradeInScoreList();

    @Select("SELECT grade_id, FLOOR(AVG(intelligence_score)) as score " +
            "FROM fu_student_score, basic_student " +
            "WHERE evaluate_date=#{date} AND fu_student_score.student_num=basic_student.student_id " +
            "GROUP BY grade_id")
    List<GradeFuItemScore> getGradeInScoreByDate(int date);

    @Select("select class_ID, " +
            "class_name, " +
            "intelligence_score as score from fu_class_score where grade_ID = #{gradeID}")
    List<ClassFuItemScore> getGradeInScoreByGradeID(int gradeID);

    @Select("select grade_ID, " +
            "grade_name, " +
            "physical_score as score from fu_grade_score")
    List<GradeFuItemScore> getGradePhScoreList();

    @Select("SELECT grade_id, FLOOR(AVG(physical_score)) as score " +
            "FROM fu_student_score, basic_student " +
            "WHERE evaluate_date=#{date} AND fu_student_score.student_num=basic_student.student_id " +
            "GROUP BY grade_id")
    List<GradeFuItemScore> getGradePhScoreByDate(int date);

    @Select("select class_ID, " +
            "class_name, " +
            "physical_score as score from fu_class_score where grade_ID = #{gradeID}")
    List<ClassFuItemScore> getGradePhScoreByGradeID(int gradeID);

    @Select("select grade_ID, " +
            "grade_name, " +
            "aesthetic_score as score from fu_grade_score")
    List<GradeFuItemScore> getGradeAeScoreList();

    @Select("SELECT grade_id, FLOOR(AVG(aesthetic_score)) as score " +
            "FROM fu_student_score, basic_student " +
            "WHERE evaluate_date=#{date} AND fu_student_score.student_num=basic_student.student_id " +
            "GROUP BY grade_id")
    List<GradeFuItemScore> getGradeAeScoreByDate(int date);

    @Select("select class_ID, " +
            "class_name, " +
            "aesthetic_score as score from fu_class_score where grade_ID = #{gradeID}")
    List<ClassFuItemScore> getGradeAeScoreByGradeID(int gradeID);

    @Select("select grade_ID, " +
            "grade_name, " +
            "labour_score as score from fu_grade_score")
    List<GradeFuItemScore> getGradeLaScoreList();

    @Select("SELECT grade_id, FLOOR(AVG(labour_score)) as score " +
            "FROM fu_student_score, basic_student " +
            "WHERE evaluate_date=#{date} AND fu_student_score.student_num=basic_student.student_id " +
            "GROUP BY grade_id")
    List<GradeFuItemScore> getGradeLaScoreByDate(int date);

    @Select("select class_ID, " +
            "class_name, " +
            "labour_score as score from fu_class_score where grade_ID = #{gradeID}")
    List<ClassFuItemScore> getGradeLaScoreByGradeID(int gradeID);


    @Select("SELECT DISTINCT evaluate_date FROM fu_student_score")
    List<Integer> getDateList();
}
