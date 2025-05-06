package com.fiveup.core.events.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.fiveup.core.events.Listener.ActStuScoreExcelListener;
import com.fiveup.core.events.mapper.ActMapper;
import com.fiveup.core.events.mapper.ActivityStudentMapper;
import com.fiveup.core.events.mapper.ClassActivityMapper;
import com.fiveup.core.events.model.ActivityStudent;
import com.fiveup.core.events.model.ClassActivity;
import com.fiveup.core.events.model.ext.ActivityStudentDownloadExt;
import com.fiveup.core.events.model.ext.ActivityStudentScoreTemplateExt;
import com.fiveup.core.events.model.response.ActivityDetailResp;
import com.fiveup.core.events.service.ActivityStudentService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.mapper.ClazzMapper;
import com.fiveup.core.management.mapper.StuMapper;
import com.fiveup.core.management.mapper.TeaMapper;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.model.DTO.TeaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/26
 */
@Slf4j
@Service
public class ActivityStudentServiceImpl implements ActivityStudentService {

    @Resource
    private ActMapper actMapper;

    @Resource
    private ClassActivityMapper classActivityMapper;

    @Resource
    private ClazzMapper clazzMapper;

    @Resource
    private StuMapper stuMapper;

    @Resource
    private TeaMapper teaMapper;

    @Resource
    private ActivityStudentMapper activityStudentMapper;

    /**
     * 一次性对所有参加活动的学生打分
     * @param activityId
     * @param score
     * @param schoolId
     */
    @Override
    public CommonResponse checkInActForStu(Long activityId, Float score, Long schoolId) {
        //获取activityId活动的细节信息
        ActivityDetailResp activityDetail = actMapper.getActivityDetail(activityId);
        if (activityDetail == null) return CommonResponse.fail(1000,"activityId传入异常或者未找到activityId");
        String grade = activityDetail.getGrade(); //grade为空表示为班级活动
        if (StringUtils.isEmpty(grade)) {
            // 识别为按班级添加的活动
            //获取到活动的相关信息
            List<ClassActivity> classActivityList = classActivityMapper.getClassActivityByActivityId(activityId);
            //返回结果classActivityList ：[ClassActivity(id=21, activityId=134, classId=21)]
            if (classActivityList == null || classActivityList.size() == 0)
                return CommonResponse.fail(1001,"该活动还没有成功分配班级，请检查活动创建是否成功！");
            //可能有多个班级都要举行这活动
            for (ClassActivity classActivity : classActivityList) {
                Long classId = classActivity.getClassId();
                //查询该班级classId的所有学生
                List<StuDTO> stuDTOList = stuMapper.getStudentListByCondition(null, null, null, classId, schoolId);
                if (stuDTOList == null || stuDTOList.size() == 0) continue;
                // 添加学生成绩记录
                for (StuDTO stuDTO : stuDTOList) {
                    //查询判断老师是否已经给这位学生打分
                    ActivityStudent student=activityStudentMapper.selectOneByActivityIdAndStuNum(activityId,stuDTO.getStudentNum());
                    ActivityStudent activityStudent = new ActivityStudent(activityId, stuDTO.getStudentNum(), score);
                    if(student != null){ //已经存在就修改数据，否则就就是第一次打分，插入数据
                        activityStudentMapper.updateOne(activityStudent);
                    }else{
                        activityStudentMapper.insertOne(activityStudent);
                    }
                }
            }
            return CommonResponse.ok(200);
        } else {
            // 识别为按年级添加的活动
            List<StuDTO> stuDTOList = stuMapper.getStuListBySchoolIdAndGradeName(schoolId, grade);
            if (stuDTOList == null || stuDTOList.size() == 0)
                CommonResponse.fail(1003,"没有查询到改年级的学生，请检查输入学校和年级参数是否正确！");
            // 添加学生成绩记录
            for (StuDTO stuDTO : stuDTOList) {
                ActivityStudent activityStudent = new ActivityStudent(activityId, stuDTO.getStudentNum(), score);
                activityStudentMapper.insertOne(activityStudent);
            }
            return CommonResponse.ok(200);
        }
    }

    @Override
    public boolean alterOne(ActivityStudent activityStudent) {
        System.out.println("mapper");
        System.out.println(activityStudent);
        return activityStudentMapper.updateOne(activityStudent);
    }

    @Override
    public boolean insertOne(ActivityStudent activityStudent) {
        return activityStudentMapper.insertOne(activityStudent);
    }

    @Override
    public List<ActivityStudentDownloadExt> getActivityStudentExtList(Long activityId, Long schoolId) {
        List<ActivityStudentDownloadExt> activityStudentDownloadExtList = new ArrayList<>();
        ActivityDetailResp activityDetailResp = actMapper.getActivityDetail(activityId);
        if (activityDetailResp == null) {
            return new ArrayList<>();
        }
        if (activityDetailResp.getActivityType() == 0) {
            // 从班级查询学生
            //List<ClassActivity> classActivityList = classActivityMapper.getClassActivityByActivityId(activityId);
            //if (classActivityList != null && classActivityList.size() != 0) {
            //    List<StuDTO> stuDTOTotalList = new ArrayList<>();
            List<StuDTO> stuDTOTotalList = stuMapper.getStuListByClassId(Long.valueOf(activityDetailResp.getClassId()));
                //for (ClassActivity classActivity : classActivityList) {
                //    Long classId = classActivity.getClassId();
                //    List<StuDTO> stuDTOList = stuMapper.getStuListByClassId(classId);
                //    stuDTOTotalList.addAll(stuDTOList);
                //}

            if (stuDTOTotalList.size() != 0) {
                for (StuDTO stuDTO : stuDTOTotalList) {
                    ActivityStudentDownloadExt activityStudentDownloadExt = new ActivityStudentDownloadExt();
                    // 拷贝活动相同属性值
                    BeanUtils.copyProperties(activityDetailResp, activityStudentDownloadExt);
                    // 拷贝学生相同属性值
                    BeanUtils.copyProperties(stuDTO, activityStudentDownloadExt);
                    ActivityStudent activityStudent = activityStudentMapper.selectOneByActivityIdAndStuNum(activityId, stuDTO.getStudentNum());
                    if (activityStudent != null) {
                        activityStudentDownloadExt.setStudentScore(activityStudent.getStuActScore());
                    }
                    ClassDTO classDTO = clazzMapper.selectOneById(Long.valueOf(stuDTO.getClassId()));
                    // 拷贝班级相同属性值
                    if (classDTO != null) {
                        BeanUtils.copyProperties(classDTO, activityStudentDownloadExt);
                    }
                    TeaDTO teaDTO = teaMapper.getTeacherById(classDTO.getMonitorId());
                    if (teaDTO != null) {
                        teaDTO.setMonitorName(teaDTO.getTeacherName());
                        BeanUtils.copyProperties(teaDTO, activityStudentDownloadExt);
                    }
                    activityStudentDownloadExtList.add(activityStudentDownloadExt);
                }
            }
        } else {
            // TODO 从年级查询学生
            List<StuDTO> stuDTOTotalList = new ArrayList<>();
            List<StuDTO> stuDTOList = stuMapper.getStuListBySchoolIdAndGradeName(schoolId, activityDetailResp.getGrade());
            stuDTOTotalList.addAll(stuDTOList);
            if (stuDTOTotalList.size() != 0) {
                for (StuDTO stuDTO : stuDTOTotalList) {
                    ActivityStudentDownloadExt activityStudentDownloadExt = new ActivityStudentDownloadExt();
                    // 拷贝活动相同属性值
                    BeanUtils.copyProperties(activityDetailResp, activityStudentDownloadExt);
                    // 拷贝学生相同属性值
                    BeanUtils.copyProperties(stuDTO, activityStudentDownloadExt);
                    ActivityStudent activityStudent = activityStudentMapper.selectOneByActivityIdAndStuNum(activityId, stuDTO.getStudentNum());
                    if (activityStudent != null) {
                        activityStudentDownloadExt.setStudentScore(activityStudent.getStuActScore());
                    }
                    ClassDTO classDTO = clazzMapper.selectOneById(Long.valueOf(stuDTO.getClassId()));
                    // 拷贝班级相同属性值
                    if (classDTO != null) {
                        BeanUtils.copyProperties(classDTO, activityStudentDownloadExt);
                    }
                    TeaDTO teaDTO = teaMapper.getTeacherById(classDTO.getMonitorId());
                    if (teaDTO != null) {
                        teaDTO.setMonitorName(teaDTO.getTeacherName());
                        BeanUtils.copyProperties(teaDTO, activityStudentDownloadExt);
                    }
                    activityStudentDownloadExtList.add(activityStudentDownloadExt);
                }
            }
        }
        return activityStudentDownloadExtList;
    }

    @Override
    public List<ActivityStudentScoreTemplateExt> getActStudentTemplate(Long activityId) {
        return null;
    }

    @Override
    public List<ActivityStudentDownloadExt> getUploadTemplate(Long activityId,Long schoolId) {
        List<ActivityStudentDownloadExt> activityStudentDownloadExtList = new ArrayList<>();
        ActivityDetailResp activityDetailResp = actMapper.getActivityDetail(activityId);
        if (activityDetailResp == null) {
            return new ArrayList<>();
        }
        if (StringUtils.isEmpty(activityDetailResp.getGrade())) {
            // 从班级查询学生
            List<ClassActivity> classActivityList = classActivityMapper.getClassActivityByActivityId(activityId);
            if (classActivityList != null && classActivityList.size() != 0) {
                List<StuDTO> stuDTOTotalList = new ArrayList<>();
                for (ClassActivity classActivity : classActivityList) {
                    Long classId = classActivity.getClassId();
                    List<StuDTO> stuDTOList = stuMapper.getStuListByClassId(classId);
                    stuDTOTotalList.addAll(stuDTOList);
                }

                if (stuDTOTotalList.size() != 0) {
                    for (StuDTO stuDTO : stuDTOTotalList) {
                        ActivityStudentDownloadExt activityStudentDownloadExt = new ActivityStudentDownloadExt();
                        // 拷贝活动相同属性值
                        BeanUtils.copyProperties(activityDetailResp, activityStudentDownloadExt);
                        // 拷贝学生相同属性值
                        BeanUtils.copyProperties(stuDTO, activityStudentDownloadExt);
                        ClassDTO classDTO = clazzMapper.selectOneById(Long.valueOf(stuDTO.getClassId()));
                        // 拷贝班级相同属性值
                        if (classDTO != null) {
                            BeanUtils.copyProperties(classDTO, activityStudentDownloadExt);
                        }
                        TeaDTO teaDTO = teaMapper.getTeacherById(classDTO.getMonitorId());
                        if (teaDTO != null) {
                            teaDTO.setMonitorName(teaDTO.getTeacherName());
                            BeanUtils.copyProperties(teaDTO, activityStudentDownloadExt);
                        }
                        activityStudentDownloadExtList.add(activityStudentDownloadExt);
                    }
                }
            }
        } else {
            // TODO 从年级查询学生
            List<StuDTO> stuDTOTotalList = new ArrayList<>();
            List<StuDTO> stuDTOList = stuMapper.getStuListBySchoolIdAndGradeName(schoolId, activityDetailResp.getGrade());
            stuDTOTotalList.addAll(stuDTOList);

            if (stuDTOTotalList.size() != 0) {
                for (StuDTO stuDTO : stuDTOTotalList) {
                    ActivityStudentDownloadExt activityStudentDownloadExt = new ActivityStudentDownloadExt();
                    // 拷贝活动相同属性值
                    BeanUtils.copyProperties(activityDetailResp, activityStudentDownloadExt);
                    // 拷贝学生相同属性值
                    BeanUtils.copyProperties(stuDTO, activityStudentDownloadExt);
                    ClassDTO classDTO = clazzMapper.selectOneById(Long.valueOf(stuDTO.getClassId()));
                    // 拷贝班级相同属性值
                    if (classDTO != null) {
                        BeanUtils.copyProperties(classDTO, activityStudentDownloadExt);
                    }
                    TeaDTO teaDTO = teaMapper.getTeacherById(classDTO.getMonitorId());
                    if (teaDTO != null) {
                        teaDTO.setMonitorName(teaDTO.getTeacherName());
                        BeanUtils.copyProperties(teaDTO, activityStudentDownloadExt);
                    }
                    activityStudentDownloadExtList.add(activityStudentDownloadExt);
                }
            }
        }
        return activityStudentDownloadExtList;
    }


    @Override
    public void uploadStuIntoDB(MultipartFile file) {
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EasyExcel.read(is, ActivityStudentDownloadExt.class, new ActStuScoreExcelListener(this)).sheet().doRead();
    }

    @Override
    public void save(List<ActivityStudentDownloadExt> activityStudentDownloadExtList) {
        if (activityStudentDownloadExtList == null || activityStudentDownloadExtList.size() == 0) {
            return;
        }
        for (ActivityStudentDownloadExt ext : activityStudentDownloadExtList) {
            Long actId = ext.getActivityId();
            String stuNum = ext.getStudentNum();
            Float score = ext.getStudentScore();
            if(score==null) {
                continue;
            }
            activityStudentMapper.insertOne(new ActivityStudent(actId, stuNum, score));
        }
    }


}
