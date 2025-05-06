package com.fiveup.core.performanceevaluation.mapper;

import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.commentgeneration.bean.Subject;
import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.bean.Teacher;
import com.fiveup.core.performanceevaluation.vo.Average;
import com.fiveup.core.performanceevaluation.vo.StudentPerformanceVO;
import org.apache.ibatis.annotations.*;
import org.python.antlr.op.In;

import java.util.List;
@Mapper
/**
 * 对学生表现的数据操作
 */
public interface StudentPerformanceMapper {

    /**
     * 根据教师id获取学生列表
     */
    @Select("select count(id) from re_student where tid = #{tid}")
    Integer getCountByTeacher(@Param("tid")Integer tid);

    /**
     * 插入数据
     * @param studentPerformance
     */
    void insert(StudentPerformance studentPerformance);

    /**
     * 根据ID删除数据
     * @param id
     */
    @Delete("delete from fu_student_score where id = #{id}")
    void deleteById(Integer id);

    /**
     * 根据ID更新数据
     * @param studentPerformance
     */
    @Update("update fu_student_score set remark = #{remark} where id=#{id}")
    void updateById(StudentPerformance studentPerformance);

    /**
     * 查询所有数据
     * @return
     */
    @Select("select id,student_name AS name,morality_score AS virtue," +
            "intelligence_score AS intelligence,physical_score AS sports," +
            "aesthetic_score AS art,labour_score AS labor,remark,tid," +
            "student_num AS sid,sum(morality_score+intelligence_score+physical_score+aesthetic_score+labour_score)/5" +
            " AS total_score \n" +
            "from fu_student_score GROUP BY id ")
    List<StudentPerformanceVO> selectAll();

    /**
     * 查询所有没有生成评语的学生数据以及对应的老师、学期，用于进行自动生成评语
     * @return
     */
    @Select("select id,student_name AS name,morality_score AS virtue," +
            "intelligence_score AS intelligence,physical_score AS sports," +
            "aesthetic_score AS art,labour_score AS labor,remark,tid," +
            "student_num AS sid,sum(morality_score+intelligence_score+physical_score+aesthetic_score+labour_score)/5" +
            " AS total_score \n" +
            "from fu_student_score where (remark is null OR remark = '') and tid = #{id} and evaluate_date>=#{yearMonth} AND evaluate_date<=#{yearMonth}+5 GROUP BY id")
    List<StudentPerformanceVO> AutoRemark(Integer id, Integer yearMonth);



    /**
     * 根据教师ID查询数据
     * @param id
     * @return
     */
    @Select("select id,student_name AS name,morality_score AS virtue," +
            "intelligence_score AS intelligence,physical_score AS sports," +
            "aesthetic_score AS art,labour_score AS labor,remark,tid," +
            "student_num AS sid,sum(morality_score+intelligence_score+physical_score+aesthetic_score+labour_score)/5" +
            " AS total_score " +
            "from fu_student_score GROUP BY id " +
            "having sid IN (select student_num AS sid from basic_student " +
            "where (class_id,grade_id) IN " +
            "(SELECT id AS class_id,grade AS grade_id from basic_class " +
            "where (grade,class) in (SELECT grade,class_num AS class " +
            "FROM basic_lesson WHERE teacher_id = #{id})))")
    List<StudentPerformance> selectByTId(Integer id);

    /**
     * 根据评语表的教师ID查询数据
     * @param id
     * @return
     */
    @Select("select id,student_name AS name,morality_score AS virtue," +
            "intelligence_score AS intelligence,physical_score AS sports," +
            "aesthetic_score AS art,labour_score AS labor,remark,tid," +
            "student_num AS sid,sum(morality_score+intelligence_score+physical_score+aesthetic_score+labour_score)/5" +
            " AS total_score " +
            "from fu_student_score GROUP BY id " +
            "having tid = #{id}")
    List<StudentPerformance> selectByOwnTId(Integer id);

    /**
     * 查询相关记录总数
     * @return
     */
    @Select("select count(id) from fu_student_score")
    Integer selectCountBySql(String sql);

    /**
     * 分页查询
     * @param start
     * @param pageSize
     * @return
     */
    @Select("select id,student_name AS name,morality_score AS virtue," +
            "intelligence_score AS intelligence,physical_score AS sports," +
            "aesthetic_score AS art,labour_score AS labor,remark,tid," +
            "student_num AS sid,sum(morality_score+intelligence_score+physical_score+aesthetic_score+labour_score)/5" +
            " AS total_score " +
            "from fu_student_score LIMIT #{start}, #{pageSize}")
    List<StudentPerformanceVO> selectPagination(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    @Select("select * from re_student where tid = #{tid} LIMIT #{page}, #{pageSize}")
    List<StudentPerformanceVO> selectPaginationByTeacherId(@Param("tid")Integer tid,@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    /**
     * 根据sid查询对应的评价表数据
     * @param sid
     * @return
     */
    @Select("select * from re_student where sid = #{sid}")
    StudentPerformance[] selectBySid(Integer sid);

    /**
     * 根据学号sid查询老师表的数据
     * @param
     * @return
     */
    @Select("select id AS teacherId,username,password from basic_teacher " +
            "where basic_teacher.id = (SELECT monitor_id AS id FROM basic_class " +
            "WHERE basic_class.id = (SELECT class_id from basic_student " +
            "where student_num = #{sid}))")
    Teacher selectTeacherById(Integer sid);

    @Select("select id AS teacherId,username,password from basic_teacher " +
            "where id = #{tid}")
    Teacher selectTeacherByTid(Integer tid);
    /**
     * 根据ID查询对应的数据
     * @param
     * @return
     */
    @Select("select id AS teacherId,username,password from basic_teacher " +
            "where basic_teacher.id = (SELECT monitor_id AS id FROM basic_class " +
            "WHERE basic_class.id = (SELECT class_id from basic_student " +
            "where id = #{id}))")

    StudentPerformance selectById(Integer id);

    /**
     * 根据教师ID查询对应的平均分
     * @param tid
     * @return
     */
    @Select("SELECT tid,avg(morality_score) as virtue, avg(intelligence_score) as intelligence" +
            ",avg(physical_score) as sports, avg(aesthetic_score) as art, avg(labour_score) as labor" +
            " FROM fu_student_score" +
            " WHERE tid = #{tid}")
    Average selectAverageByTid(Integer tid);

    /**
     * 查询教师ID对应的除查询的学生之外的所有学生的总数
     * @param tid
     * @param sid
     * @return
     */
    @Select("SELECT (morality_score + intelligence_score + physical_score + aesthetic_score + labour_score)" +
            " FROM fu_student_score" +
            " WHERE tid = #{tid} and sid != #{sid}")
    List<Integer> selectSubjectScoreSum(Integer tid, Integer sid);

    @Update("update fu_student_score set tid = #{tid} where id = #{id}")
    void updateTid(Integer id,Integer tid);


    @Select("SELECT *" +
            " FROM corpus" +
            " WHERE subject_id = #{id}")
    List<Corpus> selectCorById(Integer id);

    @Select("SELECT evaluate_date" +
            " FROM fu_student_score GROUP BY evaluate_date")
    List<Integer> selectItem();



    @Select("select id,student_name AS name,morality_score AS virtue," +
            "intelligence_score AS intelligence,physical_score AS sports," +
            "aesthetic_score AS art,labour_score AS labor,remark,tid," +
            "student_num AS sid,sum(morality_score+intelligence_score+physical_score+aesthetic_score+labour_score)/5" +
            " AS total_score \n" +
            "from fu_student_score WHERE tid=#{tid} AND evaluate_date>=#{yearMonth} AND evaluate_date<=#{yearMonth}+5 GROUP BY id ")
    List<StudentPerformanceVO> selectAllByItem(Integer tid, Integer yearMonth);


    @Select("select id,student_name AS name,morality_score AS virtue," +
            "intelligence_score AS intelligence,physical_score AS sports," +
            "aesthetic_score AS art,labour_score AS labor,remark,tid," +
            "student_num AS sid,sum(morality_score+intelligence_score+physical_score+aesthetic_score+labour_score)/5" +
            " AS total_score \n" +
            "from fu_student_score WHERE evaluate_date>=#{yearMonth} AND evaluate_date<=#{yearMonth}+5 GROUP BY id ")
    List<StudentPerformanceVO> selectAllByItemWithoutTid(Integer tid, Integer yearMonth);

    @Select("select id,student_name AS name,morality_score AS virtue," +
            "intelligence_score AS intelligence,physical_score AS sports," +
            "aesthetic_score AS art,labour_score AS labor,remark,tid," +
            "student_num AS sid,sum(morality_score+intelligence_score+physical_score+aesthetic_score+labour_score)/5" +
            " AS total_score \n" +
            "from fu_student_score WHERE tid=#{tid} GROUP BY id ")
    List<StudentPerformanceVO> selectByTid2(Integer tid);
}
