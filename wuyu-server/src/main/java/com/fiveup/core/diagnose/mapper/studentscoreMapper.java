package com.fiveup.core.diagnose.mapper;


import com.fiveup.core.diagnose.bean.GradeScoreBean;
import com.fiveup.core.diagnose.bean.student_classgrade;
import com.fiveup.core.diagnose.bean.student_gradesScore;
import com.fiveup.core.diagnose.bean.student_score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface studentscoreMapper {
    /*查询本次班级成绩*/
    @Select("select ds.s_deyu,ds.s_zhiyu,ds.s_tiyu,ds.s_meiyu,ds.s_laoyu from di_student s join di_studentscore ds on ds.student_id=s.student_id where s.student_grade=#{grade} and s.student_class_number=#{sclass} order by ds.s_exdate desc limit 5")
    public List<student_score> SelectScoreByClass(int grade,int sclass);
    /*查询本次年级成绩*/
    @Select("select ds.s_deyu,ds.s_zhiyu,ds.s_tiyu,ds.s_meiyu,ds.s_laoyu from di_student s join di_studentscore ds on ds.student_id=s.student_id where s.student_grade=#{grade} order by ds.s_exdate desc limit 10")
    public List<student_score> SelectScoreByGrade(int grade);
    /*查询上次班级成绩*/
    @Select("select ds.s_deyu,ds.s_zhiyu,ds.s_tiyu,ds.s_meiyu,ds.s_laoyu from di_student s join di_studentscore ds on ds.student_id=s.student_id where s.student_grade=#{grade} and s.student_class_number=#{sclass} order by ds.s_exdate desc limit 5,5")
    public List<student_score> SelectPreScoreByClass(int grade,int sclass);

    /*查询上次年级成绩*/
    @Select("select ds.s_deyu,ds.s_zhiyu,ds.s_tiyu,ds.s_meiyu,ds.s_laoyu from di_student s join di_studentscore ds on ds.student_id=s.student_id where s.student_grade=#{grade} order by ds.s_exdate desc limit 10,10")
    public List<student_score> SelectPreScoreByGrade(int grade);

    /*查询学生个人成绩*/
    @Select("select ds.s_deyu,ds.s_zhiyu,ds.s_tiyu,ds.s_meiyu,ds.s_laoyu from di_student s join di_studentscore ds on ds.student_id=s.student_id where s.student_id=#{id} or s.student_name=#{name} order by ds.s_exdate desc limit 2")
    public List<student_score> SelectScoreByKey(String name,Long id);

    /*查询学生所在班级,年级*/
    @Select("select student_grade,student_class_number from di_student where student_id=#{id} or student_name=#{name}")
    public student_classgrade getClass(String name, Long id);

    /*新增*/

    /*查询全部学生最近一次一年级五育成绩*/
    @Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade,ss.s_deyu,ss.s_zhiyu,ss.s_tiyu,ss.s_meiyu,ss.s_laoyu, ss.s_exdate from di_student s left join di_studentscore ss on s.student_id =ss.student_id where s.student_grade=1 order by s_exdate,student_id DESC limit 10;")
    public List<student_gradesScore> getgradeoneScore();

    /*查询全部学生最近一次二年级五育成绩*/
    @Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade,ss.s_deyu,ss.s_zhiyu,ss.s_tiyu,ss.s_meiyu,ss.s_laoyu, ss.s_exdate from di_student s left join di_studentscore ss on s.student_id =ss.student_id where s.student_grade=2 order by s_exdate,student_id DESC limit 10;")
    public List<student_gradesScore> getgradetwoScore();

    /*查询全部学生最近一次三年级五育成绩*/
    @Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade,ss.s_deyu,ss.s_zhiyu,ss.s_tiyu,ss.s_meiyu,ss.s_laoyu, ss.s_exdate from di_student s left join di_studentscore ss on s.student_id =ss.student_id where s.student_grade=3 order by s_exdate,student_id DESC limit 10;")
    public List<student_gradesScore> getgradethreeScore();

    /*查询全部学生最近一次四年级五育成绩*/
    @Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade,ss.s_deyu,ss.s_zhiyu,ss.s_tiyu,ss.s_meiyu,ss.s_laoyu, ss.s_exdate from di_student s left join di_studentscore ss on s.student_id =ss.student_id where s.student_grade=4 order by s_exdate,student_id DESC limit 10;")
    public List<student_gradesScore> getgradefourScore();

    /*查询全部学生最近一次五年级五育成绩*/
    @Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade,ss.s_deyu,ss.s_zhiyu,ss.s_tiyu,ss.s_meiyu,ss.s_laoyu, ss.s_exdate from di_student s left join di_studentscore ss on s.student_id =ss.student_id where s.student_grade=5 order by s_exdate,student_id DESC limit 10;")
    public List<student_gradesScore> getgradefiveScore();

    /*查询全部学生最近一次六年级五育成绩*/
    @Select("select s.student_id,s.student_name,s.student_class_number,s.student_grade,ss.s_deyu,ss.s_zhiyu,ss.s_tiyu,ss.s_meiyu,ss.s_laoyu, ss.s_exdate from di_student s left join di_studentscore ss on s.student_id =ss.student_id where s.student_grade=6 order by s_exdate,student_id DESC limit 10;")
    public List<student_gradesScore> getgradesixScore();
    @Select("select ")
    @Select("SELECT\n" +
            "    s.student_id,\n" +
            "    s.student_grade,\n" +
            "    s.student_class_number,\n" +
            "    s.student_name,\n" +
            "    sc.s_deyu,\n" +
            "    sc.s_zhiyu,\n" +
            "    sc.s_tiyu,\n" +
            "    sc.s_meiyu,\n" +
            "    sc.s_laoyu,\n" +
            "    sc.s_exdate\n" +
            "FROM\n" +
            "    di_student s\n" +
            "JOIN\n" +
            "    di_studentscore sc ON s.student_id = sc.student_id\n" +
            "ORDER BY\n" +
            "    s.student_grade ASC,\n" +
            "    s.student_class_number ASC,\n" +
            "    s.student_id ASC;")
    List<student_gradesScore> getAllScore();

    @Select("select distinct student_grade from di_student order by student_grade")
    int[] getGrades();

    @Select("select distinct student_class_number from di_student where student_grade = #{grade} order by student_class_number")
    int[] getClasses(int grade);

    @Select("SELECT\n" +
            "    '德育' AS name, \n" +
            "    AVG(sc.s_deyu) AS value\n" +
            "FROM \n" +
            "    di_student s\n" +
            "JOIN \n" +
            "    di_studentscore sc ON s.student_id = sc.student_id\n" +
            "WHERE\n" +
            "    s.student_grade = #{grade}\n" +
            "GROUP BY \n" +
            "    s.student_grade\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT\n" +
            "    '美育' AS name, \n" +
            "    AVG(sc.s_meiyu) AS value\n" +
            "FROM \n" +
            "    di_student s\n" +
            "JOIN \n" +
            "    di_studentscore sc ON s.student_id = sc.student_id\n" +
            "WHERE\n" +
            "    s.student_grade = #{grade}\n" +
            "GROUP BY \n" +
            "    s.student_grade\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT\n" +
            "    '劳育' AS name, \n" +
            "    AVG(sc.s_laoyu) AS value\n" +
            "FROM \n" +
            "    di_student s\n" +
            "JOIN \n" +
            "    di_studentscore sc ON s.student_id = sc.student_id\n" +
            "WHERE\n" +
            "    s.student_grade = #{grade}\n" +
            "GROUP BY \n" +
            "    s.student_grade\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT\n" +
            "    '体育' AS name, \n" +
            "    AVG(sc.s_tiyu) AS value\n" +
            "FROM \n" +
            "    di_student s\n" +
            "JOIN \n" +
            "    di_studentscore sc ON s.student_id = sc.student_id\n" +
            "WHERE\n" +
            "    s.student_grade = #{grade}\n" +
            "GROUP BY \n" +
            "    s.student_grade\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT\n" +
            "    '智育' AS name, \n" +
            "    AVG(sc.s_zhiyu) AS value\n" +
            "FROM \n" +
            "    di_student s\n" +
            "JOIN \n" +
            "    di_studentscore sc ON s.student_id = sc.student_id\n" +
            "WHERE\n" +
            "    s.student_grade = #{grade}\n" +
            "GROUP BY \n" +
            "    s.student_grade\n" +
            "\n" +
            "ORDER BY\n" +
            "    FIELD(name, '德育', '智育', '体育', '美育', '劳育');")
    List<GradeScoreBean> getGradeScoreBygrade(Integer grade);
}
