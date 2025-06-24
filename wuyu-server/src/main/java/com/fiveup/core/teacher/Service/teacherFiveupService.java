package com.fiveup.core.teacher.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiveup.core.fuScale.develop_09.common.R;
import com.fiveup.core.management.pojo.PageDto;
import com.fiveup.core.teacher.pojo.PageDto1;
import com.fiveup.core.teacher.entity.TeacherList;
import com.fiveup.core.teacher.entity.teacher;
import com.fiveup.core.teacher.mapper.teacherFiveupMapper;
import com.fiveup.core.teacher.mapper.JumpingMapper;
import com.fiveup.core.teacher.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
        // 校验并修正分页参数
        validateAndFixPageParams(dto);

        TeacherList vo = new TeacherList();
        int pageNum = dto.getPageNum();
        int pageSize = dto.getPageSize();

        // 查询数据并处理结果
        List<teacher> teacherList = getFilteredTeacherList(schoolId, dto);
        int total = teacherList.size();

        // 设置分页相关参数
        setPageInfo(vo, total, pageNum, pageSize);

        // 获取当前页数据
        List<teacher> pageData = getPageData(teacherList, vo.getCurPage(), pageSize);
        vo.setList(pageData);

        // 设置其他参数
        vo.setPageSize(pageSize);
        vo.setLast(vo.getPages() == vo.getCurPage());

        return vo;
    }

    private void validateAndFixPageParams(PageDto1 dto) {
        if (dto.getPageNum() <= 0) {
            dto.setPageNum(1);
        }
        if (dto.getPageSize() <= 0) {
            dto.setPageSize(1);
        }
    }

    private List<teacher> getFilteredTeacherList(long schoolId, PageDto1 dto) {
        List<teacher> teacherVos = mapper.getTeacherByPage(schoolId, dto);
        mapper.getTotalTeacherCount(); // 获取总数但未使用结果

        // 使用TreeMap去重并排序
        Map<Integer, teacher> map = new TreeMap<>();
        for (teacher item : teacherVos) {
            map.put(item.getId(), item);
        }
        return new ArrayList<>(map.values());
    }

    private void setPageInfo(TeacherList vo, int total, int pageNum, int pageSize) {
        vo.setTotal(total);

        int totalPages = calculateTotalPages(total, pageSize);
        vo.setPages(totalPages);

        // 设置当前页码
        if (total == 0) {
            vo.setCurPage(1);
            vo.setPages(1);
        } else if (pageNum > totalPages) {
            vo.setCurPage(1);
        } else {
            vo.setCurPage(pageNum);
        }
    }

    private int calculateTotalPages(int total, int pageSize) {
        return (total + pageSize - 1) / pageSize;
    }

    private List<teacher> getPageData(List<teacher> allData, int curPage, int pageSize) {
        if (allData.isEmpty()) {
            return new ArrayList<>();
        }

        int fromIndex = (curPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allData.size());

        return allData.subList(fromIndex, toIndex);
    }

    public Result updateTeacherInfo(teacher teacherInfoParam) {
        // 1. 参数校验
        if (teacherInfoParam == null || teacherInfoParam.getId() == 0) {
            return Result.fail(400, "参数错误，教师ID不能为空");
        }

        // 2. 检查教师是否存在
        teacher existingTeacher = teacherMapper.selectById(teacherInfoParam.getId());
        if (existingTeacher == null || existingTeacher.getDeleted() == 1) {
            return Result.fail(404, "教师不存在或已被删除");
        }

        // 3. 如果传入了手机号，检查是否已被其他教师使用
        if (StringUtils.isNotBlank(teacherInfoParam.getPhoneNum())) {
            log.debug("开始处理手机号更新，教师ID: {}"+teacherInfoParam.getId());

            String newPhone = teacherInfoParam.getPhoneNum().trim();
            String currentPhone = existingTeacher.getPhoneNum();
            log.debug("新手机号: {}, 当前手机号: {}"+ newPhone+ currentPhone);

            if (!newPhone.equals(currentPhone)) {
                log.debug("检测到手机号变更，开始验证新手机号");

                // 3.1 格式验证
                if (!isValidPhoneNumber(newPhone)) {
                    log.warn("手机号格式验证失败，无效的手机号: {}"+ newPhone);
                    return Result.fail(400, "手机号格式不正确");
                }
                log.debug("手机号格式验证通过: {}"+ newPhone);

                // 3.2 唯一性检查
                long teacherId = teacherInfoParam.getId();
                log.debug("开始检查手机号唯一性，排除教师ID: {}"+ teacherId);

                if (isPhoneNumberExists(newPhone, teacherId)) {
                    log.warn("手机号唯一性检查失败，手机号已被使用: {}"+ newPhone);
                    return Result.fail(501, "手机号已被其他教师使用");
                }
                log.debug("手机号唯一性检查通过: {}"+ newPhone);
            } else {
                log.debug("手机号未变更，跳过验证");
            }
        } else {
            log.debug("未提供新手机号，跳过手机号验证");
        }
        // 3. 构建更新对象（只更新允许修改的字段）
        teacher updateTeacher = new teacher();
        updateTeacher.setId(teacherInfoParam.getId());

        if (teacherInfoParam.getTeacherName() != null) {
            updateTeacher.setTeacherName(teacherInfoParam.getTeacherName());
        }
        if (teacherInfoParam.getGender() != 0) {
            updateTeacher.setGender(teacherInfoParam.getGender());
        }
        if (teacherInfoParam.getPhoneNum() != null) {
            updateTeacher.setPhoneNum(teacherInfoParam.getPhoneNum());
        }
        if (teacherInfoParam.getPosition() != null) {
            updateTeacher.setPosition(teacherInfoParam.getPosition());
        }
        if (teacherInfoParam.getTitle() != null) {
            updateTeacher.setTitle(teacherInfoParam.getTitle());
        }
        if (teacherInfoParam.getPoliticalAppearance() != null) {
            updateTeacher.setPoliticalAppearance(teacherInfoParam.getPoliticalAppearance());
        }
        if (teacherInfoParam.getBirthPlace() != null) {
            updateTeacher.setBirthPlace(teacherInfoParam.getBirthPlace());
        }
        if (teacherInfoParam.getAge() != 0) {
            updateTeacher.setAge(teacherInfoParam.getAge());
        }
        if (teacherInfoParam.getInfo() != null) {
            updateTeacher.setInfo(teacherInfoParam.getInfo());
        }
            updateTeacher.setSchoolId(teacherInfoParam.getSchoolId());


        // 4. 执行更新
        int result = teacherMapper.updateById(updateTeacher);
        if (result <= 0) {
            return Result.fail(500, "更新教师信息失败");
        }

        // 5. 返回成功响应
        return Result.success("教师信息更新成功");
    }
    private boolean isValidPhoneNumber(String phoneNum) {
        // 实现手机号格式验证逻辑
        return phoneNum != null && phoneNum.matches("^1[3-9]\\d{9}$");
    }

    private boolean isPhoneNumberExists(String phoneNum, Long excludeId) {
        return teacherMapper.exists(
                Wrappers.<teacher>lambdaQuery()
                        .eq(teacher::getPhoneNum, phoneNum)
                        .ne(excludeId != null, teacher::getId, excludeId)
        );
    }
}
