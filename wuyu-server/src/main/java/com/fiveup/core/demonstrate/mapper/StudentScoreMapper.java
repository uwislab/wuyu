package com.fiveup.core.demonstrate.mapper;

import com.fiveup.core.demonstrate.entity.vo.ScoreAndClass13;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.python.antlr.ast.Str;
import java.util.List;
@Mapper
public interface StudentScoreMapper {


    List<ScoreAndClass13> getScore(int page_num,
                                   int page_size,
                                   String student_num,
                                   String student_name,
                                   String class_name,
                                   String grade);

    List<ScoreAndClass13> getAllScore(String className,String gradeName);
    int getTableSize(String student_num,
                     String student_name,
                     String class_name,
                     String grade);

    List<String> getClassName();

    List<String> getGradeName();

    List<ScoreAndClass13> getOneScore(String id);
}
