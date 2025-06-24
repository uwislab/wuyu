package com.fiveup.core.fuScale.mapper;

import com.fiveup.core.fuScale.model.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface FuScaleMapper {

    // 获取领域列表
    @Select("select * from fu_domain")
    List<Domain> getDomainList();

    // 新增评价量表表头并返回自增id
    @Insert("insert into fu_scale_content(title, create_date, state, creator_id, domain, grade) " +
            "value(#{title}, #{createDate}, #{state}, #{creatorId}, #{domain}, #{grade})")
    @Options(useGeneratedKeys = true, keyProperty = "scaleId")
    int insertFuScale(ScaleInfo scaleInfo);

    // 新增评价内容
    @Insert("insert into fu_item_content(scale_id, item_content, pre_item, item_level, item_score, evaluation_method, evaluators, remark, pre_item_id)" +
            "value(#{scaleId}, #{itemContent}, #{preItem}, #{itemLevel}, #{itemScore}, #{evaluationMethod}, #{evaluators}, #{remark},#{preItemId})")
    int insertScaleContent(ScaleContent scaleContent);

    @Update("update fu_item_content set item_content = #{itemContent},item_score=#{itemScore}," +
            " evaluation_method=#{evaluationMethod} ,evaluators=#{evaluators},remark=#{remark} where id=#{itemId}")
    int updateScaleContent(ScaleContent scaleContent);

    // 获取最新量表ID
    @Select("select max(id) from fu_scale_content")
    int getFuScaleId();

    @Select("select * from fu_scale_content where title like concat('%',#{title},'%')")
    List<ScaleInfo> getFuScaleByTitle(String title);

    // 获取最新量表信息
    @Select("select * from fu_item_content where id=(select max(id) from fu_item_content)")
    ScaleContent getItemContent();

    // 删除评价内容
    @Delete("delete from fu_item_content where id=#{id}")
    int deleteItemContent(int id);

    //根据id删除量表
    @Delete("delete from fu_scale_content where id=#{id}")
    Integer deleteFuScaleContent(Integer id);

    //删除量表后根据量表id删除评价内容
    @Delete("delete from fu_item_content where scale_id=#{scaleId}")
    Integer deleteFuItemContent(Integer scaleId);

    //测试1
    @Select("select *,id item_id from fu_item_content where scale_id=#{scaleId} and item_level = 1")
    List<ScaleContent> getContentByIdCopy(Integer scaleId);

    @Select("select *,id item_id from fu_item_content where scale_id=#{scaleId} and item_level = 2")
    List<ScaleContent> getContentByIdCopy2(Integer scaleId);

    @Select("select *,id item_id from fu_item_content where scale_id=#{scaleId} and item_level = 3")
    List<ScaleContent> getContentByIdCopy3(Integer scaleId);

    @Select("select *,id item_id from fu_item_content where scale_id=#{scaleId}")
    List<ScaleContent> getContentById(Integer scaleId);

    // 通过scaleId和preItemId获取最新插入的上级指标
    @Select("SELECT * FROM `fu_item_content` WHERE " +
            "item_content =(SELECT item_content FROM `fu_item_content` WHERE id = #{preItemId}) " +
            "AND scale_id = #{scaleId} " +
            "AND item_level = (SELECT item_level FROM `fu_item_content` WHERE id = #{preItemId})")
    List<ScaleContent> getNewScaleContentByPre(@Param("scaleId") Integer scaleId,
                                               @Param("preItemId") Integer preItemId);

    @Select("select * from fu_item_content where scale_id=#{scaleId} and evaluators is not null")
    List<ScaleContent> getEditableContent(Integer scaleId);

    //测试3
    @Select("select count(1) from fu_item_content where pre_item_id = #{id}")
    Integer hasChildrenForItem(Integer id);

    //测试2
    @Select("select *,id item_id from fu_item_content where pre_item_id=#{preItemId}")
    List<ScaleContent> getItemByPreCopy(Integer preItemId);

    @Update("update fu_scale_content set state = #{state} where id = #{scaleId}")
    Integer changeState(ScaleInfo scaleInfo);

    @Select("select *,id scale_id from fu_scale_content where state = #{stateId}")
    List<ScaleInfo> getScaleByState(Integer stateId);

    @Select("select *,id scale_id from fu_scale_content")
    List<ScaleInfo> getAllFuScale();

    @Select("select teacher_name from basic_teacher where id = #{id}")
    String getCreatorName(Integer id);

    @Select("select count(1) from fu_item_content where scale_id = #{id}")
    Integer hasChildrenForScale(Integer id);

    //获取所有量表
    @Select("SELECT sc.*, " +
            "       sc.id AS scale_id, " +
            "       bt.teacher_name AS creatorName, " +
            "       (SELECT COUNT(*) FROM fu_item_content fic WHERE fic.scale_id = sc.id) AS hasChildren " +
            "FROM fu_scale_content sc " +
            "LEFT JOIN basic_teacher bt ON sc.creator_id = bt.id")
    List<ScaleInfo> scaleList();

    //根据创建者姓名获得该id
    @Select("select id from basic_teacher where teacher_name like concat('%',#{name},'%')")
    List<Integer> getCreatorIdByName(String name);

    List<ScaleInfo> getScaleByTitleUserDate(ScaleInfo scaleInfo);

    @Select("select b.id from (fu_item_content b left join fu_item_content c on b.id = c.pre_item_id) where b.pre_item_id = #{itemId}\n" +
            "union\n" +
            "select c.id from (fu_item_content b left join fu_item_content c on b.id = c.pre_item_id) where b.pre_item_id = #{itemId}\n")
    Set<Integer> getLowLevelId(Integer itemId);

    Integer deleteItem(Set<Integer> set);

    @Delete("delete from fu_item_content where scale_id = #{scaleId}")
    Integer deleteAllItemByScaleId(Integer scaleId);

    @Delete("delete from fu_scale_content where id = #{scaleId}")
    Integer deleteScale(Integer scaleId);

    @Select("select *,id scale_id from fu_scale_content where id = #{id}")
    ScaleInfo getFuScaleById(Integer id);

    Integer updateScale(ScaleInfo scaleInfo);

    //根据子项id查询父项id
    @Select("select pre_item_id from fu_item_content where id=#{id}")
    Integer getContentPreIDbyID(Integer id);

    //根据id获取其子项的分数总和
    @Select("select sum(item_score) from fu_item_content where pre_item_id=#{id}")
    Integer getSumOfChildScoreById(Integer id);

    //根据id获取当前id的评价项的分数
    @Select("select sum(item_score) from fu_item_content where id=#{id}")
    Integer getScoreById(Integer id);

    //根据评价量表id查询其所有评论项
    @Select("select id as itemId, scale_id, item_content, "+
            "pre_item, item_level, item_score, evaluation_method,"+
            " evaluators, remark, pre_item_id from fu_item_content where scale_id = #{scaleid}")
    List<ScaleContent> getAllInfoByID(Integer scaleid);

    //复制对应评价并返回自增id
    @Insert("insert into fu_item_content(scale_id, item_content, pre_item, item_level, item_score, evaluation_method, evaluators, remark, pre_item_id)" +
            "value(#{scaleId}, #{itemContent}, #{preItem}, #{itemLevel}, #{itemScore}, #{evaluationMethod}, #{evaluators}, #{remark},#{preItemId})")
    @Options(useGeneratedKeys = true, keyProperty = "itemId")
    Integer copyScaleContent(ScaleContent scaleContent);


    @Select("select * from fu_item_content where evaluation_method is not null and scale_id = #{scaleId} and id not in " +
            " (SELECT DISTINCT pre_item_id from fu_item_content WHERE scale_id = #{scaleId} and pre_item_id is not null)")
    List<ScaleContent> getExam(Integer scaleId);

    int updateItemByPre(@Param("scaleContent") ScaleContent scaleContent);

    List<ScaleInfo> getScalesByCondition( ScaleInfo scaleInfo);

    List<Execution> getExecution(Execution execution);

    int addExecution(Execution execution);
    List<Execution> getExecutionByScaleId(@Param("scaleId") Integer scaleId);

    int delExecution(Integer execId);

    @Select("select DISTINCT(grade) from basic_class")
    List<Integer> getGrade();

    @Select("select count(*) from fu_execution where grade_id = #{gradeId} && scale_id = #{scaleId}")
    int isAdd( Integer scaleId,Integer gradeId);
}
