package com.fiveup.core.diagnose.mapper;

import com.fiveup.core.diagnose.bean.student_QTypeScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QTypeScoreMapper {

    @Select("select qt.easyScore,qt.mediumScore,qt.difficuitScore from di_qtypescore qt join di_student s on qt.student_id=s.student_id  where (s.student_id=#{id} or s.student_name=#{name}) and qt.subject=#{subject}")
    public student_QTypeScore slectQTypeS(Long id,String name, String subject);
}
