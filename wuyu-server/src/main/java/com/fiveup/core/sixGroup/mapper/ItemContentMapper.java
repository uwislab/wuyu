package com.fiveup.core.sixGroup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.sixGroup.entity.DataRet;
import com.fiveup.core.sixGroup.entity.FuItemContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemContentMapper extends BaseMapper<FuItemContent> {

@Select("WITH RECURSIVE subquery AS (\n" +
        "  SELECT item_content, pre_item, id,item_score\n" +
        "  FROM fu_item_content\n" +
        "  WHERE item_content = #{firstIdxText}\n" +
        "  UNION ALL\n" +
        "  SELECT t.item_content, t.pre_item, t.id,t.item_score\n" +
        "  FROM fu_item_content t\n" +
        "  INNER JOIN subquery s ON t.pre_item = s.item_content\n" +
        ")\n" +
        "\n" +
        "\n" +
        "select b.grade_id,b.class_id,ROUND((SUM(CASE WHEN a.score >= d.item_score*#{rate} THEN 1 ELSE 0 END) / COUNT(*)) * 100, 2) AS rate\n" +
        "from basic_class c, six_stu_ic_score a, basic_student b, (SELECT * FROM subquery where pre_item is not null) d\n" +
        "where c.class = b.class_id and c.grade = b.grade_id and a.ic_id = d.id and a.stu_id = b.id \n" +
        "GROUP BY b.grade_id, b.class_id;")
  public List<DataRet> 一级指标下所有班级的指标(String firstIdxText, Float rate);
}
