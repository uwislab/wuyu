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
// 用户新增
    public boolean save(@RequestBody teacher teacher) {
        // 声明一个布尔类型的变量，用于存储操作结果
        boolean isOperationSuccessful;

        // 检查传入的teacher对象是否为null
        if (teacher != null) {
            // 如果teacher对象不为null，则继续执行保存操作
            // 调用teacherService的saveUser方法，尝试保存用户信息
            boolean saveResult = teacherService.saveUser(teacher);

            // 对保存结果进行判断
            if (saveResult) {
                // 如果保存成功，将操作结果设置为true
                isOperationSuccessful = true;
            } else {
                // 如果保存失败，将操作结果设置为false
                isOperationSuccessful = false;
            }
        } else {
            // 如果teacher对象为null，将操作结果直接设置为false
            isOperationSuccessful = false;
        }

        // 声明一个变量，用于存储最终返回的结果
        boolean finalResult;

        // 再次检查操作结果
        if (isOperationSuccessful) {
            // 如果操作成功，将最终结果设置为true
            finalResult = true;
        } else {
            // 如果操作失败，将最终结果设置为false
            finalResult = false;
        }

        // 返回最终结果
        return finalResult;
    }

    @GetMapping
    public List<teacher> findAll(){
        return teacherService.list();
    }

    //删除教师
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


    //批量删除

    @PostMapping("/del/batch")
    public CommonResponse<Boolean> deleteBatch(@RequestBody List<Integer> ids) {
        try {
            if (CollUtil.isEmpty(ids)) {
                logger.error("批量删除失败：教师ID列表为空");
                return CommonResponse.fail(BizErrorCodeEnum.PARAMS_VALIDATION_ERRNO, "教师ID列表不能为空");
            }

            for (Integer id : ids) {
                if (id == null || id <= 0) {
                    logger.error("批量删除失败：无效的教师ID: {}", id);
                    return CommonResponse.fail(BizErrorCodeEnum.PARAMS_VALIDATION_ERRNO, "无效的教师ID");
                }

                teacher teacher = teacherService.getById(id);
                if (teacher == null) {
                    logger.error("批量删除失败：教师不存在，ID: {}", id);
                    return CommonResponse.fail(BizErrorCodeEnum.ALARM_RECORD_NOT_EXIST, "教师不存在");
                }
            }

            // 执行批量删除操作
            boolean result = teacherService.removeByIds(ids);
            if (result) {
                logger.info("教师批量删除成功，IDs: {}", ids);
                return CommonResponse.ok(true);
            } else {
                logger.error("教师批量删除失败，IDs: {}", ids);
                return CommonResponse.fail(BizErrorCodeEnum.ROLLBACK_PLAN_ERRNO, "批量删除失败");
            }
        } catch (Exception e) {
            logger.error("批量删除教师时发生异常，异常信息: {}", e.getMessage(), e);
            return CommonResponse.fail(BizErrorCodeEnum.ROLLBACK_PLAN_ERRNO, "系统异常");
        }
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
    @PostMapping("/updateTeacher")
    public Result updateTeacherInfo(@RequestBody teacher teacherInfoParam) {
        Result result = teacherService.updateTeacherInfo(teacherInfoParam);
        return result;
    }


    //excel导出
    @GetMapping("/exportExcel")
    public void exportExcel(@RequestParam("schoolId") Long schoolId,
                            @RequestParam(value = "fields", required = false) String fields,
                            HttpServletResponse response) throws Exception {
        // 构建查询条件
        QueryWrapper<teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id", schoolId)
                   .eq("deleted", 0);  // 只查询未删除的数据
        // 查询符合条件的数据
        List<teacher> list = teacherService.list(queryWrapper);

        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 字段与中文表头映射
        java.util.Map<String, String> headerAlias = new java.util.HashMap<>();
        headerAlias.put("id", "教师ID");
        headerAlias.put("teacherName", "教师姓名");
        headerAlias.put("gender", "性别");
        headerAlias.put("phoneNum", "手机号码");
        headerAlias.put("position", "职位");
        headerAlias.put("title", "职称");
        headerAlias.put("role", "角色");
        headerAlias.put("username", "用户名");
        headerAlias.put("password", "密码");
        headerAlias.put("politicalAppearance", "政治面貌");
        headerAlias.put("birthPlace", "籍贯");
        headerAlias.put("age", "年龄");
        headerAlias.put("info", "备注信息");

        // 处理前端传递的fields参数
        java.util.List<String> exportFields;
        if (fields != null && !fields.trim().isEmpty()) {
            exportFields = java.util.Arrays.asList(fields.split(","));
        } else {
            // 默认导出全部字段
            exportFields = new java.util.ArrayList<>(headerAlias.keySet());
        }

        // 只设置前端选择的字段别名
        for (String field : exportFields) {
            if (headerAlias.containsKey(field)) {
                writer.addHeaderAlias(field, headerAlias.get(field));
            }
        }
        writer.setOnlyAlias(true);

        // 处理id和性别字段（id转字符串，性别int转中文）
        for (teacher t : list) {
            if (exportFields.contains("gender")) {
                try {
                    java.lang.reflect.Field genderField = t.getClass().getDeclaredField("gender");
                    genderField.setAccessible(true);
                    int genderValue;
                    Object genderObj = genderField.get(t);
                    if (genderObj instanceof Integer) {
                        genderValue = (Integer) genderObj;
                    } else if (genderObj instanceof String) {
                        // 已经是字符串则跳过
                        continue;
                    } else {
                        genderValue = 0;
                    }
                    String genderStr = "";
                    if (genderValue == 1) genderStr = "男";
                    else if (genderValue == 2) genderStr = "女";
                    else genderStr = "未知";
                    genderField.set(t, genderStr);
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        // 组装导出数据，保证id为字符串，且顺序与导出字段一致
        java.util.List<java.util.Map<String, Object>> exportList = new java.util.ArrayList<>();
        for (teacher t : list) {
            java.util.Map<String, Object> row = new java.util.LinkedHashMap<>();
            for (String field : exportFields) {
                try {
                    java.lang.reflect.Field f = t.getClass().getDeclaredField(field);
                    f.setAccessible(true);
                    Object value = f.get(t);
                    if (field.equals("id") && value != null) {
                        value = "\t" + value.toString(); // 加tab防止Excel科学计数法
                    }
                    row.put(field, value);
                } catch (Exception e) {
                    row.put(field, "");
                }
            }
            exportList.add(row);
        }

        // 一次性写出list内的对象到excel，使用默认格式，强制输出标题
        writer.write(exportList, true);

        // 设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);

        // 关闭流
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
            teacher.setSchoolId(1);
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