package com.fiveup.core.demonstrate.mapper;


    import com.fiveup.core.demonstrate.entity.Gcsj4;
    import org.apache.ibatis.annotations.Mapper;
    import org.apache.ibatis.annotations.Select;

    import java.util.List;
@Mapper
public interface Gcsj4Mapper {

    @Select("select * from fu_grade_score ")
    List<Gcsj4> getGcsj4();



}
