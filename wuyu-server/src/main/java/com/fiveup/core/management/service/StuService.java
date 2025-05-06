package com.fiveup.core.management.service;

import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.model.excel.StuDownloadExt;
import com.fiveup.core.management.model.excel.StuUploadExt;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/22
 */
public interface StuService {

    PageInfo<StuDTO> getStudentListByPage(String keyword, Integer gender, Integer inclination, Long classId, Long schoolId, Integer pageNum, Integer pageSize);

    PageInfo<StuDTO> getStudentList(String keyword, Integer gender, Integer inclination, Integer gradeId, Integer classId, Long schoolId, Integer pageNum, Integer pageSize);

    //    按筛选条件导出学生名单
    List<StuDownloadExt> getStudentDownloadExtList(String keyword, Integer gender, Integer gradeId,Integer classId,Long schoolId);

    //    添加学生
    void addStudent(StuDTO stuDTO);

    //    修改学生
    void alterStudent(StuDTO stuDTO);

    //    删除学生
    void deleteStudent(String studentId);

    //    用excel上传学生信息
    String uploadStuIntoDB(MultipartFile file);

    /**
     * 用于将导入数据Batch-Size List保存到数据库中
     *
     * @param stuUploadExtList
     */
    void save(List<StuUploadExt> stuUploadExtList);

    //按学号查询学生的数量
    long selectCount(String stunum);

    //按学生姓名查询学生信息
    StuDTO getStudentByName(String studentName);

    //按性别查询学生信息
    StuDTO getStudentByGender(String gender);

    int deleteBatch(List<Integer> ids);
    // 查询所有学生信息
    List<StuDTO> getAllStudent(Long schoolId);
}
