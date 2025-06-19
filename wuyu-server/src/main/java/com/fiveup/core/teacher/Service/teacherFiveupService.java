package com.fiveup.core.teacher.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiveup.core.management.pojo.PageDto;
import com.fiveup.core.management.pojo.PageDto1;
import com.fiveup.core.management.pojo.Teacher;
import com.fiveup.core.teacher.entity.TeacherList;
import com.fiveup.core.teacher.entity.teacher;
import com.fiveup.core.teacher.mapper.teacherFiveupMapper;
import com.fiveup.core.teacher.mapper.JumpingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Service
public class teacherFiveupService extends ServiceImpl<teacherFiveupMapper, teacher> {
    @Autowired
    private JumpingMapper mapper;
    @Autowired
    private teacherFiveupMapper teacherMapper;


    /**
     * 保存教师信息，若ID存在则更新，否则新增
     *
     * @param teacher 教师对象
     * @return 操作是否成功
     */
    public boolean saveUser(teacher teacher) {
        // 声明方法返回结果变量，初始化为失败状态
        boolean operationResult = false;

        // 声明用于存储数据库操作结果的变量
        int databaseOperationResult = 0;

        // 声明用于标识ID和手机号是否存在的标志变量
        boolean isTeacherIdAlreadyExists = false;
        boolean isPhoneNumberAlreadyExists = false;

        // 声明用于存储数据库查询结果的临时变量
        teacher existingTeacherRecordById = null;
        teacher existingTeacherRecordByPhone = null;

        // 声明查询条件包装器对象
        LambdaQueryWrapper<teacher> phoneNumberQueryWrapper = null;

        // 校验输入参数是否为空
        if (teacher == null) {
            // 输入参数为空，抛出异常
            String errorMessage = "提供的教师对象不能为空";
            throw new IllegalArgumentException(errorMessage);
        }

        // 创建手机号查询条件包装器
        phoneNumberQueryWrapper = new LambdaQueryWrapper<>();

        // 设置手机号查询条件
        String targetPhoneNumber = teacher.getPhoneNum();
        phoneNumberQueryWrapper.eq(teacher::getPhoneNum, targetPhoneNumber);

        // 执行数据库查询，检查手机号是否存在
        existingTeacherRecordByPhone = teacherMapper.selectOne(phoneNumberQueryWrapper);

        // 判断查询结果，确定手机号是否已存在
        if (existingTeacherRecordByPhone != null) {
            isPhoneNumberAlreadyExists = true;
        } else {
            isPhoneNumberAlreadyExists = false;
        }

        // 如果手机号已存在，抛出异常
        if (isPhoneNumberAlreadyExists) {
            String errorMessage = "该手机号已存在，请更换其他手机号";
            throw new IllegalArgumentException(errorMessage);
        }

        // 获取待保存教师的ID
        Integer teacherId = teacher.getId();

        // 执行数据库查询，检查教师ID是否存在
        existingTeacherRecordById = teacherMapper.selectById(teacherId);

        // 判断查询结果，确定教师ID是否已存在
        if (existingTeacherRecordById != null) {
            isTeacherIdAlreadyExists = true;
        } else {
            isTeacherIdAlreadyExists = false;
        }

        // 根据教师ID是否存在执行不同的数据库操作
        if (isTeacherIdAlreadyExists) {
            // 教师ID存在，执行更新操作

            // 打印日志，记录即将执行的操作
            System.out.println("开始执行教师信息更新操作，教师ID: " + teacherId);

            // 执行更新操作
            databaseOperationResult = teacherMapper.updateById(teacher);

            // 判断更新操作结果
            if (databaseOperationResult > 0) {
                // 更新成功
                operationResult = true;
                System.out.println("教师信息更新成功，更新记录数: " + databaseOperationResult);
            } else {
                // 更新失败
                operationResult = false;
                System.out.println("教师信息更新失败，更新记录数: " + databaseOperationResult);
            }
        } else {
            // 教师ID不存在，执行插入操作

            // 打印日志，记录即将执行的操作
            System.out.println("开始执行教师信息插入操作，教师姓名: " + teacher.getUsername());

            // 执行插入操作
            databaseOperationResult = teacherMapper.insert(teacher);

            // 判断插入操作结果
            if (databaseOperationResult > 0) {
                // 插入成功
                operationResult = true;
                System.out.println("教师信息插入成功，插入记录数: " + databaseOperationResult);
            } else {
                // 插入失败
                operationResult = false;
                System.out.println("教师信息插入失败，插入记录数: " + databaseOperationResult);
            }
        }

        // 返回操作结果
        return operationResult;
    }

    /**
     * 检查指定教师对象的手机号是否已存在于数据库中
     *
     * @param teacher 待检查的教师对象
     * @return 手机号是否存在
     */
    private boolean checkIfPhoneNumberExists(teacher teacher) {
        // 声明查询条件包装器
        LambdaQueryWrapper<teacher> queryWrapper = null;

        // 声明查询结果变量
        teacher queryResult = null;

        // 声明结果标志变量
        boolean resultFlag = false;

        // 创建查询条件包装器
        queryWrapper = new LambdaQueryWrapper<>();

        // 获取待检查的手机号
        String phoneNumberToCheck = teacher.getPhoneNum();

        // 设置查询条件
        queryWrapper.eq(teacher::getPhoneNum, phoneNumberToCheck);

        // 执行数据库查询
        queryResult = teacherMapper.selectOne(queryWrapper);

        // 判断查询结果
        if (queryResult != null) {
            resultFlag = true;
        } else {
            resultFlag = false;
        }

        // 返回结果
        return resultFlag;
    }

    /**
     * 检查指定教师对象的ID是否已经存在于数据库中
     *
     * @param teacher 待检查的教师对象
     * @return 教师ID是否存在
     */
    private boolean checkIfTeacherIdExists(teacher teacher) {
        // 声明查询结果变量
        teacher queryResult = null;

        // 声明结果标志变量
        boolean resultFlag = false;

        // 获取待检查的教师ID
        Integer teacherIdToCheck = teacher.getId();

        // 执行数据库查询
        queryResult = teacherMapper.selectById(teacherIdToCheck);

        // 判断查询结果
        if (queryResult != null) {
            resultFlag = true;
        } else {
            resultFlag = false;
        }

        // 返回结果
        return resultFlag;
    }


    public teacher searchTeacherById(String id) {
        return teacherMapper.selectById(id);
    }

    public TeacherList getTeacherByPage(PageDto1 dto, long schoolId) {
        //如果页码异常
        if (dto.getPageNum() <= 0) {
            dto.setPageNum(1);
        }
        if (dto.getPageSize() <= 0) {
            dto.setPageSize(1);
        }

        List<Long> classList = new ArrayList<>();
//        //判断是否有年级模糊查询
//        if (dto.getGrade()!=null&&!dto.getGrade().trim().equals("")){
//            classList = mapper.getClassByGrade(dto.getGrade());//如果有,获取该年级的所有的班级信息
//            System.out.println("输出list:"+classList);
//        }

        TeacherList vo = new TeacherList();
        int pageNum = dto.getPageNum();//当前请求页码

        int pageSize = dto.getPageSize();
        //查询所有数据
        //如果有年级查询,给定该年级的所有班级,判断该年级的老师有哪些,同时只会查询出老师该年级所教的班级,而不会查出其它年级的班级
        List<List<?>> list = new ArrayList<>();
        List<teacher> teacherVos = mapper.getTeacherByPage(schoolId, dto, classList);
        list.add(teacherVos);
        List<Integer> teacherCount = mapper.getTotalTeacherCount();
        list.add(teacherCount);
        List<teacher> temp = (List<teacher>) list.get(0);//所有符合条件的记录

        Map<Integer, teacher> map = new TreeMap<>();
        for (teacher item : temp) {
            map.put(item.getId(), item);
        }
        temp = new ArrayList<>(map.values());
        //去重,因为班级存在,可能存在多个相同教师但不同班级的记录
//        temp=temp.stream().collect(Collectors.collectingAndThen(
//                Collectors.toCollection(() -> new TreeSet<>(//先收集起来然后比较id,最后组成一个新的数组
//                        Comparator.comparing(o -> o.getId())
//                )), ArrayList::new));

        List<teacher> result = new ArrayList<>();
        int total = temp.size();//总记录条数,从过滤后的数组中获得,否则分页显示异常
        vo.setTotal(total);
        //判断可以分多少页
        int sum = total / pageSize;
        //设置总页数
        if (sum >= vo.getPages())
            vo.setPages(vo.getTotal() % pageSize != 0 ? sum + 1 : sum);

        //获取该页显示的数据
        if (pageNum <= vo.getPages() && total != 0) {
            for (int i = (pageNum - 1) * pageSize; i < temp.size() && i < pageNum * pageSize; i++) {

                result.add(temp.get(i));
            }
            //设置当前页码
            vo.setCurPage(pageNum);
        }
        //如果页码超出总页数,可能进行了模糊查询
        if (pageNum > vo.getPages() && total != 0) {
            pageNum = 1;//设置为第一页
            vo.setCurPage(pageNum);//并且需要重新进行查询
            for (int i = 0; i < temp.size() && i < pageNum * pageSize; i++) {
                result.add(temp.get(i));
            }

        }
        //如果没有数据
        if (total == 0) {
            System.out.println(result);
            vo.setCurPage(1);
            vo.setPages(1);
        }
        //设置搜索结果

        vo.setList(result);

        //设置页面大小
        vo.setPageSize(pageSize);

        //判断是否为最后一页
        if (vo.getPages() == vo.getCurPage()) {
            vo.setLast(true);
        } else {
            vo.setLast(false);
        }
        return vo;
    }
}
