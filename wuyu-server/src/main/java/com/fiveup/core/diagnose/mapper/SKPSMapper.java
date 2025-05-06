package com.fiveup.core.diagnose.mapper;


import com.fiveup.core.diagnose.bean.student_kpointsScore;
import com.fiveup.core.diagnose.bean.student_zhiyuScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SKPSMapper {

    /*依据姓名或id查询学生知识点得分情况*/
    @Select("select s.student_id,s.student_name,dk.k_name,dk.k_proportion,ds.ks_score FROM di_studentkscore ds JOIN di_student s on ds.student_id=s.student_id JOIN di_kproportion dk on dk.k_id=ds.k_id where dk.k_type=#{type} and (s.student_id = #{id} or s.student_name = #{name})")
    public List<student_kpointsScore> getstudentKpointsScoreByKey(String type,String name,Long id);


    /*依据班级年级查询班级的学科得分情况*/
    @Select("select sz.chinese,sz.math,sz.english from di_student s join di_studentzhiyuscore sz on s.student_id=sz.zhiyu_id where s.student_grade=#{grade} and s.student_class_number=#{sclass} ")
    public List<student_zhiyuScore> getclassSubject(int grade, int sclass);


}
