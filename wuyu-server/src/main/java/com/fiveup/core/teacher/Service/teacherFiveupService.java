package com.fiveup.core.teacher.Service;
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

    public boolean saveUser(teacher teacher){
        return saveOrUpdate(teacher);
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

        // 4. 执行更新
        int result = teacherMapper.updateById(updateTeacher);
        if (result <= 0) {
            return Result.fail(500, "更新教师信息失败");
        }

        // 5. 返回成功响应
        return Result.success("教师信息更新成功");
    }
}
