package com.fiveup.core.events.service.impl;

import com.fiveup.core.events.mapper.ActMapper;
import com.fiveup.core.events.mapper.ActivityClassStudentMapper;
import com.fiveup.core.events.mapper.ClassActivityMapper;
import com.fiveup.core.events.mapper.PictureActivityMapper;
import com.fiveup.core.events.model.Card;
import com.fiveup.core.events.model.ext.ActivityStudentDownloadExt;
import com.fiveup.core.events.model.request.ActivityAddReq;
import com.fiveup.core.events.model.request.ActivityEditReq;
import com.fiveup.core.events.model.ActivityClassStudent;
import com.fiveup.core.events.model.response.ActivityDetailResp;
import com.fiveup.core.events.model.response.ActivityPageResp;
import com.fiveup.core.events.model.response.StatisticNumResp;
import com.fiveup.core.events.service.ActivityClassStudentService;
import com.fiveup.core.events.service.ActivityService;
import com.fiveup.core.events.service.ActivityStudentService;
import com.fiveup.core.management.mapper.ClazzMapper;
import com.fiveup.core.management.mapper.StuMapper;
import com.fiveup.core.management.mapper.TeaMapper;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.model.DTO.TeaDTO;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.questionnaire.entity.Activity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 钟承远
 * @date 2022/3/29
 */
@Slf4j
@Service("actServiceImpl")
public class ActivityServiceImpl implements ActivityService {


    @Resource
    private ActMapper actMapper;

    @Resource
    private ClassActivityMapper classActivityMapper;

    @Resource
    private ClazzMapper clazzMapper;

    @Resource
    private TeaMapper teaMapper;

    @Resource
    private PictureActivityMapper pictureActivityMapper;

    @Resource
    private StuMapper stuMapper;

    @Resource
    private ActivityClassStudentMapper activityClassStudentMapper;

    @Resource
    private ActivityClassStudentService activityClassStudentService;


    @Override
    @Transactional(readOnly = false)
    public void addOne(ActivityAddReq activityAddReq) {
        List<StuDTO> stuDTOList = new ArrayList<>();
        if (activityAddReq.getGrade() != null && !activityAddReq.getClass().equals("")) {
            activityAddReq.setActivityType(0);
            activityAddReq.setActivityFee(String.valueOf(0));
            actMapper.insertOne(activityAddReq);
            stuDTOList = stuMapper.getStuListBySchoolIdAndGradeNameClassId(1L, activityAddReq.getGrade(), activityAddReq.getClassId());
        } else if (activityAddReq.getGrade() != null && "".equals(activityAddReq.getClassId())){
            activityAddReq.setClassId("");
            activityAddReq.setActivityType(0);
            activityAddReq.setActivityFee(String.valueOf(0));
            actMapper.insertOne(activityAddReq);
            stuDTOList = stuMapper.getStuListBySchoolIdAndGradeName(1L, activityAddReq.getGrade());
        }

        // 根据年级和班级查询学生信息
        ArrayList<ActivityClassStudent> list = new ArrayList<>();
        Long activityId = activityAddReq.getId();
        for (StuDTO stuDTO : stuDTOList) {
            ActivityClassStudent activityClassStudent = new ActivityClassStudent();
            BeanUtils.copyProperties(stuDTO, activityClassStudent);
            activityClassStudent.setClassId(String.valueOf(stuDTO.getClassId()));
            activityClassStudent.setParentPhone(stuDTO.getParentPhoneNum());
            activityClassStudent.setParentScore((float) 0);
            activityClassStudent.setTeacherScore((float) 0);
            activityClassStudent.setTotalScore((float) 0);
            activityClassStudent.setActivityId(Math.toIntExact(activityId));

            //list.add(activityClassStudent);
            activityClassStudentMapper.insert(activityClassStudent);
        }
    }

    @Override
    public PageInfo<ActivityPageResp> getActivityListByPage(String activityName,
                                                            Integer activityState,
                                                            String grade,
                                                            Integer activityType,
                                                            Integer activityAspect,
                                                            Long schoolId,
                                                            List<Integer> classIdList,
                                                            Integer pageNum,
                                                            Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityPageResp> activityPageRespList = actMapper.getActivitiesByDynamicCondition(activityName,activityState,grade,activityType,activityAspect,schoolId,classIdList);

        for (ActivityPageResp activityPageResp : activityPageRespList) {
            activityPageResp.setStartTime(activityPageResp.getStartTimeDate().getTime());
            Long activityId = activityPageResp.getActivityId();
            int studentCount = activityClassStudentService.getStudentCount(activityId);
            activityPageResp.setStudentNumber(studentCount);
            // List<String> classNameList = this.getClassNameList(activityId);
            // activityPageResp.setClassNameList(classNameList);
        }

        PageInfo<ActivityPageResp> pageInfo = new PageInfo<>(activityPageRespList);
        return pageInfo;
    }

    @Resource
    private ActivityStudentService activityStudentService;

    @Resource
    private CommonManagementService commonManagementService;

    //查询某教师的班级学生
    @Override
    public ActivityDetailResp getActivityDetail(Long activityId, Integer activityAspect) {

        //查询fu_activity表，获取该活动的全部信息
        ActivityDetailResp activityDetailResp = actMapper.getActivityDetail(activityId);

        //ActivityType 0是班级活动，1是年级活动
        if(activityDetailResp.getActivityType() == 1){
            List<String> classNameList = this.getClassNameList(activityId, activityDetailResp.getGrade());
            activityDetailResp.setClassNameList(classNameList);
        }

        //获取教师名字
        TeaDTO teaDTO = teaMapper.getTeacherById(activityDetailResp.getTeacherId());
        System.out.println(teaDTO);
        if (teaDTO != null) {
            activityDetailResp.setTeacherName(teaDTO.getTeacherName());
        }

    /*   //获取图片链接
        List<String> urlList = pictureActivityMapper.getUrlListByActivityId(activityId);
        if (urlList != null && urlList.size() != 0) {
            activityDetailResp.setActivityPictureList(urlList);
        }*/

        // 目前SchoolId只有一个 默认为 1
         List<ActivityStudentDownloadExt> activityStudentDownloadExtList = activityStudentService.getActivityStudentExtList(activityId, 1L);
         activityDetailResp.setStudentScoreList(activityStudentDownloadExtList);
        return activityDetailResp;
    }

    public List<String> getClassNameList(Long actId, String grade) {

        List<String> classNameList = null;
        //检查该活动关联了多少个班级，年级活动可以关联多个班级
        List<ActivityAddReq> activityListByGrade = actMapper.getActivityListByGrade(actId, grade);
        // List<ClassActivity> classActivityList = classActivityMapper.getClassActivityByActivityId(actId);

        if (activityListByGrade != null && activityListByGrade.size() != 0) {
            classNameList = new ArrayList<>();
            for (ActivityAddReq classActivity : activityListByGrade) {
                List<Long> classIdList = classActivity.getClassIdList();
                for (Long classId : classIdList) {
                    ClassDTO cLassDTO = clazzMapper.selectOneById(classId);
                    if (cLassDTO != null && cLassDTO.getGrade() != null && cLassDTO.getClassName() != null) {
                        classNameList.add(cLassDTO.getGrade() + cLassDTO.getClassName());
                    }
                }
            }
        }
        return classNameList;
    }

    @Override
    public void edit(ActivityEditReq activityEditReq) {
        activityEditReq.setStartTimeDate(new Date(activityEditReq.getStartTime()));
        actMapper.edit(activityEditReq);


        if (activityEditReq.getClassIdList() != null && activityEditReq.getClassIdList().size() != 0) {
            List<Long> classIdList = activityEditReq.getClassIdList();
            Long activityId = activityEditReq.getActivityId();
            classActivityMapper.deleteItemsByActivityId(activityId);
            for (Long classId : classIdList) {
                classActivityMapper.inertOne(activityId, classId);
            }
        }
    }

    @Override
    public void deleteOne(Long activityId) {
        actMapper.deleteOne(activityId);
    }


    @Override
    public StatisticNumResp getStatisticNum() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0); //获取当前月第一天
        c.set(Calendar.DAY_OF_MONTH, 1); //设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0); //将小时至0
        c.set(Calendar.MINUTE, 0); //将分钟至0
        c.set(Calendar.SECOND, 0); //将秒至0
        c.set(Calendar.MILLISECOND, 0); //将毫秒至0

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH)); //获取当前月最后一天
        c2.set(Calendar.HOUR_OF_DAY, 23); //将小时至23
        c2.set(Calendar.MINUTE, 59); //将分钟至59
        c2.set(Calendar.SECOND, 59); //将秒至59
        c2.set(Calendar.MILLISECOND, 999); //将毫秒至999

        Integer eventMonthTotalNum = actMapper.getEventMonthTotalNum(c.getTime(), c2.getTime());
        Integer toBeginEventNum = actMapper.getToBeginEventNum();
        Integer finishedEventNum = actMapper.getFinishedEventNum();
        Integer averageEventScore = actMapper.getAverageEventScore();
        StatisticNumResp statisticNumResp = new StatisticNumResp(eventMonthTotalNum, toBeginEventNum, finishedEventNum, averageEventScore);
        return statisticNumResp;
    }

    @Override
    public List<Card> getDisplayData() {
        List<Card> cards = actMapper.getCardList();
        if (cards != null && cards.size() != 0) {
            Iterator<Card> iterator = cards.iterator();// iterator安全的删除元素
            while (iterator.hasNext()) {
                Card card = iterator.next();
                Long actId = card.getActivityId();
                List<String> urlList = new ArrayList<>();
                urlList.add("https://img1.baidu.com/it/u=463439701,1254524263&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800");
                urlList.add("https://img1.baidu.com/it/u=146657627,1172731911&fm=253&fmt=auto&app=138&f=JPEG?w=890&h=500");
                urlList.add("https://img1.baidu.com/it/u=2605372625,2257936617&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=313");
                urlList.add("https://img1.baidu.com/it/u=866698258,2027745967&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1067");
                urlList.add("https://inews.gtimg.com/om_bt/O_DyA7LF3uL3wZ9zYVo8ZhI_IMUOn_NJ_Pgj2IhAuRApoAA/641");
                if (urlList == null || urlList.size() == 0) {
                    iterator.remove();
                } else {
                    Random random = new Random();
                    int i = random.nextInt(urlList.size());
                    card.setImgUrl(urlList.get(i));
                }
            }
        }
        return cards;
    }

    /**
     * 修改活动总分
     * @param averageScore
     * @param actId
     */
    @Override
    public void updateScore(float averageScore, Long actId) {
        actMapper.updateScore(averageScore, actId);
    }
}
