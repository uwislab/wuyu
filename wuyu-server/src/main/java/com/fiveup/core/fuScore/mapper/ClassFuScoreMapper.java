package com.fiveup.core.fuScore.mapper;

import com.fiveup.core.fuScore.model.ClassFuScore;
import com.fiveup.core.fuScore.model.ClassScore;
import com.fiveup.core.fuScore.model.FuScore;
import com.fiveup.core.fuScore.model.FuClassScore;
import com.fiveup.core.fuScore.model.StudentFuScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassFuScoreMapper {

    @Select("select class_id, " +
            "FLOOR(AVG(morality_score)) as morality_score, " +
            "FLOOR(AVG(intelligence_score)) as intelligence_score, " +
            "FLOOR(AVG(physical_score)) as physical_score, " +
            "FLOOR(AVG(aesthetic_score)) as aesthetic_score, " +
            "FLOOR(AVG(labour_score)) as labour_score, " +
            "evaluate_date " +
            "from fu_student_score join basic_student on student_num=student_id where grade_id=#{gradeId} and class_id=#{classId} GROUP BY evaluate_date")
    List<ClassScore> getClassScore(int classId, int gradeId);

    @Select("SELECT class_ID,class_name,morality_score ,intelligence_score,physical_score , aesthetic_score,labour_score,data as evaluate_date FROM fu_class_score WHERE grade_ID = #{gradeId} AND class_name = #{className};")
    List<ClassScore> getClassScoreByGradeAndClass(@Param("gradeId") int gradeId, @Param("className") String className, @Param("itemId") int itemId);


    @Select("SELECT class_ID,class_name,morality_score ,intelligence_score,physical_score , aesthetic_score,labour_score,data as evaluate_date FROM fu_class_score WHERE grade_ID = #{gradeId} AND class_ID = #{classId};")
    List<ClassScore> getClassScoreByClassIdAndGradeId(int classId, int gradeId);


    @Select("select basic_student.student_name, basic_student.student_num, grade_id, basic_student.class_id, " +
            "morality_score, intelligence_score, physical_score, aesthetic_score, labour_score, evaluate_date " +
            "from fu_student_score join basic_student on fu_student_score.student_num=basic_student.student_num " +
            "where grade_id=#{gradeId} and basic_student.class_id=#{classId} and evaluate_date=#{date}")
    List<StudentFuScore> getStudentsFuScoreList(int gradeId, int classId, int date);

    @Select("SELECT morality_score, intelligence_score, physical_score, aesthetic_score, labour_score " +
            "FROM fu_student_score where student_num=#{studentNum} and evaluate_date=#{date}")
    StudentFuScore getLastScore(String studentNum, int date);

    @Select("select student_name, student_num, morality_score as score from fu_student_score where class_ID=#{classId}")
    List<ClassFuScore> getStudentsMoScoreList(Long classId);

    @Select("select student_name, student_num, intelligence_score as score from fu_student_score where class_ID=#{classId}")
    List<ClassFuScore> getStudentsInScoreList(Long classId);

    @Select("select student_name, student_num, physical_score as score from fu_student_score where class_ID=#{classId}")
    List<ClassFuScore> getStudentsPhScoreList(Long classId);

    @Select("select student_name, student_num, aesthetic_score as score from fu_student_score where class_ID=#{classId}")
    List<ClassFuScore> getStudentsAeScoreList(Long classId);

    @Select("select student_name, student_num, labour_score as score from fu_student_score where class_ID=#{classId}")
    List<ClassFuScore> getStudentsLaScoreList(Long classId);

    @Select("SELECT DISTINCT data FROM fu_grade_score")
    List<Integer> getClassDataList();

    @Insert("insert into fu_score"+
            "(upid, title, type, starttime, finishtime, zhibiao, zhibiao2, zhibiao3, score, beizhu, status, teacher_name)"+
            " value(#{upid}, #{title}, #{type}, #{startTime}, #{finishTime},"+
            " #{zhibiao}, #{zhibiao2}, #{zhibiao3}, #{score}, #{beizhu}, #{status}, #{teacher_name})")
    int insertFuScore(FuScore fuScore);
    //工程实践 古
    @Select("SELECT class_ID,class_name,morality_score ,intelligence_score,physical_score , aesthetic_score,labour_score,data as evaluate_date \n" +
            "FROM fu_class_score\n" +
            "WHERE \n" +
            "    CASE\n" +
            "        WHEN MONTH(CURRENT_DATE()) = 12 THEN data = YEAR(CURRENT_DATE()) * 100 + 7\n" +
            "        WHEN MONTH(CURRENT_DATE()) < 7 THEN data = (YEAR(CURRENT_DATE()) - 1) * 100 + 12\n" +
            "        ELSE data = (YEAR(CURRENT_DATE()) - 1) * 100 + 12\n" +
            "    END\n" +
            "AND grade_ID = #{gradeId} AND class_ID = #{classId}")
    List<ClassScore> getWorkList(int classId, int gradeId);

    @Insert("insert into fu_class_score values(#{id},#{grade_ID},#{class_ID},#{class_name},#{morality_score},#{intelligence_score},#{physical_score},#{aesthetic_score},#{labour_score},#{data},#{isenter},#{isreview})")
    int addFuClassScore(FuClassScore fuClassScore);
/*一样的。。。要加feat: */
}
