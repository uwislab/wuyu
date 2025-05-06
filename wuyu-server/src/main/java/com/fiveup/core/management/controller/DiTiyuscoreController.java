package com.fiveup.core.management.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.PageResult;
import com.fiveup.core.management.model.Req.TiyuScoreReq;
import com.fiveup.core.management.model.Resp.DiTiyuscoreResp;
import com.fiveup.core.management.pojo.TeacherInfoVo;
import com.fiveup.core.management.service.DitiyuScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XXXXX
 * @since 2024-11-25
 */
@RestController
@RequestMapping("/api/di-tiyuscore")
@Api("ssssss")
public class DiTiyuscoreController {


    @Autowired
    private DitiyuScoreService ditiyuScoreService;

    @PostMapping("/pages")
    @ApiOperation("获取数据")
    public PageResult<DiTiyuscoreResp> getTiYuScore(@RequestBody TiyuScoreReq tiyuScoreReq) {
        return ditiyuScoreService.pages(tiyuScoreReq);
    }


    // 导入数据
    @PostMapping("/import")
    @ApiOperation("导入数据")
    public CommonResponse importData(@RequestParam("file") MultipartFile file) {
            ditiyuScoreService.importData(file);
        return CommonResponse.ok();
    }


    // 导出数据
    @RequestMapping("/export")
    @ApiOperation("导出数据")
    public CommonResponse exportData(HttpServletResponse response) throws IOException {
        ditiyuScoreService.export(response);
        return CommonResponse.ok();
    }

    // 下载模板
    @RequestMapping("/export-template")
    @ApiOperation("导出数据")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        ditiyuScoreService.exportTemplate(response);
    }

    // 导出数据
    @GetMapping("/class-list/{gradeId}")
    @ApiOperation("导出数据")
    public CommonResponse classList(@PathVariable("gradeId") long gradeId) throws IOException {

        return CommonResponse.ok( ditiyuScoreService.classList(gradeId));
    }

    // 导出数据
    @RequestMapping("/grade-list")
    @ApiOperation("导出数据")
    public CommonResponse gradeList(HttpServletResponse response) throws IOException {
        return CommonResponse.ok( ditiyuScoreService.gradeList(response));
    }




}

