package com.fiveup.core.management.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.management.model.DTO.BasicStudent;
import com.fiveup.core.management.model.DTO.StuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface StuMapper extends BaseMapper<BasicStudent> {


    List<StuDTO> getStudentListByCondition(@Param("keyword") String keyword,
                                           @Param("gender") Integer gender,
                                           @Param("inclination") Integer inclination,
                                           @Param("classId") Long classId,
                                           @Param("schoolId") Long schoolId);
    List<StuDTO> getStudentListByConditions(@Param("keyword") String keyword,
                                           @Param("gender") Integer gender,
                                           @Param("inclination") Integer inclination,
                                           @Param("gradeId") Integer gradeId,
                                            @Param("classId") Integer classId,
                                           @Param("schoolId") Long schoolId);


    void insertOne(StuDTO stuDTO);

    void updateOne(StuDTO stuDTO);

    @Select("select student_num from basic_student where student_name=#{studentName}")
    String getStuNumByStudentName(String studentName);

    @Select("select * from basic_student where student_num=#{studentNum}")
    StuDTO getStuInfoByStudentNum(Long studentNum);

    @Select("select * from basic_student")
    List<StuDTO> getAllStudent();

    List<StuDTO> getStuListByClassId(Long classId);

    void softlyDeleteById(@Param("studentId") String studentId);

    List<StuDTO> getStuListBySchoolIdAndGradeName(Long schoolId, String grade);

    /**
     * 根据班级学校，班级查找学生列表
     * @param schoolId
     * @param grade
     * @param classId
     * @return
     */
    List<StuDTO> getStuListBySchoolIdAndGradeNameClassId(Long schoolId, String grade, String classId);

    HashMap<String, Object> getStudentInfoWithFuScore( int studentId);

    List<StuDTO> getStudentListByName(String studentName);
    @Select("select * from basic_student where student_name=#{studentName}")
    StuDTO getStudentByName(String studentName);

    @Select("select * from basic_student where student_gender=#{gender}")
    StuDTO getStudentByGender(String gender);

    int deleteBatch(List<Integer> ids);

    @Update("update basic_student set parent_phone_num = #{phoneNum} where student_num=#{studentNum}")
    void updateStudentByNum(int studentNum,String phoneNum);

    @Update("update basic_student set isreview=1 where student_num=#{studentNum}")
    void confirmReview(String studentNum);
}
