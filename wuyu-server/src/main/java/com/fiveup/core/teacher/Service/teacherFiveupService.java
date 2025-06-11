package com.fiveup.core.teacher.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiveup.core.management.pojo.PageDto;
import com.fiveup.core.teacher.pojo.PageDto1;
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
}
