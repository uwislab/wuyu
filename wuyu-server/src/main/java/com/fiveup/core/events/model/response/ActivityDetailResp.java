package com.fiveup.core.events.model.response;

import com.fiveup.core.events.model.ext.ActivityStudentDownloadExt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetailResp {
    private String activityName;
    private Long activityId;
    private String classId;
    private List<String> classNameList;
    private String activityContent;
    private Long teacherId;
    private String teacherName;
    private String activityPlace;
    private Integer activityState;
    private Integer activityType;
    private Integer activityAspect;
    private Float activityScore;
    private List<String> activityPictureList;
    private String teacherComment;
    private String grade;
    private List<ActivityStudentDownloadExt> studentScoreList;
}
