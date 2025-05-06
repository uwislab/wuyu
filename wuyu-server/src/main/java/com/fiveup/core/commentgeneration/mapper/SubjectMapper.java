package com.fiveup.core.commentgeneration.mapper;

import com.fiveup.core.commentgeneration.bean.Subject;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 对科目的数据操作
 */
public interface SubjectMapper {
    /**
     * 查询所有数据
     * @return
     */
    @Select("SELECT * FROM fu_domain")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "domain_name")
    })
    List<Subject> selectAll();

    /**
     * 根据ID查询数据
     * @param id
     * @return
     */
    @Select("SELECT * FROM fu_domain where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "domain_name")
    })
    Subject selectById(Integer id);
}
