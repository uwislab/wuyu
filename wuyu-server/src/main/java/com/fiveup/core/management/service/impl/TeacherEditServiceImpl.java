package com.fiveup.core.management.service.impl;

import com.fiveup.core.management.mapper.TeacherEditMapper;
import com.fiveup.core.management.model.Teacher;
import com.fiveup.core.management.service.TeacherEditService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 教师编辑服务实现类，负责实现教师信息的添加、编辑和删除操作。
 */
@Slf4j
@Service
public class TeacherEditServiceImpl implements TeacherEditService {
    @Autowired
    private SqlSessionFactory sqlSessionFactory; // MyBatis SQL会话工厂
    @Autowired
    private TeacherEditMapper teacherEditMapper; // 教师编辑Mapper接口

    /**
     * 添加教师信息。
     * @param data 教师信息列表
     */
    @Override
    public void add(List<Teacher> data) {
        // 单条数据
        if(data.size() == 1) {
            teacherEditMapper.add(data.get(0)); // 添加单个教师信息
            return;
        }
        // 多条数据
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false); // 开启批量操作的SQL会话
        TeacherEditMapper mapperNew = sqlSession.getMapper(TeacherEditMapper.class); // 获取Mapper实例
        data.stream().forEach(mapperNew::add); // 批量添加教师信息
        sqlSession.commit(); // 提交事务
    }

    /**
     * 编辑教师信息。
     * @param teacher 教师信息
     * @param classes 班级ID列表
     */
    @Override
    public void edit(Teacher teacher, List<Long> classes) {
        log.info("!!!!!ServiceTeacher: " + teacher);
        teacherEditMapper.edit(teacher);
        System.out.println("teacher = " + teacher);
        if(classes != null && classes.size() != 0){
            teacherEditMapper._deleteById(teacher.getId());
            classes.stream().forEach(id -> teacherEditMapper._edit(teacher.getId(), id,teacher.getTeacherName()));
        }
    }

    /**
     * 根据ID删除教师信息。
     * @param id 教师ID
     */
    @Override
    public void deleteById(Long id) {
        teacherEditMapper.deleteById(id); // 删除教师基本信息
        teacherEditMapper._deleteById(id); // 删除教师与班级的关联
        teacherEditMapper._deleteMonitorById(id); // 删除教师与班级监控的关联
    }
}
