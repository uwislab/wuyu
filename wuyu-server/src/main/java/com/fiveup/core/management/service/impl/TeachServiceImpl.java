package com.fiveup.core.management.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.fiveup.core.management.mapper.TeachMapper;
import com.fiveup.core.management.pojo.*;
import com.fiveup.core.management.service.TeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @Author 付义程
 * @Date 2024/11/25
 */

@Service
public class TeachServiceImpl implements TeachService {
    @Autowired
    TeachMapper mapper;

    /**
     * 查询所有老师信息
     * @return
     */
    @Override
    public List<TeacherVo> getAllTeacher() {

        return mapper.getAll();
    }

    @Transactional
    @Override
    public TeacherListVo getTeacherByPage(PageDto dto, long schoolId,List<Integer> classIds ) {
        //如果页码异常
        if (dto.getPageNum()<=0) {
            dto.setPageNum(1);
        }
        if (dto.getPageSize()<=0) {
            dto.setPageSize(1);
        }

        List<Integer> classList = new ArrayList<>();
        //判断是否有年级模糊查询
        System.out.println("模糊查询年级:"+dto.getGrade());
        if (dto.getGrade()!=null&&!dto.getGrade().trim().equals("")){
            classList = mapper.getClassByGrade(dto.getGrade());//如果有,获取该年级的所有的班级信息
            System.out.println("输出list:"+classList);
        }
        if (CollUtil.isNotEmpty(classIds)){
            if (CollUtil.isNotEmpty(classList)){
                for (Integer id : classList) {
                    if (!classIds.contains(id)){
                        classList.remove(id);
                    }
                }
            }else {
                classList = classIds;
            }
        }
        TeacherListVo vo = new TeacherListVo();
        int pageNum = dto.getPageNum();//当前请求页码

        int pageSize = dto.getPageSize();
        //查询所有数据
        //如果有年级查询,给定该年级的所有班级,判断该年级的老师有哪些,同时只会查询出老师该年级所教的班级,而不会查出其它年级的班级
        List<List<?>> list = new ArrayList<>();
        List<TeacherVo>  teacherVos = mapper.getTeacherByPage(schoolId,dto,classList);
        list.add(teacherVos);
        List<Integer> teacherCount = mapper.getTotalTeacherCount();
        list.add(teacherCount);
        List<TeacherVo> temp = (List<TeacherVo>) list.get(0);//所有符合条件的记录

        Map<Long,TeacherVo> map = new TreeMap<>();
        for (TeacherVo item:temp){
            if (item.getClassName()!=null&&item.getGrade()!=null){//如果没有班级和年级
                if (!map.containsKey(item.getId())){
                    item.setClassName(item.getGrade()+"年级"+item.getClassName());
                    map.put(item.getId(),item);
                }else {//如果一个老师教多个班级
                    TeacherVo teacherVo = map.get(item.getId());
                    teacherVo.setClassName(teacherVo.getClassName()+"/"+ item.getGrade()+"年级"+item.getClassName());
                }
            }else {//如果老师没有班级,则暂无
                item.setClassName("暂无");
                map.put(item.getId(),item);
            }

        }
        temp = new ArrayList<>(map.values());
        //去重,因为班级存在,可能存在多个相同教师但不同班级的记录
//        temp=temp.stream().collect(Collectors.collectingAndThen(
//                Collectors.toCollection(() -> new TreeSet<>(//先收集起来然后比较id,最后组成一个新的数组
//                        Comparator.comparing(o -> o.getId())
//                )), ArrayList::new));


        List<TeacherVo> result = new ArrayList<>();
        int total = temp.size();//总记录条数,从过滤后的数组中获得,否则分页显示异常
        vo.setTotal(total);
        //判断可以分多少页
        int sum = total/pageSize;
        //设置总页数
        if (sum>=vo.getPages())
            vo.setPages(vo.getTotal()%pageSize != 0 ? sum+1 : sum);

        //获取该页显示的数据
        if (pageNum<=vo.getPages()&&total!=0){
            for (int i = (pageNum-1)*pageSize; i < temp.size() && i<pageNum*pageSize; i++) {

                result.add(temp.get(i));
            }
            //设置当前页码
            vo.setCurPage(pageNum);
        }
        //如果页码超出总页数,可能进行了模糊查询
        if (pageNum>vo.getPages()&&total!=0){
            pageNum = 1;//设置为第一页
            vo.setCurPage(pageNum);//并且需要重新进行查询
            for (int i = 0; i < temp.size() && i<pageNum*pageSize; i++) {
                result.add(temp.get(i));
            }

        }
        //如果没有数据
        if (total==0){
            System.out.println(result);
            vo.setCurPage(1);
            vo.setPages(1);
        }
        //设置搜索结果

        vo.setList(result);

        //设置页面大小
        vo.setPageSize(pageSize);

        //判断是否为最后一页
        if (vo.getPages() == vo.getCurPage()){
            vo.setLast(true);
        }else {
            vo.setLast(false);
        }
        return vo;
    }

    @Override
    public List<TeacherExcel> searchTeacherList(PageDto dto, long schoolId) {
        List<TeacherExcel> teachList = mapper.getTeachList(schoolId, dto.getTeacherName(), dto.getTitle(), dto.getPosition());
        return teachList;
    }

    @Override
    public TeacherInfoVo getTeacherInfo(Long teacherId) {
        // 检查传入的教师ID是否为空
        if (teacherId != null) {
            // 根据教师ID查询教师信息
            TeacherInfoVo vo = mapper.getTeacherInfoById(teacherId);
            // 检查查询结果是否为空以及班级列表是否为空
            if (vo != null && vo.getClassList() != null) {
                // 使用Stream API处理班级列表，组合年级和班级名称
                vo.setClassList(vo.getClassList().stream().map(item -> {
                    // 设置班级名称为年级和班级名称的组合
                    item.setClassName(item.getGrade() + "年级" + item.getClassName());
                    return item;
                }).collect(Collectors.toList()));
                // 打印处理后的教师信息对象
                System.out.println(vo);
            }
            // 返回处理后的教师信息对象
            return vo;
        }
        // 如果教师ID为空，则返回null
        return null;
    }

    /**
     * 获取表单对象，包含年级列表和职位列表。
     * @return 表单对象，包含年级列表和职位列表。
     */
    @Override
    public FormVo getFormObject() {
        // 查询年级列表
        List<Integer> gradeList = mapper.getGradeList();
        // 查询职位列表
        List<String> positionList = mapper.getPositionList();
        // 创建表单对象
        FormVo vo = new FormVo();
        // 设置年级列表
        vo.setGradeList(gradeList);
        // 设置职位列表
        vo.setPositionList(positionList);
        // 返回表单对象
        return vo;
    }

    @Override
    public List<ClassInfoVo> getClassInfo() {
        List<ClassInfoVo> classInfo = mapper.getClassInfo();
        classInfo = classInfo.stream().map(item -> {
            item.setClassName(item.getGrade() + "年级" + item.getClassName());
            return item;
        }).collect(Collectors.toList());
        return classInfo;
    }
}
