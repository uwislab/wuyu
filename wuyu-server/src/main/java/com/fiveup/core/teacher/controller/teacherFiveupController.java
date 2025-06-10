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
import com.fiveup.core.management.pojo.PageDto1;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.teacher.Service.teacherFiveupService;
import com.fiveup.core.teacher.entity.TeacherList;
import com.fiveup.core.teacher.entity.teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class teacherFiveupController {
    @Autowired
    private teacherFiveupService teacherService;
    @Resource
    private CommonManagementService commonManagementService;
    @PostMapping("/getTeacherByPage")
    public CommonResponse<TeacherList> getTeacherByPage(PageDto1 search) {
        System.out.println("search = " + search);
        long schoolId = commonManagementService.getSchoolId();
        System.out.println("schoolId = " + schoolId);
        TeacherList vo = teacherService.getTeacherByPage(search,schoolId);
        return CommonResponse.ok(vo);
    }
    @GetMapping("/searchTeacherById")
    public teacher searchTeacherById(@RequestParam("id") String id) {
        return teacherService.searchTeacherById(id);
    }
    @PostMapping("/save")
    public  boolean save(@RequestBody teacher teacher){
        //新增或者更新
        return teacherService.saveUser(teacher);
    }

    @GetMapping
    public List<teacher> findAll(){
        return teacherService.list();
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return teacherService.removeById(id);
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




    //excel导出
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        //查询所有数据
        List<teacher> list = teacherService.list();
        //在内存操作，写出到浏览器，从浏览器下载
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("newsType", "名字");
        writer.addHeaderAlias("title", "密码");
        writer.addHeaderAlias("content", "昵称");
        writer.addHeaderAlias("author", "邮箱");
        //一次性写出list内的对象到excel，使用默认格式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream,true);

        //关闭流
        outputStream.close();
        writer.close();
    }

    //excel导入
    @PostMapping("/importExcel")
    public Boolean importExcel(MultipartFile file) throws IOException {
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

            users.add(teacher);
        }
        //将excel导入的数据保存到数据库
        teacherService.saveBatch(users);
        return true;
    }




}
