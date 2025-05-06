package com.fiveup.core.teacherworkspace.mapper;

import com.fiveup.core.teacherworkspace.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherWorkspaceMapper {

    @Select("select * from basic_teacher")
    List<Teacher> getAllTeacher();

    @Select("select id,username,age,info,birth_place as birthPlace,political_appearance as politicalAppearance,school_id as schoolId,deleted,title,position,password,role,gender,phone_num as phoneNum,teacher_name as teacherName from basic_teacher where id=#{id}")
    Teacher getTeacherInfo(Long id);


    @Select("select real_name from basic_web_user where identity=#{identity}")
    List<String> getTeacherByIdentity(int identity);

    @Select("select real_name, gender, identity_info " +
            "from correlation_grade_teacher a, basic_user b, basic_user_identity c " +
            "where grade_id=#{gradeId} and class_id=#{classId} and a.teacher_id=b.user_id and b.identity=c.identity_id")
    List<LessonTeacher> getTeacherByClass(int gradeId, int classId);

    @Select("SELECT real_name, contact_info " +
            "FROM basic_user join correlation_grade_teacher on user_id = teacher_id " +
            "where grade_id=2 and class_id=3 and identity=3")
    LessonTeacher getMonitorByClass(int gradeId, int classId);

    @Select("select * from basic_lesson where teacher_id = #{teacherId}")
    List<Lesson> getLessoByTeacherId(Long teacherId);

    //    @Update("update fu_score set upid = #{upid}, title = #{title}, type = #{type}, starttime = #{starttime}, finishtime = #{finishtime}," +
//            "zhibiao = #{zhibiao}, zhibiao2 = #{zhibiao2}, zhibiao3 = #{zhibiao3}, score = #{score}, beizhu = #{beizhu}, status = #{status}, " +
//            "teacher_name = #{teacherName} where id = #{id}")
    int updateWork(Work work);
    @Update("update basic_teacher set id = #{id},teacher_name=#{teacherName},username = #{username},gender=#{gender},title=#{title},role=#{role},deleted=#{deleted},password=#{password},position=#{position},phone_num=#{phoneNum},username=#{username},political_appearance=#{politicalAppearance},birth_place=#{birthPlace},age=#{age},info=#{info} where id=#{id}; ")
    int updateTeacherInfo(Teacher teacher);

    @Update("update basic_teacher set password = #{password} where id=#{id};")

    int updatePassword(Teacher teacher);

    @Update("update basic_web_user set password=#{password} where id=#{id}")
    int updateUserPassword(Teacher teacher);


    @Delete("delete from fu_score where id = #{id}")
    int deleteWork(@Param("id") Long id);


    @Insert("insert into fu_score (upid,title,type,starttime,finishtime,zhibiao,zhibiao2,zhibiao3,score,beizhu,`status`,teacher_name)" +
            "values (#{upid},#{title},#{type},#{starttime},#{finishtime},#{zhibiao},#{zhibiao2},#{zhibiao3},#{score},#{beizhu},#{status},#{teacherName})")
    int insertWork(Work work);

    int deleteBatch(List<Integer> ids);


    //工程实践4
    @Select("select * from fu_score where id like #{id} and title like #{title} and type like #{type}")
    List<Work> getAllWork(String id, String title, String type);

    @Select("select * from fu_score where id like #{id} and title like #{title} and type like #{type} ORDER BY id DESC limit #{pageNum},#{pageSize}")
    List<Work> getWorkByPage(int pageNum, int pageSize, String id, String title, String type);

    // 多表查询，结果包括班级ID、年级、班级、学生人数
    @Select("select basic_class.id, grade, class_name, COUNT(student_name) as student_count " +
            "from basic_class, basic_student " +
            "where monitor_id=#{teacherId} and basic_student.class_id = basic_class.id GROUP BY basic_class.id")
    ClassBasicInfo getClassBasicInfo(Long teacher_id);
}
