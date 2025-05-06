package com.fiveup.core.events.service;

import com.fiveup.core.events.model.ActivityStudent;
import com.fiveup.core.events.model.ext.ActivityStudentDownloadExt;
import com.fiveup.core.events.model.ext.ActivityStudentScoreTemplateExt;
import com.fiveup.core.events.model.request.AlterStuActReq;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/26
 */

public interface ActivityStudentService {


    CommonResponse checkInActForStu(Long activityId, Float score, Long schoolId);

    boolean alterOne(ActivityStudent activityStudent);

    boolean insertOne(ActivityStudent activityStudent);

    List<ActivityStudentDownloadExt> getActivityStudentExtList(Long activityId,Long schoolId);

    List<ActivityStudentScoreTemplateExt> getActStudentTemplate(Long activityId);

    List<ActivityStudentDownloadExt> getUploadTemplate(Long activityId,Long schoolId);

    void uploadStuIntoDB(MultipartFile file);

    void save(List<ActivityStudentDownloadExt> activityStudentDownloadExtList);


}
