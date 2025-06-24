package com.fiveup.core.management.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveup.core.management.common.exception.BizException;
import com.fiveup.core.management.listener.StuUploadExcelListener;
import com.fiveup.core.management.mapper.ClazzMapper;
import com.fiveup.core.management.mapper.StuMapper;
import com.fiveup.core.management.model.DTO.BasicStudent;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.model.excel.StuDownloadExt;
import com.fiveup.core.management.model.excel.StuUploadExt;
import com.fiveup.core.management.service.StuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 尔宣赫
 * @date 2022/3/22
 */
@Slf4j
@Service
public class StuServiceImpl implements StuService {


    @Resource
    private StuMapper stuMapper;

    @Resource
    private ClazzMapper clazzMapper;

    private String flag = "";

    //获取学生页面
    @Override
    public PageInfo<StuDTO> getStudentListByPage(String keyword, Integer gender, Integer inclination, Long classId, Long schoolId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);


        if (!StringUtils.isEmpty(keyword)) {
            keyword = "%" + keyword + "%";
        }

        List<StuDTO> stuDTOList = stuMapper.getStudentListByCondition(keyword, gender, inclination, classId, schoolId);
//        this.completeQueriedStuDTO(stuDTOList);
        PageInfo<StuDTO> pageInfo = new PageInfo<>(stuDTOList);
        return pageInfo;
    }

    //    获取学生信息列表
    @Override
    public PageInfo<StuDTO> getStudentList(String keyword, Integer gender, Integer inclination, Integer gradeId, Integer classId, Long schoolId, Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize);


        if (!StringUtils.isEmpty(keyword)) {
            keyword = "%" + keyword + "%";
        }

        List<StuDTO> stuDTOList = stuMapper.getStudentListByConditions(keyword, gender, inclination, gradeId, classId, schoolId);
//        this.completeQueriedStuDTO(stuDTOList);
        PageInfo<StuDTO> pageInfo = new PageInfo<>(stuDTOList);
        return pageInfo;
    }

    //导出学生excel
    @Override
    public List<StuDownloadExt> getStudentDownloadExtList(String keyword, Integer gender, Integer gradeId,Integer classId, Long schoolId) {
        if (!StringUtils.isEmpty(keyword)) {
            keyword = "%" + keyword + "%";
        }
        List<StuDTO> stuDTOList = stuMapper.getStudentListByConditions(keyword, gender, null, gradeId,classId, schoolId);
        this.completeQueriedStuDTO(stuDTOList);
        List<StuDownloadExt> stuDownloadExtList = new ArrayList<>();
        for (StuDTO stuDTO : stuDTOList) {
            StuDownloadExt stuDownloadExt = new StuDownloadExt();
            BeanUtils.copyProperties(stuDTO, stuDownloadExt);
            if (stuDownloadExt.getGender() != null) {
                stuDownloadExt.setGenderChineseCharacter(stuDownloadExt.getGender() == 0 ? "女" : "男");
            } if (stuDownloadExt.getClassId() != null) {
                stuDownloadExt.setClassName(stuDownloadExt.getClassId()+"班");
            } if (stuDownloadExt.getGradeId() != null) {
                stuDownloadExt.setGradeName(stuDownloadExt.getGradeId()+"年级");
            }
            stuDownloadExtList.add(stuDownloadExt);
        }
        return stuDownloadExtList;
    }


    /**
     * 使学生信息补齐完整(班级信息)
     *
     * @param stuDTOList 学生list
     */
    private void completeQueriedStuDTO(List<StuDTO> stuDTOList) {
        if (stuDTOList == null || stuDTOList.size() == 0) {
            return;
        }
        for (int i = 0; i < stuDTOList.size(); i++) {
            StuDTO stuDTO = stuDTOList.get(i);
            int classId = stuDTO.getClassId();
            ClassDTO classDTO = clazzMapper.selectOneById(Long.valueOf(classId));
            if (classDTO != null) {
//                stuDTO.setClassName(classDTO.getClassName());
//                stuDTO.setGrade(classDTO.getGrade());
            }
        }
    }

    //2.添加学生
    @Override
    public void addStudent(StuDTO stuDTO) throws BizException {
        int classId = stuDTO.getClassId();
        System.out.println(classId);
        if (classId == 0) {
            throw new BizException("classId为空");
        }
        //查找班级id
        ClassDTO classDTO = clazzMapper.selectOneById(Long.valueOf(classId));
        System.out.println("插入学生的班级为 " + classDTO);
        if (classDTO == null) {
            throw new BizException("该班级不存在!请联系管理员");
        }
        //插入数据
        stuMapper.insertOne(stuDTO);
    }

    //3.修改学生
    @Override
    public void alterStudent(StuDTO stuDTO) {
        stuMapper.updateOne(stuDTO);
    }

    //4.删除学生
    @Override
    public void deleteStudent(String studentId) {
        stuMapper.softlyDeleteById(studentId);
    }

    //5.上传学生文档
    @Override
    public String uploadStuIntoDB(MultipartFile file) {
        InputStream is = null;
        flag = "";
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            log.error("uploadStuIntoDB || error={}", e.getMessage(), e);
            flag += "导入失败，文件流获取失败";
            e.printStackTrace();
        }
        //1.进行读取数据，StuUploadExt，
        //2.new StuUploadExcelListener(this)这个是读取数据的策略
        //3.特别注意的是storageService这个service，我上面有注入进去 @Autowired，切记不要new会报错
        EasyExcel.read(is, StuUploadExt.class, new StuUploadExcelListener(this)).sheet().doRead();
        return flag;
    }

    //上传
    @Override
    public void save(List<StuUploadExt> stuUploadExtList) throws RuntimeException {
        System.out.println(stuUploadExtList + "学生信息记录解析");
        int count = stuUploadExtList.size();
        if (stuUploadExtList == null || stuUploadExtList.size() == 0) {
            flag += "上传失败，导入的学生集合为空";
            return;
        }
        for (StuUploadExt stuUploadExt : stuUploadExtList) {
//            StuDTO stuDTO = new StuDTO();
            BasicStudent stuDTO= new BasicStudent();
            BeanUtils.copyProperties(stuUploadExt, stuDTO);
            if (stuUploadExt.getGenderChineseCharacter() != null) {
                if ("男".equals(stuUploadExt.getGenderChineseCharacter().trim())) {
                    stuDTO.setGender(1);
                } else if ("女".equals(stuUploadExt.getGenderChineseCharacter().trim())) {
                    stuDTO.setGender(0);
                } else {
                    log.error("gender_errorly_recognised || error={}");
                    flag += "学号:"+stuDTO.getStudentNum()+",姓名:"+stuDTO.getStudentName()+"的性别导入错误，请检查后重试!";
                    count--;
//                    throw new RuntimeException("性别导入错误，请检查后重试!");
                    continue;
                }
            }
//            不能导入学号已经存在的学生
//            正则校验
            Long number = selectCount(stuDTO.getStudentNum());
            if (number != 0) {
                log.error("excel导入学生学号已存在");
                flag += "学号:"+stuDTO.getStudentNum()+",姓名:"+stuDTO.getStudentName()+"的学号已存在!\n";
                count--;
                continue;
//                throw new RuntimeException("学号导入错误");
            }
            System.out.println("未知错误-matcher");
            System.out.println(stuDTO);
            if (!Pattern.matches("^[\\u4e00-\\u9fa5]{1,20}$", stuDTO.getStudentName())) {
                log.error("excel导入学生姓名部位中文");
                flag += "学号:"+stuDTO.getStudentNum()+",姓名:"+stuDTO.getStudentName()+"的姓名导入错误，请检查后重试!";
                count--;
                continue;
//                throw new RuntimeException("学生姓名导入错误");
            }
            if (!Pattern.matches("^((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9}))$", stuDTO.getParentPhoneNum())) {
                log.error("excel导入手机号码失败");
                flag += "学号:"+stuDTO.getStudentNum()+",姓名:"+stuDTO.getStudentName()+"的手机号码导入错误，请检查后重试!";
                count--;
                continue;
//                throw new RuntimeException("手机号码导入错误");
            }
            if (!Pattern.matches("^[0-9]{10}$", stuDTO.getStudentNum())) {
                log.error("excel导入学号错误");
                flag += "学号:"+stuDTO.getStudentNum()+",姓名:"+stuDTO.getStudentName()+"的学号导入错误，请检查后重试!";
                count--;
                continue;
//                throw new RuntimeException("学号位数错误");
            }
            stuDTO.setIsreview(0);
            stuDTO.setIsenter(0);
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(stuUploadExt.getClassName());
            Matcher matcher1 = pattern.matcher(stuUploadExt.getGradeName());
            if(matcher1.find()&&matcher.find()){
                int c = clazzMapper.getClassByGradeIdAndClassId(Integer.parseInt(matcher1.group()),null);
                int d = clazzMapper.getClassByGradeIdAndClassId(Integer.parseInt(matcher1.group()),Integer.parseInt(matcher.group()));
                if(c==0){
                    flag += "学号:" + stuDTO.getStudentNum() + ",姓名:" + stuDTO.getStudentName() + "的年级不存在，请检查后重试!";
                    count--;
                    continue;
                }else {
                    if(d==0){
                        flag += "学号:" + stuDTO.getStudentNum() + ",姓名:" + stuDTO.getStudentName() + "的班级不存在，请检查后重试!";
                        count--;
                        continue;
                    }
                }
                stuDTO.setClassId(Integer.parseInt(matcher.group()));
                stuDTO.setGradeId(Integer.parseInt(matcher1.group()));
            }else {
                flag += "学号:" + stuDTO.getStudentNum() + ",姓名:" + stuDTO.getStudentName() + "的班级或年级导入错误，请检查后重试!";
                count--;
                continue;
//                throw new RuntimeException("年级或者班级格式错误");
            }
            stuMapper.insert(stuDTO);
        }
        if(count==stuUploadExtList.size()){
            flag += "成功导入"+count+"条数据";
        }else if(count>0){
            flag += "成功导入"+count+"条数据，失败"+(stuUploadExtList.size()-count)+"条数据";
        }else {
            flag += "全部导入失败";
        }
    }

    //按学号查询学生的数量
    @Override
    public long selectCount(String stunum) {
        //构造条件构造器
        LambdaQueryWrapper<BasicStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BasicStudent::getStudentNum, stunum);
        // 按学号查询学生的数量，并设置条件deleted = 0
//        queryWrapper.eq(BasicStudent::getStudentNum, stunum).eq(BasicStudent::getDeleted, 0);
        Long count = stuMapper.selectCount(queryWrapper);
        return count;
    }

    //按学生姓名查询学生信息
    public StuDTO getStudentByName(String studentName) {
        StuDTO stuDTO = stuMapper.getStudentByName(studentName);
        return stuDTO;
    }

    //按学生性别查询学生信息
    public StuDTO getStudentByGender(String gender) {
        StuDTO stuDTO = stuMapper.getStudentByGender(gender);
        return stuDTO;
    }

    @Override
    public int deleteBatch(List<Integer> ids) {
        return stuMapper.deleteBatch(ids);
    }

    //按照关键字查询学生
    public List<StuDTO> getStudentByKeyword(String keyword) {
        List<StuDTO> stuDTO = stuMapper.getStudentListByCondition(keyword, 0, 0, 0L, 0L);
        return stuDTO;
    }
    // 获取所有学生信息
    public List<StuDTO> getAllStudent(Long schoolId) {
        List<StuDTO> stuDTOList = stuMapper.getStudentListByConditions(null, null, null,null, null, schoolId);
        return stuDTOList;
    }
}
