package com.fiveup.core.classManage.service.impl;


import com.fiveup.core.classManage.mapper.ClassManageMapper;
import com.fiveup.core.classManage.model.*;
import com.fiveup.core.classManage.model.dto.ClassResp;
import com.fiveup.core.classManage.model.request.ClassReq;
import com.fiveup.core.classManage.model.response.ClassPageResp;
import com.fiveup.core.classManage.model.*;
import com.fiveup.core.classManage.service.ClassManageService;
import com.fiveup.core.events.model.response.ActivityPageResp;
import com.fiveup.core.questionnaire.mapper.ClassMapper;
import com.fiveup.core.sixGroup.entity.BasicClass;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import com.fiveup.core.fuScore.model.ClassFuItemScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ParentServiceImpl
 * @Author: shilin
 * @Date: 2022/9/18 16:32
 */
@Service
@Slf4j
public class ClassManageServiceImpl implements ClassManageService {

    @Autowired
    private ClassManageMapper classManageMapper;

    //    @Override
//    public List<ClassAndMonitor> getAllClassInfo() {
//        List<ClassAndMonitor> classAndMonitor;
//        classAndMonitor = classManageMapper.getAllClassInfo();
//        return classAndMonitor;
//    }
//
//    @Override
//    public int addTeacherToClass(CTCorrelation ctCorrelation) {
//        return classManageMapper.addTeacherToClass(ctCorrelation);
//    }
//
//    @Override
//    public int addClass(ClassInfo classInfo) {
//        int code = classManageMapper.addClass(classInfo);
//        return code;
//    }
//
//    @Override
//    public List<StudentInfo> getStudentsByClass(int gradeId, int classId) {
//        List<StudentInfo> studentList;
//        studentList = classManageMapper.getStudentsByClass(gradeId, classId);
//        return studentList;
//    }
//
    @Override
    @Transactional
    public List<Integer> getAllClassByGrade(int gradeId) {
        List<Integer> classList;
        classList = classManageMapper.getAllClassByGrade(gradeId);
        return classList;
    }

    @Override
    public PageInfo<ClassPageResp> getClassListByPage( List<Integer> classIds,int grade,
                                                      int monitorId,
                                                      Integer pageNum,
                                                      Integer pageSize) {
        System.out.println("grade: " + grade);
        PageHelper.startPage(pageNum, pageSize);
        List<ClassPageResp> classPageResps = classManageMapper.getClassByDynamicCondition(grade, monitorId,classIds);
        System.out.println("classPageResps: " + classPageResps);
        for (ClassPageResp classPageResp : classPageResps) {
            Teacher teacher = classManageMapper.getTeacherByTeacherId(classPageResp.getMonitorId());
            System.out.println("teacher: " + teacher);
            if (teacher != null) {
                int studentNum = classManageMapper.getStudentNumByClassId(classPageResp.getId());
                classPageResp.setGender(teacher.getGender());
                classPageResp.setRealName(teacher.getTeacherName());
                classPageResp.setContactInfo(teacher.getPhoneNum());
                classPageResp.setStudentNumber(studentNum);
            }
        }

        PageInfo<ClassPageResp> pageInfo = new PageInfo<>(classPageResps);
        return pageInfo;
    }

    @Override
    public List<Teacher> getMonitorList() {
        List<Teacher> teacherList = classManageMapper.getAllMonitor();
        return teacherList;
    }

    @Override
    public int getMonitorIdByName(String realName) {
        int monitorId = classManageMapper.getMonitorIdByName(realName);
        return monitorId;
    }

//    @Override
//    public int addClass(ClassInfo classInfo) {
//        int code = classManageMapper.addClass(classInfo);
//        return code;
//    }
//
//    @Override
//    public Integer addTeacher(ClassInfo classInfo) {
//        return classManageMapper.addTeacher(classInfo);
//    }

    @Override
    public List<Teacher> getAllTeacherList() {
        List<Teacher> teacherList = classManageMapper.getAllTeacher();
        return teacherList;
    }

    /**
     * 添加班级
     *
     * @param classReq
     * @return boolean
     * @author: 王德荣
     * @date: 2024/11/25
     */
    @Transactional
    @Override
    public ClassResp addClass(ClassReq classReq) {
        log.info("Service addClass: " + classReq);
        // 首先判断年级ID是否存在，
        // 此项目数据库不存在年级表，
        // 只有basic_class表中有grade字段取值为1~6，
        // 也就是从1年级到6年级
        // 因此只需判断gradeId是否在1~6之间即可
        // 按理说应该从gradeService或gradeMapper中提供的例如existGrade()方法获取
        // 但鉴于此项目的设计颇具创造性，我在此也就沿用其设计
        int gradeId = classReq.getGradeId();
        if (!(gradeId >= 1 && gradeId <= 6)) {
            return ClassResp.fail("年级参数异常，只能为1~6");
        }

        // 判断该年级的该班级ID（表示几班，例如班级ID为1表示1班）是否存在
        int classId = classReq.getClassId();
        if (classManageMapper.existClass(gradeId, classId)) {
            // 班级ID已存在
            return ClassResp.fail("此年级已存在该班级");
        }

        // 根据班主任名字查询其ID
        int monitorId = classManageMapper.getIdByTeacherName(classReq.getTeacherName());
        log.info("monitorId: " + monitorId);
        // 判断班主任ID是否存在
        if (!classManageMapper.existTeacher(monitorId)) {
            // 班主任ID不存在
            return ClassResp.fail("该班主任不存在");
        }

        // grade字段值与gradeId一致
        String grade = String.valueOf(gradeId);

        // 班级名称
        String className = classId + "班";

        if (classManageMapper.addClass(grade, classId, className, monitorId, gradeId)) {
            return ClassResp.success("添加成功");
        }
        return ClassResp.fail("添加失败");
    }

    //李江平 TODO
    @Override
    public boolean deleteClass(int id) {
        int count = classManageMapper.deleteClass(id);
        System.out.println("ClassManageController(136): count = " + count);
        if (count != 0) {
            return true;
        }
        return false;
    }


    @Override
    public ClassPartInfo getClassPartInfoByClassId(String grade, String className) {

        log.info("***********************************************");
        log.info("grade: " + grade);
        log.info("className: " + className);
        ClassPartInfo classPartInfo = classManageMapper.getClassPartInfoByClassId(grade, className);

        log.info("++++++++++++++++++++++++++++++++++++++++++++");
        log.info("classPartInfo: " + classPartInfo);
        switch (classPartInfo.getGrade()) {
            case "1":
                classPartInfo.setGrade("1年级");
                break;
            case "2":
                classPartInfo.setGrade("2年级");
                break;
            case "3":
                classPartInfo.setGrade("3年级");
                break;
            case "4":
                classPartInfo.setGrade("4年级");
                break;
            case "5":
                classPartInfo.setGrade("5年级");
                break;
            case "6":
                classPartInfo.setGrade("6年级");
                break;
            default:
        }
        log.info("/////////////////////////////////////////////");
        return classPartInfo;
    }

    @Override
    public List<StudentInfo> getStudentByPage(int classId, int page, int pageSize) {
        List<StudentInfo> studentList;
        int pageStart = (page - 1) * pageSize;
        studentList = classManageMapper.getStudentByPage(classId, pageStart, pageSize);
        return studentList;
    }

    @Override
    public int deletestudent(int studentId) {

        return classManageMapper.deletestudent(studentId);
    }

    @Override
    public int updateClassByInformationId(int classInformationId, String classIntroduction) {
        int res = classManageMapper.updateClassByInformationId(classInformationId, classIntroduction);
        return res;
    }

    @Override
    public int getStudentNum(int classId) {
        return classManageMapper.getStudentNum(classId);
    }

    @Override
    public List<ClassFuItemScore> getClassNameByGrade(int gradeId) {
        return classManageMapper.getClassName(gradeId);
    }

    @Override
    public int addGrade(List<GradeInfo> gradeInfoList) {
        for (GradeInfo gradeInfo : gradeInfoList) {
            Integer monitorId = classManageMapper.getTeacherId(gradeInfo.getTeacherName());
            System.out.println("monitorId:" + monitorId);
            if (monitorId == null || monitorId.equals("")) {
                //教师不存在
                return -1;
            }
            gradeInfo.setMonitorId(monitorId);
            if (gradeInfo.getGrade() == null || gradeInfo.getGrade().equals("")) {
                return -2;
            }
            if (gradeInfo.getClassName() == null || gradeInfo.getClassName().equals("")) {
                return -3;
            }
            int gradeId;
            String grade = gradeInfo.getGrade();
            switch (grade) {
                case "1年级":
                    gradeId = 1;
                    gradeInfo.setGrade("1");
                    break;
                case "2年级":
                    gradeId = 2;
                    gradeInfo.setGrade("2");
                    break;
                case "3年级":
                    gradeId = 3;
                    gradeInfo.setGrade("3");
                    break;
                case "4年级":
                    gradeId = 4;
                    gradeInfo.setGrade("4");
                    break;
                case "5年级":
                    gradeId = 5;
                    gradeInfo.setGrade("5");
                    break;
                case "6年级":
                    gradeId = 6;
                    gradeInfo.setGrade("6");
                    break;
                default:
                    gradeId = 0;
            }
            gradeInfo.setGradeId(gradeId);
            gradeInfo.setClassName(gradeInfo.getClassName() + "班");

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(gradeInfo.getClassName());
            if (matcher.find()) {
                int number = Integer.parseInt(matcher.group());
                gradeInfo.setClassType(number);
                System.out.println(number);
            }

        }
        for (GradeInfo gradeInfo : gradeInfoList) {
            if (classManageMapper.getGradeNum(gradeInfo) == 0) {
                classManageMapper.addGrade(gradeInfo);
            } else {
                classManageMapper.updateGrade(gradeInfo);
            }

        }
        return 1;
    }

    @Override
    public List<BasicClass> getClassByTeacherId(Integer id) {
      return   classManageMapper.getClassByTeacherId(id);
    }
}


