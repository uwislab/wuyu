package com.fiveup.core.fuScore.mapper;

import com.fiveup.core.fuScore.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentFuScoreMapper {

    @Select("select morality_score, intelligence_score, physical_score, aesthetic_score, labour_score, evaluate_date " +
            "from fu_student_score where student_num=#{studentNum}")
    List<StudentFuScore> getStudentsFuScore(int studentNum);

    @Select("SELECT morality_score, intelligence_score, physical_score, aesthetic_score, labour_score,remark,evaluate_date as evaluate_date " +
            "FROM fu_student_score WHERE student_name = #{studentName} ;")
    List<StudentFuScore> getStudentFuScoreByName(String studentName);


    //添加name和num。
//    @Select("SELECT morality_score, intelligence_score, physical_score, aesthetic_score, labour_score,remark,evaluate_date as evaluate_date " +
//            "FROM fu_student_score WHERE student_name = #{studentName} or student_num=#{studentNum};")
//    List<StudentFuScore> getStudentFuScoreByName(String studentName);

    @Select("SELECT * FROM fu_student_score WHERE student_num = #{info} OR student_name LIKE CONCAT('%', #{info}, '%')")
    List<StudentFuScore> getScoreByInfo(String info);


    @Select("SELECT morality_score, intelligence_score, physical_score, aesthetic_score, labour_score,remark,evaluate_date as evaluate_date " +
            "FROM fu_student_score WHERE student_num = #{studentNumber};")
    List<StudentFuScore> getStudentFuScoreById(int studentNum);


    @Select("SELECT f.student_name,b.gender,f.class_ID,f.morality_score, f.intelligence_score, f.physical_score, f.aesthetic_score, f.labour_score \n" +
            "FROM fu_student_score f Left Join basic_student  b On f.student_num = b.student_num WHERE f.class_id = #{classId};")
    List<StudentFuScore> getStudentFuScoreByClassId(int classId);

    @Select({
            "<script>",
            "SELECT ",
            "  s.student_id,",
            "  s.s_deyu AS moralityScore,",
            "  s.s_zhiyu AS intelligenceScore,",
            "  s.s_tiyu AS physicalScore,",
            "  s.s_meiyu AS aestheticScore,",
            "  s.s_laoyu AS labourScore,",
            "  CONCAT(",
            "    DATE_FORMAT(FROM_UNIXTIME(CAST(s.s_exdate AS UNSIGNED) / 1000), '%Y'), '-',",
            "    CASE WHEN MONTH(FROM_UNIXTIME(CAST(s.s_exdate AS UNSIGNED) / 1000)) BETWEEN 3 AND 8 THEN '第一学期' ELSE '第二学期' END",
            "  ) AS semester,",
            "  d.student_name AS studentName",
            "FROM di_studentscore s",
            "JOIN di_student d ON s.student_id = d.student_id",
            "WHERE 1=1",
            "  AND s.student_id = #{studentId}",
            "  <if test='studentName != null and studentName != \"\"'>",
            "    AND d.student_name = #{studentName}",
            "  </if>",
            "  <if test='semester != null and semester != \"\"'>",
            "    AND CONCAT(",
            "          DATE_FORMAT(FROM_UNIXTIME(CAST(s.s_exdate AS UNSIGNED) / 1000), '%Y'), '-',",
            "          CASE WHEN MONTH(FROM_UNIXTIME(CAST(s.s_exdate AS UNSIGNED) / 1000)) BETWEEN 3 AND 8 THEN '第一学期' ELSE '第二学期' END",
            "        ) = #{semester}",
            "  </if>",
            "</script>"
    })
    StudentSemesterScore getStudentSemesterScores(int studentId, String studentName, String semester);

    @Select({
            "SELECT",
            "  CONCAT(",
            "    DATE_FORMAT(FROM_UNIXTIME(CAST(s.s_exdate AS UNSIGNED) / 1000), '%Y'), '-',",
            "    (CASE WHEN MONTH(FROM_UNIXTIME(CAST(s.s_exdate AS UNSIGNED) / 1000)) BETWEEN 3 AND 8 THEN '第一学期' ELSE '第二学期' END)",
            "  ) AS semester,",
            "  (s.s_deyu + s.s_zhiyu + s.s_tiyu + s.s_meiyu + s.s_laoyu) AS totalScore",
            "FROM di_studentscore s",
            "JOIN di_student d ON s.student_id = d.student_id",
            "WHERE s.student_id = #{studentId}",
            "  AND d.student_name = #{studentName}",
            "ORDER BY s.s_exdate"
    })
    List<StuSemesterTotalScore> getStudentSemesterTotalScores(int studentId, String studentName);

    @Select({
            "SELECT student_id, student_name",
            "FROM di_student",
            "WHERE student_name LIKE CONCAT('%', #{keyword}, '%')",
            "   OR student_id LIKE CONCAT('%', #{keyword}, '%')",
            "ORDER BY student_name LIMIT 20"
    })
    List<StudentInfo> searchStudents(@Param("keyword") String keyword);

    @Select({
            "SELECT DISTINCT",
            "  CONCAT(",
            "    DATE_FORMAT(FROM_UNIXTIME(CAST(s_exdate AS UNSIGNED) / 1000), '%Y'), '-',",
            "    CASE WHEN MONTH(FROM_UNIXTIME(CAST(s_exdate AS UNSIGNED) / 1000)) BETWEEN 3 AND 8 THEN '第一学期' ELSE '第二学期' END",
            "  ) AS semester,",
            "  MAX(s_exdate) AS latest_date",
            "FROM di_studentscore",
            "WHERE student_id = #{studentId}",
            "GROUP BY semester",
            "ORDER BY latest_date DESC"
    })
    @Results({
            @Result(property = "semester", column = "semester")
    })
    List<StudentSemesterDto> selectStudentSemesters(Integer studentId);
}
