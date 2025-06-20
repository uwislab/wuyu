package com.fiveup.core.teacherworkspace.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fiveup.core.common.api.CommonResult;
import com.fiveup.core.common.exception.Asserts;
import com.fiveup.core.teacherworkspace.common.utils.ImportExportUtils;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.bo.LessonExcelBo;
import com.fiveup.core.teacherworkspace.model.bo.LessonFailExportBo;
import com.fiveup.core.teacherworkspace.model.dto.PageLessonDto;
import com.fiveup.core.teacherworkspace.model.vo.ExcelImportVo;
import com.fiveup.core.teacherworkspace.service.LessonExcelService;
import com.fiveup.core.teacherworkspace.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lesson/excel")
@Slf4j
@RequiredArgsConstructor
@Api(value = "排课Excel导入导出管理")
public class LessonExcelController {
    private final LessonExcelService lessonExcelService;
    private final LessonService lessonService;

    /**
     * 批量导入用户信息
     *
     * @param file 导入文件
     * @return 导入数据校验后的结果
     */
    @ApiOperation("批量导入课程信息")
    @ApiImplicitParam(paramType = "form", name = "file", value = "文件对象", required = true, dataType = "file")
    @PostMapping(value = "/try-import")
    public CommonResult<ExcelImportVo<LessonExcelBo, LessonFailExportBo>> userBatchImport(@RequestPart("file") MultipartFile file) throws IOException {
        //excel表获取所有想要导入数据
        List<LessonExcelBo> list = ImportExportUtils.importExcel(file, LessonExcelBo.class);
        log.info("导入数据：{}", list);
        //校验导入数据合法性
        ExcelImportVo<LessonExcelBo, LessonFailExportBo> result = lessonExcelService.tryImport(list);
        return CommonResult.success(result);
    }

    /**
     * 确认导入校验成功的数据
     *
     * @param id 校验成功数据标识码
     * @return 导入结果
     */
    @ApiOperation("确认导入校验成功的数据")
    @PostMapping(value = "/import/{id}")
    public CommonResult<Object> confirmImport(@PathVariable String id) {
        log.info("确认导入校验成功的数据：{}", id);
        lessonExcelService.confirmImport(id);
        return CommonResult.success();
    }


    /**
     * 导出导入失败的数据
     *
     * @param id       校验失败数据标识码
     * @param response 响应
     */
    @ApiOperation("导出导入失败的数据")
    @GetMapping(value = "/export/fail/{id}")
    public void exportFail(@PathVariable String id, HttpServletResponse response) throws IOException {
        List<LessonFailExportBo> list = lessonExcelService.getFailExportList(id);
        ImportExportUtils.exportExcel(list, LessonFailExportBo.class, response, true);
    }

    /**
     * 导出数据
     *
     * @param dto 筛选条件
     */
    @ApiOperation("导出排课数据")
    @GetMapping("/export")
    public void export(HttpServletResponse response, PageLessonDto dto) throws IOException {
        List<Lesson> records = lessonService.pageLesson(dto).getRecords();
        if (CollectionUtils.isEmpty(records)) {
            Asserts.fail("无导出数据");
        }
        List<LessonExcelBo> list = records.stream()
                .map(lesson -> BeanUtil.copyProperties(lesson, LessonExcelBo.class))
                .collect(Collectors.toList());
        ImportExportUtils.exportExcel(list, LessonExcelBo.class, response, false);
    }

    /**
     * 导出模版
     */
    @ApiOperation("导出模版")
    @GetMapping("/export-template")
    public void export(HttpServletResponse response) throws IOException {
        ImportExportUtils.exportTemplate(LessonExcelBo.class, response, false);
    }
}
