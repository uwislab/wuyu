package com.fiveup.core.noticeBooklet.domain.vo;

import lombok.Data;

@Data
public class NoticeBookletVo {
    private String studentName;
    private Integer studentClassNumber;
    private Integer studentGrade;
    private Integer sDeyu;
    private Integer sZhiyu;
    private Integer sTiyu;
    private Integer sMeiyu;
    private Integer sLaoyu;
    private Integer pDeyu;
    private Integer pZhiyu;
    private Integer pTiyu;
    private Integer pMeiyu;
    private Integer pLaoyu;
    private String comment;
}
