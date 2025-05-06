package com.fiveup.core.management.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.management.common.PageResult;
import com.fiveup.core.management.listener.TiyuscoreListener;
import com.fiveup.core.management.mapper.DiTiyuscoreMapper;
import com.fiveup.core.management.mapper.StuMapper;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.model.DiTiyuscore;
import com.fiveup.core.management.model.Req.TiyuScoreReq;
import com.fiveup.core.management.model.Resp.DiTiyuscoreResp;
import com.fiveup.core.management.model.excel.TiyuscoreExt;
import com.fiveup.core.management.repository.DiTiyuscoreRepository;
import com.fiveup.core.management.service.DitiyuScoreService;
import com.fiveup.core.questionnaire.entity.Class;
import com.fiveup.core.questionnaire.mapper.ClassMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author xxy
 * @version v1.0.1
 * @date 2024/11/25 23:20
 * @description:
 */

@Service
public class DitiyuScoreServiceImpl implements DitiyuScoreService {

    @Resource
    private DiTiyuscoreRepository diTiyuscoreRepository;
    @Resource
    private DiTiyuscoreMapper diTiyuscoreMapper;

    @Resource
    private StuMapper stuMapper;

    @Resource
    private ClassMapper classMapper;

    @Override
    public PageResult<DiTiyuscoreResp> pages(TiyuScoreReq tiyuScoreReq) {
        Integer start = (tiyuScoreReq.getPageNum() - 1) * tiyuScoreReq.getPageSize();

        long count = diTiyuscoreMapper.countNum(start, tiyuScoreReq.getPageSize(), tiyuScoreReq.getClassId(),
            tiyuScoreReq.getGradeId());
        List<DiTiyuscore> list = diTiyuscoreMapper.list(start, tiyuScoreReq.getPageSize(),tiyuScoreReq.getClassId(),
            tiyuScoreReq.getGradeId());

        PageResult<DiTiyuscoreResp> diTiyuscorePageResult = new PageResult<>();
        ArrayList<DiTiyuscoreResp> diTiyuscoreResps = new ArrayList<>();
        for (DiTiyuscore diTiyuscore : list) {
            DiTiyuscoreResp diTiyuscoreResp = new DiTiyuscoreResp();
            BeanUtils.copyProperties(diTiyuscore, diTiyuscoreResp);
            diTiyuscoreResps.add(diTiyuscoreResp);
        }

        diTiyuscorePageResult.setList(diTiyuscoreResps);
        diTiyuscorePageResult.setTotal(count);
        diTiyuscorePageResult.setPageSize(Long.valueOf(tiyuScoreReq.getPageSize()));
        diTiyuscorePageResult.setCurrentPage(Long.valueOf(tiyuScoreReq.getPageNum()));
        return diTiyuscorePageResult;
    }

    @Override
    public void importData(MultipartFile file) {
        if (file.isEmpty()) {
            return;
        }
        try {
            //获取文件的输入流
            InputStream inputStream = file.getInputStream();
            List<TiyuscoreExt> lst = EasyExcel.read(inputStream) //调用read方法
                //注册自定义监听器，字段校验可以在监听器内实现
                .registerReadListener(new TiyuscoreListener()).head(TiyuscoreExt.class) //对应导入的实体类
                .sheet(0) //导入数据的sheet页编号，0代表第一个sheet页，如果不填，则会导入所有sheet页的数据
                .headRowNumber(1) //列表头行数，1代表列表头有1行，第二行开始为数据行
                .doReadSync(); //开始读Excel，返回一个List<T>集合，继续后续入库操作

            List<DiTiyuscore> diTiyuscores = diTiyuscoreMapper.selectList(null);
            //模拟导入数据库操作
            for (TiyuscoreExt tiyuscoreExt : lst) {
                if (Objects.isNull(tiyuscoreExt) || Objects.isNull(tiyuscoreExt.getStudentId())) {
                    continue;
                }
                DiTiyuscore diTiyuscoreUpdate = new DiTiyuscore();
                BeanUtils.copyProperties(tiyuscoreExt, diTiyuscoreUpdate);
                Optional<DiTiyuscore> diTiyuscore =
                    diTiyuscores.stream().filter(r -> Objects.equals(tiyuscoreExt.getStudentId(), r.getStudentId()))
                        .findFirst();

                if (diTiyuscore.isPresent()) {
                    diTiyuscoreMapper.updateById(diTiyuscoreUpdate);
                    continue;
                }

                StuDTO studentByName = stuMapper.getStudentByName(tiyuscoreExt.getStudentName());
                if (Objects.isNull(studentByName)) {
                    continue;
                }
                diTiyuscoreUpdate.setStudentId(tiyuscoreExt.getStudentId());
                diTiyuscoreMapper.insert(diTiyuscoreUpdate);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {

            this.setExcelResponseProp(response, "体育成绩");

            outputStream = response.getOutputStream();
            writer = EasyExcel.write(outputStream, TiyuscoreExt.class).excelType(ExcelTypeEnum.XLSX).build();

            List<DiTiyuscore> diTiyuscores = diTiyuscoreMapper.selectList(null);
            if (Objects.isNull(diTiyuscores)) {
                return;
            }
            // 模拟根据条件在数据库分页查询数据
            List<TiyuscoreExt> result = new ArrayList<>();

            diTiyuscores.forEach(diTiyuscore -> {
                TiyuscoreExt tiyuscoreExt = new TiyuscoreExt();
                BeanUtils.copyProperties(diTiyuscore, tiyuscoreExt);
                result.add(tiyuscoreExt);
            });
            //创建新的sheet页
            WriteSheet writeSheet = EasyExcel.writerSheet("体育成绩").build();
            //将list集合中的对象写到对应的sheet中去
            writer.write(result, writeSheet);
        } catch (Exception exception) {
            // 处理文件导出时的异常，发送错误响应
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Internal Server Error (500)
            response.getWriter().write("Excel 导出失败: " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            // 完成写入后关闭流和 writer
            if (writer != null) {
                writer.finish();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @Override
    public void exportTemplate(HttpServletResponse response) {
        // 文件路径
        String filePath = "templates/导入学生体育成绩模板.xlsx";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource file not found: " + filePath);
            }

            // 设置响应头，提示浏览器下载文件
            String fileName = "导入学生体育成绩模板"; // 下载文件名
            setExcelResponseProp(response, fileName);
            // 将文件内容写入响应流
            try (OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ClassDTO> classList(long gradeId) {
        if (Objects.isNull(gradeId)) {
            return null;
        }
        List<ClassDTO> classes = diTiyuscoreMapper.selectListById(gradeId);
        return classes;
    }

    @Override
    public List<Integer> gradeList(HttpServletResponse response) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        return list;
    }

    private void setExcelResponseProp(HttpServletResponse response, String fileName)
        throws UnsupportedEncodingException {
        // 设置响应头，告诉浏览器这是一个 Excel 文件并且要下载
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        // 防止文件名中文乱码，使用 URLEncoder 编码文件名
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

        // 设置文件下载的响应头
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName + ".xlsx");
    }

    private Date getBirthday(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
