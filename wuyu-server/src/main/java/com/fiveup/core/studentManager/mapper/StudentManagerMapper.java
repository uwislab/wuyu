package com.fiveup.core.studentManager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.management.model.School;
import com.fiveup.core.studentManager.entity.StudentManager;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.pojo.StudentVO;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import java.util.Set;

public interface StudentManagerMapper extends BaseMapper<StudentManager> {
    @Select("select distinct (student_num) from basic_student where deleted=0")
    Set<String> selectnum();

    @Select("select concat(grade,'#',class) from basic_class where deleted=0 group by class,grade;")
    Set<String> selectclassgrade();

    //添加学生
    @Insert("INSERT INTO basic_student (student_num,student_name,gender,class_id,grade_id,parent_phone_num,deleted,isreview,isenter,school_id)" +
            "VALUES (#{studentNum},#{studentName},#{gender},#{classId},#{gradeId},#{parentPhoneNum},#{deleted},#{isreview},#{isenter},#{schoolId})")
    void insertStudent(StudentManager studentManager);

    //根据学校年级班级名查询class_id
    @Select("select id from basic_class where grade_id=#{gradeId} and school_id=#{schoolId} and class_name=#{className}")
    int selectClassId(int gradeId, int schoolId, String className);

    //对比增加的学号是否重复
    @Select("SELECT COUNT(1) FROM basic_student WHERE student_num = #{studentNum}")
    Integer countByStudentNum(String studentNum);

    //根据schoolId查询schoolName
    @Select("select school_name from basic_school where id = #{schoolId}")
    String getSchoolNameById(int schoolId);

    //分页查询学生
    List<StudentVO> getStudentPage(StudentManagerQuery studentManagerQuery);

    //获取学校列表
    @Select("select * from basic_school")
    List<School> getSchool();

    //获取班级列表
    @Select("select DISTINCT class_name from basic_class where deleted = 0 order by class_name")
    List<String> getClassName();

    //获取年纪列表
    @Select("select DISTINCT grade_id from basic_student order by grade_id")
    List<Integer> getGrade();


}
