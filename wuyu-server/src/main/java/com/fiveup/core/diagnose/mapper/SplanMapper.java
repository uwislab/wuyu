package com.fiveup.core.diagnose.mapper;


import com.fiveup.core.diagnose.bean.Student_plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SplanMapper {

    /*获得所有的学生信息及其计划信息*/
    //@Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade, p.p_deyu,p.p_zhiyu,p.p_tiyu,p.p_meiyu,p.p_laoyu,p.p_plan from di_student s right join di_studentplan p on s.student_id=p.p_id")
    public List<Student_plan> getallPlan(String name,Long id);

    /*获得指定某些学生信息及其计划信息*/
    @Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade, p.p_deyu,p.p_zhiyu,p.p_tiyu,p.p_meiyu,p.p_laoyu,p.p_plan from di_student s right join di_studentplan p on s.student_id=p.p_id where s.student_id like CONCAT('%',#{id},'%') or s.student_name like CONCAT('%',#{name},'%')")
    public List<Student_plan> getPlanByid(String name,Long id);

    /*获得指定一个学生的计划信息*/
    @Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade, p.p_deyu,p.p_zhiyu,p.p_tiyu,p.p_meiyu,p.p_laoyu,p.p_plan from di_student s right join di_studentplan p on s.student_id=p.p_id where s.student_id =#{id} or s.student_name =#{name}")
    public Student_plan getPlanByidone(String name,Long id);
}
