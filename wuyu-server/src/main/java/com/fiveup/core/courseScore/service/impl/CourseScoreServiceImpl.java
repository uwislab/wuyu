package com.fiveup.core.courseScore.service.impl;

import com.fiveup.core.courseScore.entity.CourseScore;
import com.fiveup.core.courseScore.entity.ResPage;
import com.fiveup.core.courseScore.mapper.CourseScoreMapper;
import com.fiveup.core.courseScore.service.CourseScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CourseScoreServiceImpl implements CourseScoreService {
    @Autowired
    private CourseScoreMapper courseScoreMapper;

    /**
     * 成绩录入
     *
     * @param courseScore
     */
    @Override
    public void save(CourseScore courseScore) {
        courseScoreMapper.insert(courseScore);
    }

    /**
     * 将全部成绩设置为85
     */
    @Override
    public void update85() {
        courseScoreMapper.update85();
    }

    @Override
    public List<CourseScore> getList(String teacher_name, int course_type, int test_number, String course_name) {
        return courseScoreMapper.getList(teacher_name, course_type, test_number, course_name);
    }

    /**
     * 条件分页查询
     *
     * @param courseName
     * @param courseType
     * @param teacherName
     * @param studentNum
     * @param studentName
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ResPage<List<CourseScore>> search(String courseName, Integer courseType, String teacherName, String studentNum, String studentName, Integer page, Integer pageSize) {
        // 设置分页信息
        PageHelper.startPage(page, pageSize);
        // 执行sql
        List<CourseScore> courseScoreList = courseScoreMapper.findByCondition(courseName, courseType, teacherName, studentNum, studentName);
        // 获取分页信息
        PageInfo<CourseScore> pageInfo = new PageInfo<>(courseScoreList);
        //
        log.debug(pageInfo.toString());
        // 封装分页信息返回给前端
        ResPage<List<CourseScore>> resPage = new ResPage<>();
        resPage.setPage(page);  // 当前页
        resPage.setSize(pageSize);  // 页大小
        resPage.setTotalPage(pageInfo.getPages());  // 总页数
        resPage.setTotal(pageInfo.getTotal());      // 总条数
        resPage.setData(courseScoreList);

        // 返回数据
        return resPage;
    }

    /**
     * 根据ids数组删除成绩
     *
     * @param ids
     */
    @Override
    public boolean deleteByIds(String[] ids) {
        return courseScoreMapper.deleteByIds(ids) == ids.length;
    }

    /**
     * 修改成绩
     *
     * @param courseScore
     */
    @Override
    public void edit(CourseScore courseScore) {
        courseScoreMapper.edit(courseScore);
    }


    @Override
    public List<String> getAllDistinctCourseNames() {
        return courseScoreMapper.getAllDistinctCourseNames();
    }

    @Override
    public List<Integer> getAllDistinctTestNumbers() {
        return courseScoreMapper.getAllDistinctTestNumbers();
    }

    @Override
    public List<CourseScore> getByCourseNameAndTestNumber(String courseName, Integer testNumber) {
        return courseScoreMapper.getByCourseNameAndTestNumber(courseName, testNumber);
    }

}
