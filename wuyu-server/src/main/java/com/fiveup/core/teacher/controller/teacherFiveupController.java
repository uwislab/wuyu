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

@RestController
@RequestMapping("/teacher")
public class teacherFiveupController {
    private static final Logger logger = LoggerFactory.getLogger(teacherFiveupController.class);

    @Autowired
    private teacherFiveupService teacherService;
    @Resource
    private CommonManagementService commonManagementService;
    /**
     * 分页查询教师列表
     * @param pageDto 分页查询参数
     * @return 教师列表数据
     */
    @PostMapping("/getTeacherByPage")
    public CommonResponse<TeacherList> getTeacherByPage( PageDto1 pageDto) {
        // 参数校验
        if (pageDto == null) {
            logger.error("查询参数不能为空");
            return CommonResponse.fail(BizErrorCodeEnum.PARAMS_VALIDATION_ERRNO,"查询参数不能为空");
        }

        // 获取学校ID
        Long schoolId = pageDto.getSchoolId();
        if (schoolId == null || schoolId <= 0) {
            logger.error("学校ID不能为空或无效: {}", schoolId);
            return CommonResponse.fail(BizErrorCodeEnum.PARAMS_VALIDATION_ERRNO,"学校ID不能为空或无效");
        }

        // 调用服务层获取数据
        TeacherList teacherList = teacherService.getTeacherByPage(pageDto, schoolId);
        return CommonResponse.ok(teacherList);
    }
    @GetMapping("/searchTeacherById")
    public teacher searchTeacherById(@RequestParam("id") String id) {
        return teacherService.searchTeacherById(id);
    }
    @PostMapping
    //用户新增
    public  boolean save(@RequestBody teacher teacher){
        //新增或者更新
        return teacherService.saveUser(teacher);
    }

    @GetMapping
    public List<teacher> findAll(){
        return teacherService.list();
    }
    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> delete(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                logger.error("删除教师失败：无效的教师ID: {}", id);
                return CommonResponse.fail(BizErrorCodeEnum.PARAMS_VALIDATION_ERRNO, "无效的教师ID");
            }
            
            // 检查教师是否存在
            teacher teacher = teacherService.getById(id);
            if (teacher == null) {
                logger.error("删除教师失败：教师不存在，ID: {}", id);
                return CommonResponse.fail(BizErrorCodeEnum.ALARM_RECORD_NOT_EXIST, "教师不存在");
            }
            
            // 执行逻辑删除
            boolean result = teacherService.removeById(id);
            if (result) {
                logger.info("教师删除成功，ID: {}", id);
                return CommonResponse.ok(true);
            } else {
                logger.error("教师删除失败，ID: {}", id);
                return CommonResponse.fail(BizErrorCodeEnum.ROLLBACK_PLAN_ERRNO, "删除失败");
            }
        } catch (Exception e) {
            logger.error("删除教师时发生异常，ID: {}, 异常信息: {}", id, e.getMessage(), e);
            return CommonResponse.fail(BizErrorCodeEnum.ROLLBACK_PLAN_ERRNO, "系统异常");
        }
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return teacherService.removeByIds(ids);
    }

    @GetMapping("/page") //接口路径user/page
    public IPage<teacher> findPage(@RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam(value = "newsType",defaultValue = "") String   newsType, //value--String--实体类
                                   @RequestParam(value = "title",defaultValue = "") String title,
                                   @RequestParam(value = "content",defaultValue = "") String     content){
        IPage<teacher> page=new Page<>(pageNum,pageSize);
        QueryWrapper<teacher> queryWrapper=new QueryWrapper<>();
        if(!"".equals(newsType)){
            queryWrapper.like("newsType",newsType);
        }
        if(!"".equals(title)){
            queryWrapper.like("title",title);
        }
        if(!"".equals(content)){
            queryWrapper.like("content",content);
        }
        return  teacherService.page(page,queryWrapper);
    }


   //修改教师信息
    @PostMapping("updateTeacher")
    public Result updateTeacherInfo(@RequestBody teacher teacherInfoParam) {
        Result result = teacherService.updateTeacherInfo(teacherInfoParam);
        return result;
    }


    //excel导出
    @GetMapping("/exportExcel")
    public void exportExcel(@RequestParam("schoolId") Long schoolId, HttpServletResponse response) throws Exception {
        // 构建查询条件
        QueryWrapper<teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id", schoolId)
                   .eq("deleted", 0);  // 只查询未删除的数据

        // 查询符合条件的数据
        List<teacher> list = teacherService.list(queryWrapper);

        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 自定义标题别名（使用中文表头）
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

        // 设置只导出这些列，排除deleted列
        writer.setOnlyAlias(true);

        // 一次性写出list内的对象到excel，使用默认格式，强制输出标题
        writer.write(list, true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream,true);

        //关闭流
        outputStream.close();
        writer.close();
    }

    //excel导入
    @PostMapping("/importExcel")
    public CommonResponse<Boolean> importExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        //方式1：通过JavaBean的方式读取excel内的对象，但是要求表头必须市英文，和JavaBean属性对应
//        List<User> users = reader.readAll(User.class);

        //方式二：忽略表头中文，直接获取表格数据
        List<List<Object>> list = reader.read(1);
        List<teacher> users = CollUtil.newArrayList();

        for(List<Object> row:list){
            teacher teacher =new teacher();
//            user.setNewsType(row.get(1).toString());
//            user.setTitle(row.get(2).toString());
//            user.setContent(row.get(3).toString());
//            user.setAuthor(row.get(4).toString());
            teacher.setTeacherName(row.get(1).toString());
            teacher.setGender(Integer.parseInt(row.get(2).toString()));
            teacher.setPhoneNum(row.get(3).toString());
            teacher.setPosition(row.get(4).toString());
            teacher.setTitle(row.get(5).toString());
            teacher.setRole(row.get(6).toString());
            teacher.setUsername(row.get(7).toString());
            teacher.setPassword(row.get(8).toString());
            teacher.setPoliticalAppearance(row.get(9).toString());
            teacher.setBirthPlace(row.get(10).toString());
            teacher.setAge(Integer.parseInt(row.get(11).toString()));
            teacher.setInfo(row.get(12).toString());

            users.add(teacher);
        }
        //将excel导入的数据保存到数据库
        teacherService.saveBatch(users);
        return CommonResponse.ok(true);
    }

    /**
     * 下载教师信息导入模板
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        // 从classpath中读取模板文件
        ClassPathResource resource = new ClassPathResource("templates/教师信息下载模版.xlsx");

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息导入模板", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        // 将模板文件写入响应流
        try (InputStream inputStream = resource.getInputStream();
             ServletOutputStream outputStream = response.getOutputStream()) {
            StreamUtils.copy(inputStream, outputStream);
        }
    }

}