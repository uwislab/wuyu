package com.fiveup.core.teacherworkspace.service;

import com.fiveup.core.teacherworkspace.model.bo.LessonExcelBo;
import com.fiveup.core.teacherworkspace.model.bo.LessonFailExportBo;
import com.fiveup.core.teacherworkspace.model.vo.ExcelImportVo;

import java.util.List;

public interface LessonExcelService {
    /**
     * 尝试导入排课信息
     * @param list 排课Excel列表信息
     * @return 尝试导入排课结果
     */
    ExcelImportVo<LessonExcelBo, LessonFailExportBo> tryImport(List<LessonExcelBo> list);

    /**
     * 确认导入正确排课信息
     * @param id 缓存id
     */
    void confirmImport(String id);

    /**
     * 获取失败导入数据
     * @param id 缓存id
     * @return 导入失败数据
     */
    List<LessonFailExportBo> getFailExportList(String id);
}
