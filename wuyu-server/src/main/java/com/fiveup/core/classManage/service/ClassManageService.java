package com.fiveup.core.classManage.service;


import com.fiveup.core.classManage.model.*;
import com.fiveup.core.classManage.model.dto.ClassResp;
import com.fiveup.core.classManage.model.request.ClassReq;
import com.fiveup.core.classManage.model.response.ClassPageResp;
import com.github.pagehelper.PageInfo;
import com.fiveup.core.classManage.model.*;
import com.fiveup.core.classManage.model.CTCorrelation;
import com.fiveup.core.classManage.model.ClassAndMonitor;
import com.fiveup.core.classManage.model.ClassInfo;
import com.fiveup.core.classManage.model.StudentInfo;
import com.fiveup.core.fuScore.model.ClassFuItemScore;

import java.util.HashMap;
import java.util.List;

public interface ClassManageService {

//    List<ClassAndMonitor> getAllClassInfo();
//
//    int addTeacherToClass(CTCorrelation ctCorrelation);
//
//    int addClass(ClassInfo classinfo);
//
//    List<StudentInfo> getStudentsByClass(int gradeId, int classId);
//
//    List<Integer> getAllClassByGrade(int gradeId);

    PageInfo<ClassPageResp> getClassListByPage(int grade,int monitorId, Integer pageNum, Integer pageSize);

    List<Teacher> getMonitorList();

    int getMonitorIdByName(String realName);

//    int addClass(ClassInfo classinfo);
//
//    Integer addTeacher(ClassInfo classInfo);

    List<Teacher> getAllTeacherList();

    ClassResp addClass(ClassReq classReq);

    boolean deleteClass(int id);
//    List<Integer> getAllClassByGrade(int gradeId);

    ClassPartInfo getClassPartInfoByClassId(String grade,String className);

    List<StudentInfo> getStudentByPage(int classId, int pageStart,  int pageSize);

    int deletestudent(int studentId);

    int updateClassByInformationId(int classInformationId,String classIntroduction);

    int getStudentNum(int classId);
    List<Integer> getAllClassByGrade(int gradeId);
    List<ClassFuItemScore> getClassNameByGrade(int gradeId);

    public int addGrade(List<GradeInfo> gradeInfoList);
}
