package com.fiveup.core.management.mapper;

import com.fiveup.core.management.pojo.SportScore;
import com.fiveup.core.management.pojo.SportSearchVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SportScoreMapper {
    /**
     * 查询所有体育成绩
     * @return 所有体育成绩
     */
    List<SportScore> findAllSportScore();

    /**
     * 根据关键词查询学生体育成绩
     * @param sportSearchVO 搜索条件
     * @return 学生体育成绩
     */
    List<SportScore> findSportScoreForSearch(SportSearchVO sportSearchVO);

    /**
     * 删除学生肺活量体育成绩
     * @param studentNum
     * @return 影响行数
     */
    @Delete("delete from si_fvc_score where student_num=#{studentNum}")
    int deleteFvcSportScore(String studentNum);

    /**
     * 删除学生跳绳体育成绩
     * @param studentNum
     * @return 影响行数
     */
    @Delete("delete from si_rs_score where student_num=#{studentNum}")
    int deleteRsSportScore(String studentNum);

    /**
     * 删除学生体脂率体育成绩
     * @param studentNum
     * @return 影响行数
     */
    @Delete("delete from si_bmi_score where student_num=#{studentNum}")
    int deleteBmiSportScore(String studentNum);

    /**
     * 删除学生仰卧起坐体育成绩
     * @param studentNum
     * @return 影响行数
     */
    @Delete("delete from si_sar_score where student_num=#{studentNum}")
    int deleteSarSportScore(String studentNum);

    /**
     * 删除学生坐位体前屈体育成绩
     * @param studentNum
     * @return 影响行数
     */
    @Delete("delete from si_sub_score where student_num=#{studentNum}")
    int deleteSubSportScore(String studentNum);

    /**
     * 删除学生50米往返跑体育成绩
     * @param studentNum
     * @return 影响行数
     */
    @Delete("delete from si_50x8oab_score where student_num=#{studentNum}")
    int deleteDashSportScore(String studentNum);

    /**
     * 删除学生50米短跑体育成绩
     * @param studentNum
     * @return 影响行数
     */
    @Delete("delete from si_50mdash_score where student_num=#{studentNum}")
    int deleteOabSportScore(String studentNum);




    /**
     * 编辑学生学生肺活量体育成绩
     * @param sportScore
     * @return 影响行数
     */
    @Update("update si_fvc_score set score=#{fvcscore} where student_num=#{studentNum}")
    int editFvcSportScore(SportScore sportScore);

    /**
     * 编辑学生学生跳远体育成绩
     * @param sportScore
     * @return 影响行数
     */
    @Update("update si_rs_score set score=#{rsscore} where student_num=#{studentNum}")
    int editRsSportScore(SportScore sportScore);

    /**
     * 编辑学生学生体脂率体育成绩
     * @param sportScore
     * @return 影响行数
     */
    @Update("update si_bmi_score set score=#{bmiscore} where student_num=#{studentNum}")
    int editBmiSportScore(SportScore sportScore);

    /**
     * 编辑学生学生仰卧起坐体育成绩
     * @param sportScore
     * @return 影响行数
     */
    @Update("update si_sar_score set score=#{sarscore} where student_num=#{studentNum}")
    int editSarSportScore(SportScore sportScore);

    /**
     * 编辑学生学生50米往返跑体育成绩
     * @param sportScore
     * @return 影响行数
     */
    @Update("update si_50mdash_score set score=#{dashscore} where student_num=#{studentNum}")
    int editDashSportScore(SportScore sportScore);

    /**
     * 编辑学生学生50米跑体育成绩
     * @param sportScore
     * @return 影响行数
     */
    @Update("update si_50x8oab_score set score=#{oabscore} where student_num=#{studentNum}")
    int editOabSportScore(SportScore sportScore);

    /**
     * 编辑学生坐位前屈体育成绩
     * @param sportScore
     * @return 影响行数
     */
    @Update("update si_sub_score set score=#{subscore} where student_num=#{studentNum}")
    int editSubSportScore(SportScore sportScore);



    /**
     * 添加一条肺活量体育成绩
     * @param sportScore
     */
    @Insert("insert into si_fvc_score(student_num,score,value,level,class_id) values(#{studentNum},#{fvcscore},4000,5,#{classNum})")
    int addFvcSportScore(SportScore sportScore);

     /**
     * 添加一条跳绳体育成绩
     * @param sportScore
     */
     @Insert("insert into si_rs_score(student_num,score,value,level,class_id) values(#{studentNum},#{rsscore},140,3,#{classNum})")
     int addRsSportScore(SportScore sportScore);

     /**
     * 添加一条体脂率体育成绩
     * @param sportScore
     */
     @Insert("insert into si_bmi_score(student_num,score,level,class_id,value_stature,value_weight) values(#{studentNum},#{bmiscore},3,#{classNum},'165',60)")
     int addBmiSportScore(SportScore sportScore);

     /**
     * 添加一条坐位体前屈体育成绩
     * @param sportScore
     */
     @Insert("insert into si_sar_score(student_num,value,score,level,class_id) values(#{studentNum},20,#{sarscore},2,#{classNum})")
     int addSarSportScore(SportScore sportScore);

     /**
     * 添加一条仰卧起坐体育成绩
     * @param sportScore
     */
     @Insert("insert into si_sub_score(student_num,value,score,level,class_id) values(#{studentNum},30,#{sarscore},4,#{classNum})")
     int addSubSportScore(SportScore sportScore);

     /**
     * 添加一条50米往返跑体育成绩
     * @param sportScore
     */
     @Insert("insert into si_50x8oab_score(student_num,score,value_minute,value_second,level,class_id) values(#{studentNum},#{oabscore},4,10,1,#{classNum})")
     int addOabSportScore(SportScore sportScore);

     /**
     * 添加一条50米短跑体育成绩
     * @param sportScore
     */
     @Insert("insert into si_50mdash_score(student_num,value,score,level,class_id) values(#{studentNum},'4.00',#{dashscore},1,#{classNum})")
     int addDashSportScore(SportScore sportScore);


    /**
     * 通过学号查询肺活量体育成绩
     * @param studentNum
     */
    @Select("select count(*) from si_fvc_score where student_num=#{studentNum}")
    int findFvcSportScoreByStudentNum(String studentNum);

    /**
     * 通过学号查询跳绳体育成绩
     * @param studentNum
     */
    @Select("select count(*) from si_rs_score where student_num=#{studentNum}")
    int findRsSportScoreByStudentNum(String studentNum);

    /**
     * 通过学号查询体脂率体育成绩
     * @param studentNum
     */
    @Select("select count(*) from si_bmi_score where student_num=#{studentNum}")
    int findBmiSportScoreByStudentNum(String studentNum);

    /**
     * 通过学号查询坐位体前屈体育成绩
     * @param studentNum
     */
    @Select("select count(*) from si_sar_score where student_num=#{studentNum}")
    int findSarSportScoreByStudentNum(String studentNum);

    /**
     * 通过学号查询仰卧起坐体育成绩
     * @param studentNum
     */
    @Select("select count(*) from si_sub_score where student_num=#{studentNum}")
    int findSubSportScoreByStudentNum(String studentNum);

    /**
     * 通过学号查询50米往返跑体育成绩
     * @param studentNum
     */
    @Select("select count(*) from si_50x8oab_score where student_num=#{studentNum}")
    int findOabSportScoreByStudentNum(String studentNum);

    /**
     * 通过学号查询50米短跑体育成绩
     * @param studentNum
     */
    @Select("select count(*) from si_50mdash_score where student_num=#{studentNum}")
    int findDashSportScoreByStudentNum(String studentNum);

    /**
     * 批量删除肺活量体育成绩
     * @param ids 学号集
     * @return
     */
    int deleteBatchFvc(List<String> ids);
    /**
     * 批量删除跳绳体育成绩
     * @param ids 学号集
     * @return
     */
    int deleteBatchRs(List<String> ids);
    /**
     * 批量删除体脂率体育成绩
     * @param ids 学号集
     * @return
     */
    int deleteBatchBmi(List<String> ids);
    /**
     * 批量删除坐位体前屈体育成绩
     * @param ids 学号集
     * @return
     */
    int deleteBatchSar(List<String> ids);
    /**
     * 批量删除仰卧起坐体育成绩
     * @param ids 学号集
     * @return
     */
    int deleteBatchSub(List<String> ids);
    /**
     * 批量删除50米往返跑体育成绩
     * @param ids 学号集
     * @return
     */
    int deleteBatchOab(List<String> ids);
    /**
     * 批量删除50米短跑体育成绩
     * @param ids 学号集
     * @return
     */
    int deleteBatchDash(List<String> ids);

    @Select("select class_id from basic_student where student_num=#{studentNum}")
    int findClassIdByStudentNum(String studentNum);
}
