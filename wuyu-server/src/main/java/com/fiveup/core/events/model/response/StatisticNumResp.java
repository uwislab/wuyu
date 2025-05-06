package com.fiveup.core.events.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 钟承远
 * @date 2022/4/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticNumResp {
    private Integer eventMonthTotalNum;
    private Integer toBeginEventNum;
    private Integer finishedEventNum;
    private Integer averageEventScore;
}
