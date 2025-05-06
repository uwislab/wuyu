package com.fiveup.core.commentgeneration.mapper;

import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.commentgeneration.bean.Subject;
import com.fiveup.core.commentgeneration.vo.CorpusVO;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 对语料库的数据操作
 */
@Mapper
public interface CorpusMapper {
    /**
     * 插入数据
     * @param corpus
     */
    @Insert("insert into corpus(subject_id,score,comment,tid) values (#{subjectId},#{score},#{comment},1)")
    void insert(Corpus corpus);

    /**
     * 根据ID删除数据
     * @param id
     */
    @Delete("delete from corpus where id = #{id}")
    void deleteById(Integer id);

    /**
     * 根据ID更新数据
     * @param corpus
     */
    @Update("UPDATE corpus SET subject_id = #{subjectId},score = #{score},comment = #{comment} WHERE id = #{id}")
    void updateById(Corpus corpus);
    /**
     * 查询所有数据
     * @return
     */
    @Select("select * from corpus")
    List<Corpus> selectList();

    /**
     * 查询所有已封装数据
     * @return
     */
    @Select("SELECT id, domain_name AS name FROM fu_domain WHERE id = #{subject_id}")
    Subject selectById1(Integer subject_id);

    @Select("SELECT c.*, f.id AS subject_id, f.domain_name AS subject_name FROM corpus c JOIN fu_domain f ON c.subject_id = f.id")
    @Results({
            @Result(property = "subject", column = "subject_id", javaType = Subject.class, one = @One(select = "selectById1"))
    })
    List<CorpusVO> selectAll();

    /**
     * 根据ID查询数据
     * @param id
     * @return
     */
    @Select("select * from corpus where id = #{id}")
    CorpusVO selectById(Integer id);


    @Select("select * from corpus where tid = 1 LIMIT #{offset},#{pageSize}")
    List<Corpus> findAll1(int offset,int pageSize);

    @Select("<script>" +
            "SELECT * FROM corpus " +
            "<where>" +
            "`comment` LIKE CONCAT('%', #{comment}, '%') AND tid = 1 " +
            "<if test='subjectId != null'> " +
            "AND subject_id = #{subjectId} " +
            "</if>" +
            "</where>" +
            "</script>")
    List<CorpusVO> search(@Param("subjectId") Integer subjectId, @Param("comment") String comment);


    /**
     * 根据ID进行批量删除
     * @param ids
     * @return
     */
    @Delete("<script>" +
            "DELETE FROM corpus WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +  // 使用 id 作为 item
            "</foreach>" +
            "</script>")
    Integer deleteAll(@Param("ids") Integer[] ids);
}
