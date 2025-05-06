package com.fiveup.core.classManage.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassPageResp {
    private int id;
    private String grade;
    private int classType;
    private String className;
    private int monitorId;
    private int gradeId;
    private int gender;
    private String realName;
    private String contactInfo;
    private int StudentNumber;
}
