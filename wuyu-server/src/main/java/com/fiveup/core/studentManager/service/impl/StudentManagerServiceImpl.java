package com.fiveup.core.studentManager.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.common.exception.ApiException;

import com.fiveup.core.management.model.School;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.mapper.StudentManagerMapper;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentInsertDTO;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.pojo.StudentVO;
import com.fiveup.core.studentManager.service.StudentManagerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class StudentManagerServiceImpl extends ServiceImpl<StudentManagerMapper, StudentManager> implements StudentManagerService {
    @Autowired
    private StudentManagerMapper studentManagerMapper;

    private StudentManagerService studentManagerService;

    @Override
    public PageBean<StudentVO> getStudentPage(StudentManagerQuery studentManagerQuery) {
        System.out.println("前端传来页码"+studentManagerQuery.getPage());
        System.out.println("前端传来每页显示的条数"+studentManagerQuery.getSizeOfPage());

        int sizeOfPage = studentManagerQuery.getSizeOfPage() == null ? 10 : studentManagerQuery.getSizeOfPage();

        if (studentManagerQuery.getPage() == null) {
            studentManagerQuery.setPage(1);
        }

        PageHelper.startPage(studentManagerQuery.getPage(), sizeOfPage);

        //开始查询
        List<StudentVO> list = studentManagerMapper.getStudentPage(studentManagerQuery);
        Page<StudentVO> newPage = (Page<StudentVO>) list;

        //为pagebean赋值
        PageBean<StudentVO> pageBean = new PageBean<>();
        pageBean.setData(newPage.getResult());
        pageBean.setTotalNum((int) newPage.getTotal());
        pageBean.setTotalPage(newPage.getPages());
        pageBean.setSizeOfPage(sizeOfPage);
        pageBean.setSizeOfCurrPage(newPage.getResult().size());
        pageBean.setPage(studentManagerQuery.getPage());

        return pageBean;
    }

    @Override
    public void addStudent(StudentInsertDTO studentInsertDTO) {
        int classId;

        //获取classId
        try {
            classId = studentManagerMapper.selectClassId(
                    studentInsertDTO.getGradeId(),
                    studentInsertDTO.getSchoolId(),
                    studentInsertDTO.getClassName());
        } catch (Exception e) {
            String schoolName = studentManagerMapper.getSchoolNameById(studentInsertDTO.getSchoolId());
            throw new ApiException("学校" + schoolName + "的年级" + studentInsertDTO.getGradeId() + "不存在班级" + studentInsertDTO.getClassName());
        }

        //创建StudentManager对象
        StudentManager studentManager = new StudentManager();
        studentManager.setStudentName(studentInsertDTO.getStudentName());
        studentManager.setStudentNum(studentInsertDTO.getStudentNum());
        studentManager.setGender(studentInsertDTO.getGender());
        studentManager.setClassId(classId);
        studentManager.setGradeId(studentInsertDTO.getGradeId());
        studentManager.setParentPhoneNum(studentInsertDTO.getParentPhoneNum());
        studentManager.setSchoolId(studentInsertDTO.getSchoolId());
        studentManager.setIsreview(0);
        studentManager.setDeleted(0);
        studentManager.setIsenter(0);

        studentManagerMapper.insertStudent(studentManager);
    }

    @Override
    public void updateStudent(StudentInsertDTO studentInsertDTO) {
        int classId;

        //获取classId
        try {
            classId = studentManagerMapper.selectClassId(
                    studentInsertDTO.getGradeId(),
                    studentInsertDTO.getSchoolId(),
                    studentInsertDTO.getClassName());
        } catch (Exception e) {
            String schoolName = studentManagerMapper.getSchoolNameById(studentInsertDTO.getSchoolId());
            throw new ApiException("学校" + schoolName + "的年级" + studentInsertDTO.getGradeId() + "不存在班级" + studentInsertDTO.getClassName());
        }

        //创建StudentManager对象
        StudentManager studentManager = new StudentManager();

        studentManager.setId(studentInsertDTO.getId());
        studentManager.setStudentName(studentInsertDTO.getStudentName());
        studentManager.setStudentNum(studentInsertDTO.getStudentNum());
        studentManager.setGender(studentInsertDTO.getGender());
        studentManager.setClassId(classId);
        studentManager.setGradeId(studentInsertDTO.getGradeId());
        studentManager.setParentPhoneNum(studentInsertDTO.getParentPhoneNum());
        studentManager.setSchoolId(studentInsertDTO.getSchoolId());
        studentManager.setIsreview(studentInsertDTO.getIsreview());
        studentManager.setDeleted(studentInsertDTO.getDeleted());
        studentManager.setIsenter(studentInsertDTO.getIsenter());

        studentManagerMapper.updateById(studentManager);
    }

    @Override
    public void removeStudent(Integer studentId) {
        QueryWrapper<StudentManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", studentId);
        StudentManager studentManager = studentManagerMapper.selectOne(queryWrapper);
        studentManager.setDeleted(1);
        studentManagerMapper.updateById(studentManager);
    }

    @Override
    public List<School> getSchool() {
        return studentManagerMapper.getSchool();
    }

    @Override
    public List<String> getClassName() {
        return studentManagerMapper.getClassName();
    }

    @Override
    public List<Integer> getGrade() {
        return studentManagerMapper.getGrade();
    }

    public void export(HttpServletResponse response) {
        QueryWrapper<StudentManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",0);
        List<StudentManager> list = studentManagerMapper.selectList(queryWrapper);
        try {
            ClassPathResource resource = new ClassPathResource("templates/studentInput.xlsx");
            InputStream in = resource.getInputStream();
            XSSFWorkbook excel = new XSSFWorkbook(in);
            XSSFSheet sheet1 = excel.getSheetAt(0);
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); ++i) {
                    XSSFRow row = sheet1.getRow(i + 1);
                    if (row == null) {
                        row = sheet1.createRow(i + 1);
                    }
                    for (int col = 0; col < 6; col++) {
                        XSSFCell cell = row.getCell(col);
                        if (cell == null) {
                            row.createCell(col);
                        }
                    }
                    row.getCell(0).setCellValue(list.get(i).getStudentNum());
                    row.getCell(1).setCellValue(list.get(i).getStudentName());
                    row.getCell(2).setCellValue(list.get(i).getGender()==0?"女":"男");
                    row.getCell(3).setCellValue(list.get(i).getGradeId()+"年级");
                    row.getCell(4).setCellValue(list.get(i).getClassId()+"班");
                    row.getCell(5).setCellValue(list.get(i).getParentPhoneNum());
                }

            }
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=studentOutput.xlsx");
            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);
            outputStream.close();
            in.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //判断中文
    private   boolean isPureChineseWithExt(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (char c : str.toCharArray()) {
            // 判断基本汉字和扩展A区
            if ((c < '\u4E00' || c > '\u9FFF') &&
                    (c < '\u3400' || c > '\u4DBF')) {
                return false;
            }
        }
        return true;
    }
    //获取单元格
    private String getCellValueAsString(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        System.out.println(cell.getCellType());
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                BigDecimal bd = new BigDecimal(String.valueOf(cell.getNumericCellValue()));
                System.out.println(bd.toPlainString());
                return bd.toPlainString();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

        @Override
        public void importstudent(MultipartFile file) {
            InputStream inputStream = null;
            XSSFWorkbook workbook = null;
            Set<String> numset = studentManagerMapper.selectnum();//学号集合
            Set<String> classset= studentManagerMapper.selectclassgrade();//班级年级
            try {
                inputStream = file.getInputStream();
                workbook = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = workbook.getSheetAt(0);
                List<StudentManager> list = new ArrayList<>();
                Set<String> set=new HashSet<>();//学号
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    StudentManager studentManager = new StudentManager();
                    XSSFRow row = sheet.getRow(i);
                    String f=new String();
                    if (row != null) {
                        for (int col = 0; col < 6; col++) {
                            String stringCellValue = getCellValueAsString(row.getCell(col));
                            switch (col) {
                                case 0:
                                    if (!Pattern.matches("\\d{10}", stringCellValue))
                                        throw new ApiException("第" + i + "行学生学号为10位数字");
                                    else if(!set.contains(stringCellValue)&&!numset.contains(stringCellValue)){
                                        set.add(stringCellValue);
                                        studentManager.setStudentNum(stringCellValue);
                                    }else throw new ApiException("第"+i+"行重复导入");
                                    break;
                                case 1:
                                    if (!isPureChineseWithExt(stringCellValue))
                                        throw new ApiException("第" + i + "行学生姓名只能为中文");
                                    else studentManager.setStudentName(stringCellValue);
                                    break;
                                case 2:
                                    if ("男".equals(stringCellValue) || "女".equals(stringCellValue))
                                        studentManager.setGender("男".equals(stringCellValue) ? 1 : 0);
                                    else throw new ApiException("第" + i + "行性别只能为男或者女");
                                    break;
                                case 3:
                                    if (Pattern.matches("\\d+年级", stringCellValue)) {
                                        studentManager.setGradeId(Integer.valueOf(stringCellValue.substring(0, stringCellValue.length() - 2)));
                                        f+=stringCellValue.substring(0, stringCellValue.length() - 2)+"#";
                                    }
                                    else throw new ApiException("第" + i + "行年级示例：1年级，使用阿拉伯数字");
                                    break;
                                case 4:
                                    if (Pattern.matches("\\d+班", stringCellValue)){
                                        f+=stringCellValue.substring(0, stringCellValue.length() - 1);
                                        if(!classset.contains(f))throw new ApiException("第"+i+"行班级年级不存在");
                                        studentManager.setClassId(Integer.valueOf(stringCellValue.substring(0, stringCellValue.length() - 1)));
                                    }
                                    else throw new ApiException("第" + i + "行班级示例：3班，使用阿拉伯数字");
                                    break;
                                default:
                                    if (Pattern.matches("\\d{11}", stringCellValue))
                                        studentManager.setParentPhoneNum(stringCellValue);
                                    else throw new ApiException("第" + i + "行联系方式只能为11为手机号码");
                            }
                        }
                        studentManager.setIsreview(1);
                        studentManager.setIsenter(1);
                        list.add(studentManager);
                    }
                }
                this.saveBatch(list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (workbook != null) {
                        workbook.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        }
}
