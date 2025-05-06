package com.fiveup.core.remark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.remark.entity.studentInfo;
import com.fiveup.core.remark.entity.studentAllInfo;
import com.fiveup.core.remark.entity.studentScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper //数据库操作
public interface studentAllInfoMapper extends BaseMapper<studentAllInfo> {

    //分页查询，查找对应学生姓名（studentName），年级（value），班级（valueClass），状态（valueZT）的学生
    @Select("SELECT DISTINCT a.id, b.student_num,b.student_name, b.class_id,b.grade_id,a.isenter,a.isreview,a.morality_score, " +
            "a.intelligence_score,a.physical_score,a.aesthetic_score,a.labour_score,a.remark,a.tid " +
            "FROM fu_student_score AS a " +
            "LEFT JOIN basic_student AS b ON a.student_num = b.student_num " +
            "where b.student_name like concat ('%',#{studentName},'%') and " +
            " b.grade_id like concat ('%',#{value},'%') and " +
            "b.class_id like concat ('%',#{valueClass},'%') and " +
            " a.isreview like concat ('%',#{valueZT},'%') limit #{pageNum},#{pageSize}")
    public List<studentAllInfo> findstuAllInfo(@Param("studentName") String studentName,@Param("value") String value,
                                               @Param("valueClass") String valueClass,@Param("valueZT") String valueZT,
                                               @Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize);
    //上面分页查询查到的总条目
    @Select("SELECT count(DISTINCT a.id) " +
            "FROM fu_student_score AS a " +
            "LEFT JOIN basic_student AS b ON a.student_num = b.student_num " +
            "where b.student_name like concat ('%',#{studentName},'%') and " +
            " b.grade_id like concat ('%',#{value},'%') and " +
            "b.class_id like concat ('%',#{valueClass},'%') and " +
            " a.isreview like concat ('%',#{valueZT},'%')")
    Integer findTotal(@Param("studentName") String studentName,@Param("value") String value,
                      @Param("valueClass") String valueClass,@Param("valueZT") String valueZT);

}
