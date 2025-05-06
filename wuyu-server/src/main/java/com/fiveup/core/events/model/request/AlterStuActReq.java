package com.fiveup.core.events.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 钟承远
 * @date 2022/4/26
 *
 * 修改时间 2022年8月2日17:04:48
 * 赵敏
 * 个人活动成绩修改model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterStuActReq {
    private Long activityId;
    private Long studentNum;
    private Float score;
}
