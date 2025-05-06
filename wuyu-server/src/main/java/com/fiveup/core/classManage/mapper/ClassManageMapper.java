package com.fiveup.core.classManage.mapper;

import com.fiveup.core.classManage.model.*;
import com.fiveup.core.classManage.model.response.ClassPageResp;
import com.fiveup.core.classManage.model.CTCorrelation;
import com.fiveup.core.classManage.model.ClassAndMonitor;
import com.fiveup.core.classManage.model.ClassInfo;
import com.fiveup.core.classManage.model.StudentInfo;
import com.fiveup.core.fuScore.model.ClassFuItemScore;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.fiveup.core.classManage.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ClassManageMapper {

    /**
     * 查询班级ID是否存在
     *
     * @param classId
     * @return true or false
     * @author 王德荣
     * @date 2024/11/25
     */
    @Select("SELECT COUNT(*) FROM basic_class WHERE grade_id = #{gradeId} AND class = #{classId}")
    Boolean existClass(int gradeId, int classId);

    /**
     * 查询班主任ID是否存在
     *
     * @param monitorId
     * @return true or false
     * @author 王德荣
     * @date 2024/11/25
     */
    @Select("SELECT COUNT(*) FROM basic_teacher WHERE id = #{monitorId}")
    Boolean existTeacher(int monitorId);

    /**
     *
     * @author 王德荣
     * @date 2024/11/25
     * @param grade
     * @param classId
     * @param className
     * @param monitorId
     * @param gradeId
     * @return int
     */
    @Insert("INSERT INTO basic_class (grade, class, class_name, monitor_id, grade_id) " +
            "VALUES (#{grade}, #{classId}, #{className}, #{monitorId}, #{gradeId})")
    Boolean addClass(String grade, int classId, String className, int monitorId, int gradeId);

    /**
     * 根据教师名获取教师ID
     * @author 王德荣
     * @date 2024/11/25
     * @param teacherName
     * @return int
     */
    @Select("SELECT id FROM basic_teacher WHERE teacher_name = #{teacherName}")
    int getIdByTeacherName(String teacherName);

    //
//    @Select("select grade_id, class_id, teacher_id, gender, real_name, contact_info, identity " +
//            "from correlation_grade_teacher a inner join basic_user b on a.teacher_id = b.user_id where identity=3")
//    List<ClassAndMonitor> getAllClassInfo();
//
//    @Insert("insert into correlation_grade_teacher(grade_id, class_id, teacher_id) values(#{gradeId}, #{classId}, #{teacherId})")
//    int addTeacherToClass(CTCorrelation ctCorrelation);
//
//    @Insert("insert into basic_grade(id, grade_id, class_id, class_name) values(#{id}, #{gradeId}, #{classId}, #{className})")
//    int addClass(ClassInfo classinfo);
//
//    @Select("select student_id, student_name, gender from basic_student where grade_id=#{gradeId} and class_id=#{classId}")
//    List<StudentInfo> getStudentsByClass(int gradeId, int classId);
//

//    @Select("select class_id from basic_grade where grade_id=#{gradeId}")
//    List<Integer> getAllClassByGrade(int gradeId);


    List<ClassPageResp> getClassByDynamicCondition(int grade, int monitorId);

    @Select("select id,teacher_name,gender,phone_num from basic_teacher where id = #{monitorId}")
    Teacher getTeacherByTeacherId(int monitorId);

    @Select("select count(*) from basic_student INNER JOIN basic_class ON basic_student.class_id=basic_class.class and basic_student.grade_id=basic_class.grade_id where basic_class.deleted=0 and basic_student.deleted=0 and basic_class.id = #{id}")
    int getStudentNumByClassId(int id);

    @Select("select id,teacher_name,gender,phone_num from basic_teacher where deleted=0 and role = '班主任'")
    List<Teacher> getAllMonitor();

    @Select("select id from basic_teacher where deleted=0 and role='班主任' and teacher_name = #{realName}")
    int getMonitorIdByName(String realName);

//    @Insert("insert into basic_grade(id, grade_id, class_id, class_name) values(#{id}, #{gradeId}, #{classId}, #{className})")
//    int addClass(ClassInfo classinfo);
//
//    @Update("update basic_grade set monitor_id=#{teacher_id} where id=#{id}")
//    Integer addTeacher(ClassInfo classInfo);

    @Select("select id,teacher_name,gender,phone_num from basic_teacher where role='班主任' and deleted=0")
    List<Teacher> getAllTeacher();

    @Insert("INSERT into basic_class(grade, class, class_name, monitor_id, deleted, grade_id, school_id)VALUES(#{grade},#{classType},#{className},#{monitorId},0,#{gradeId},1)")
    int addClass(Grade grade);

    @Update("update basic_teacher set role = '班主任' where id = #{monitorId}")
    int updateTeacher(int monitorId);

    @Select("select count(id) from basic_class where deleted=0 and grade_id = #{gradeId} and class = #{classType}")
    int selectClass(Grade grade);

    //李江平 TODO
    //这是在数据库里查询
    @Update("UPDATE basic_class SET deleted = 1 WHERE id = #{id} AND id NOT IN (SELECT id FROM (SELECT bc.id FROM basic_class bc INNER JOIN basic_student bs ON bs.class_id = bc.class AND bs.grade_id = bc.grade_id WHERE bs.deleted = 0 AND bc.id = #{id}) AS subquery)")
    int deleteClass(int id);
//    @Select("select class_id from basic_grade where grade_id=#{gradeId}")
//    List<Integer> getAllClassByGrade(int gradeId);

    @Select("SELECT basic_class.grade as grade,basic_class.class_name as className, basic_teacher.teacher_name as headTeacherNAme,basic_class.class_introduction as introductionToClass , basic_class.id  as classId " +
            " from basic_class INNER JOIN basic_teacher ON basic_class.monitor_id=basic_teacher.id " +
            " WHERE basic_class.grade=#{grade} and basic_class.class_name=#{className} and basic_class.deleted = 0")
    ClassPartInfo getClassPartInfoByClassId(String grade, String className);


    @Select("SELECT basic_student.student_num AS studentId, basic_student.student_name, gender,(SELECT COUNT(*) FROM basic_student INNER JOIN basic_class ON basic_student.class_id=basic_class.class and basic_student.grade_id=basic_class.grade_id WHERE basic_student.deleted=0 and basic_class.id = #{classId}) AS totalNum FROM basic_student INNER JOIN basic_class ON basic_student.class_id=basic_class.class and basic_student.grade_id=basic_class.grade_id WHERE basic_student.deleted=0 and basic_class.deleted=0 and basic_class.id = #{classId} LIMIT #{pageStart}, #{pageSize};")
    List<StudentInfo> getStudentByPage(int classId, int pageStart, int pageSize);

    @Update("update basic_student set deleted=1 where student_num=#{studentId}")
    int deletestudent(int studentId);

    @Update("UPDATE basic_class " +
            "SET class_introduction=#{classIntroduction} " +
            "WHERE deleted=0 and basic_class.id=#{classInformationId}")
    int updateClassByInformationId(int classInformationId, String classIntroduction);

    @Select("select num(*) " +
            "from basic_student  " +
            "where class_id=#{classId} ")
    int getStudentNum(int classId);
    @Select("select distinct class from basic_class where grade = #{gradeId} order by class;")
    List<Integer> getAllClassByGrade(int gradeId);
//    @Select("select class_id from basic_grade where grade_id=#{gradeId}")
//    List<Integer> getAllClassByGrade(int gradeId);

    @Select("SELECT DISTINCT class_name,class_ID FROM fu_class_score WHERE grade_ID = #{gradeId};")
    List<ClassFuItemScore> getClassName(int gradeId);

    @Select("SELECT id FROM basic_teacher where teacher_name=#{headTeacherNAme} and role='班主任';")
    Integer getTeacherId(String headTeacherNAme);

    @Insert("INSERT into basic_class(grade, class, class_name, monitor_id, deleted, grade_id)VALUES(#{grade},#{classType},#{className},#{monitorId},0,#{gradeId})")
    int addGrade(GradeInfo gradeInfo);

    @Select("SELECT count(*) FROM basic_class where grade=#{grade} and class_name=#{className} and deleted=0;")
    Integer getGradeNum(GradeInfo gradeInfo);

    @Update("UPDATE basic_class SET  monitor_id=#{monitorId} where grade=#{grade} and class_name=#{className}")
    Integer updateGrade(GradeInfo gradeInfo);
}



