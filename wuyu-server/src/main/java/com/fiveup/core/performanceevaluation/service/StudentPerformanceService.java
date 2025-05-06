package com.fiveup.core.performanceevaluation.service;

import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.dto.PageDto;
import com.fiveup.core.performanceevaluation.vo.Average;
import com.fiveup.core.performanceevaluation.vo.StudentPerformanceVO;
import org.python.antlr.op.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生表现的业务逻辑处理
 */
public interface StudentPerformanceService {

    /**
     * 增加
     * @param studentPerformance
     */
    void add(StudentPerformance studentPerformance);

    /**
     * 根据ID删除数据
     * @param id
     */
    void deleteById(Integer id);

   Integer[] getAverageGrade(Integer tid);
    /**
     * 根据ID更新数据
     * @param studentPerformance
     */
    void updateById(StudentPerformance studentPerformance);

    /**
     * 获取所有已封装数据
     * @return
     */
    List<StudentPerformanceVO> getAll();

    List<StudentPerformanceVO> getAllByTidChange(Integer tid);

    /**
     * 根据教师ID获取已封装数据
     * @param tid
     * @return
     */
    List<StudentPerformanceVO> getByTId(Integer tid);

    List<StudentPerformanceVO> AutoRemark(Integer tid, Integer year_month);

    /**
     * 查询记录总数
     * @return
     */
    Integer getCount(String sql);


    /**
     * 根据老师查询记录总数
     * @return
     */
    Integer getCountByTeacher(Integer tid);

    /**
     * 分页查询
     * @param start 起始
     * @param pageSize
     * @return
     */
    List<StudentPerformanceVO> getPagination(Integer start, Integer pageSize);

    /**
     * 分页查询
     * @param start 起始
     * @param pageSize
     * @return
     */
    List<StudentPerformanceVO> getPaginationByTeacherId(Integer tid,Integer start, Integer pageSize);
    /**
     * 根据学号查询对应的数据
     * @param sid
     * @return
     */
    StudentPerformanceVO getBySid(Integer sid);

    /**
     * 根据ID查询对应的数据
     * @param id
     * @return
     */
    StudentPerformanceVO getById(Integer id);

    /**
     * 根据教师ID查询对应的平均分
     * @param tid
     * @return
     */
    Average getAverageByTid(Integer tid);

    /**
     * 查询教师ID对应的除查询的学生之外的所有学生的总数
     * @param tid
     * @param sid
     * @return
     */
    List<Integer> getSubjectScoreSum(Integer tid, Integer sid);

    //实现通过学科查询语料库
    List<Corpus> getCorpusBySubject(Integer id);

    List<String> getItem();

    List<StudentPerformanceVO> getAllByItem(Integer tid, Integer yearMonth);
}
