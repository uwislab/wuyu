package com.fiveup.core.teacher.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.pojo.PageDto;
import com.fiveup.core.teacher.pojo.PageDto1;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.teacher.Service.teacherFiveupService;
import com.fiveup.core.teacher.entity.TeacherList;
import com.fiveup.core.teacher.entity.teacher;
import com.fiveup.core.management.common.enums.BizErrorCodeEnum;
import com.fiveup.core.teacher.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 教师信息管理控制器
 * 提供教师信息的增删改查、分页查询、Excel导入导出等功能
 * 该控制器处理的URL路径前缀为 "/teacher"
 */
@RestController
@RequestMapping("/teacher")
public class teacherFiveupController {
    // 日志记录器，用于记录重要操作和错误信息
    private static final Logger logger = LoggerFactory.getLogger(teacherFiveupController.class);

    // 注入教师业务逻辑处理服务
    @Autowired
    private teacherFiveupService teacherService;

    // 注入通用管理服务
    @Resource
    private CommonManagementService commonManagementService;

    /**
     * 分页查询教师列表
     *
     * @param pageDto 分页查询参数，包含页码、每页数量、学校ID等信息
     * @return 封装了教师列表数据的通用响应对象
     */
    @PostMapping("/getTeacherByPage")
    public CommonResponse<TeacherList> getTeacherByPage(PageDto1 pageDto) {
        // 参数校验：确保请求参数有效
        if (pageDto == null) {
            logger.error("查询参数不能为空");
            return CommonResponse.fail(BizErrorCodeEnum.PARAMS_VALIDATION_ERRNO, "查询参数不能为空");
        }

        // 获取并校验学校ID：学校ID是查询教师的必要条件
        Long schoolId = pageDto.getSchoolId();
        if (schoolId == null || schoolId <= 0) {
            logger.error("学校ID不能为空或无效: {}", schoolId);
            return CommonResponse.fail(BizErrorCodeEnum.PARAMS_VALIDATION_ERRNO, "学校ID不能为空或无效");
        }

        // 调用服务层获取分页数据：将业务逻辑委托给Service处理
        TeacherList teacherList = teacherService.getTeacherByPage(pageDto, schoolId);
        return CommonResponse.ok(teacherList);
    }

    /**
     * 根据教师ID查询教师信息
     *
     * @param id 教师ID
     * @return 教师实体对象
     */
    @GetMapping("/searchTeacherById")
    public teacher searchTeacherById(@RequestParam("id") String id) {
        // 直接调用Service层方法获取教师信息
        return teacherService.searchTeacherById(id);
    }

    /**
     * 新增或更新教师信息
     *
     * @param teacher 教师实体对象
     * @return 操作结果，成功返回true，失败返回false
     */
    @PostMapping
    public boolean save(@RequestBody teacher teacher) {
        // 新增或更新教师信息：根据ID是否存在决定操作类型
        return teacherService.saveUser(teacher);
    }

    /**
     * 查询所有教师信息
     *
     * @return 教师列表
     */
    @GetMapping
    public List<teacher> findAll() {
        // 获取所有教师信息列表
        return teacherService.list();
    }

    /**
     * 根据ID删除教师信息
     *
     * @param id 教师ID
     * @return 操作结果，成功返回true，失败返回false
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        // 根据ID删除教师信息
        return teacherService.removeById(id);
    }

    /**
     * 批量删除教师信息
     *
     * @param ids 教师ID列表
     * @return 操作结果，成功返回true，失败返回false
     */
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        // 批量删除教师信息
        return teacherService.removeByIds(ids);
    }

    /**
     * 带条件的分页查询教师信息
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param newsType 新闻类型（可选）
     * @param title    标题（可选）
     * @param content  内容（可选）
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public IPage<teacher> findPage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "newsType", defaultValue = "") String newsType,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "content", defaultValue = "") String content) {

        // 创建分页对象
        IPage<teacher> page = new Page<>(pageNum, pageSize);

        // 构建查询条件
        QueryWrapper<teacher> queryWrapper = new QueryWrapper<>();
        if (!"".equals(newsType)) {
            queryWrapper.like("newsType", newsType);
        }
        if (!"".equals(title)) {
            queryWrapper.like("title", title);
        }
        if (!"".equals(content)) {
            queryWrapper.like("content", content);
        }

        // 执行分页查询
        return teacherService.page(page, queryWrapper);
    }

    /**
     * 修改教师信息
     *
     * @param teacherInfoParam 包含教师信息的参数对象
     * @return 操作结果封装对象
     */
    @PostMapping("updateTeacher")
    public Result updateTeacherInfo(@RequestBody teacher teacherInfoParam) {
        // 调用Service层的更新方法
        Result result = teacherService.updateTeacherInfo(teacherInfoParam);
        return result;
    }

    /**
     * 导出教师信息到Excel文件
     *
     * @param schoolId  学校ID
     * @param response  HTTP响应对象，用于输出Excel文件
     * @throws Exception 可能的异常，如IO异常、编码异常等
     */
    @GetMapping("/exportExcel")
    public void exportExcel(@RequestParam("schoolId") Long schoolId, HttpServletResponse response) throws Exception {
        // 构建查询条件：只查询指定学校且未删除的教师信息
        QueryWrapper<teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id", schoolId)
                .eq("deleted", 0);  // 只查询未删除的数据

        // 查询符合条件的数据
        List<teacher> list = teacherService.list(queryWrapper);

        // 创建Excel写入器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 设置Excel表头别名：使用中文表头提高可读性
        writer.addHeaderAlias("id", "教师ID");
        writer.addHeaderAlias("teacherName", "教师姓名");
        writer.addHeaderAlias("gender", "性别");
        writer.addHeaderAlias("phoneNum", "手机号码");
        writer.addHeaderAlias("position", "职位");
        writer.addHeaderAlias("title", "职称");
        writer.addHeaderAlias("role", "角色");
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("politicalAppearance", "政治面貌");
        writer.addHeaderAlias("birthPlace", "籍贯");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("info", "备注信息");

        // 设置只导出别名列，排除其他列
        writer.setOnlyAlias(true);

        // 写入数据到Excel
        writer.write(list, true);

        // 设置HTTP响应头，告诉浏览器这是一个Excel文件
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        // 输出Excel数据到响应流
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);

        // 关闭资源
        outputStream.close();
        writer.close();
    }

    /**
     * 从Excel文件导入教师信息
     *
     * @param file 上传的Excel文件
     * @return 导入结果，成功返回true
     * @throws IOException 可能的IO异常
     */
    @PostMapping("/importExcel")
    public Boolean importExcel(MultipartFile file) throws IOException {
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        // 读取Excel数据：方式二：忽略表头中文，直接获取表格数据
        List<List<Object>> list = reader.read(1);
        List<teacher> users = CollUtil.newArrayList();

        // 解析Excel数据并转换为教师对象
        for (List<Object> row : list) {
            teacher teacher = new teacher();
            // TODO: 根据实际Excel列结构映射数据
            // user.setNewsType(row.get(1).toString());
            // user.setTitle(row.get(2).toString());
            // user.setContent(row.get(3).toString());
            // user.setAuthor(row.get(4).toString());

            users.add(teacher);
        }

        // 批量保存到数据库
        teacherService.saveBatch(users);
        return true;
    }

    /**
     * 下载教师信息导入模板
     *
     * @param response HTTP响应对象，用于输出模板文件
     * @throws IOException 可能的IO异常
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        // 从classpath中读取模板文件
        ClassPathResource resource = new ClassPathResource("templates/教师信息下载模版.xlsx");

        // 设置响应头：告诉浏览器这是一个Excel文件
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息导入模板", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        // 将模板文件内容写入响应流
        try (InputStream inputStream = resource.getInputStream();
             ServletOutputStream outputStream = response.getOutputStream()) {
            StreamUtils.copy(inputStream, outputStream);
        }
    }
}