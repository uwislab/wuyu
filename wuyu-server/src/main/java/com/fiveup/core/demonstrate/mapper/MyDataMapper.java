package com.fiveup.core.demonstrate.mapper;


import com.fiveup.core.demonstrate.entity.Score;
import com.fiveup.core.demonstrate.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface MyDataMapper{
    Map<String, Object> test();
    int addStudent(Student student);
    int addScore(Score score);

    List<Map<String,Object>> barTj2(@Param("gradeList") List<String> gradeParaList);
    List<Map<String, Object>> barTj3();

    List<Map<String,Object>> lineTj();
    List<Map<String, Object>> lineTj2();

    List<Map<String, Object>> getWanchengdu();

    Map<String,Object> getTj();
    List<Map<String, Object>> tj(Map<String, Object> paraMap);
    List<Map<String, Object>> tj2(Map<String, Object> paraMap);

    List<Map<String,Object>> Qxwycj(@Param("gradeList") List<String> gradeParaList);

    List<Map<String, Object>> getNjcz();

    List<Map<String, Object>> getXYStuByAll();

    List<Map<String, Object>> getXYStuByOne(@Param("one") String one );

    List<Map<String, Object>> getXYClaByAll();

    List<Map<String, Object>> getNjqk();

    List<Map<String, Object>> getSubLineTj();

    List<Map<String, Object>> getTable(@Param("queryParm") String queryParm ,@Param("range") String range);
}
