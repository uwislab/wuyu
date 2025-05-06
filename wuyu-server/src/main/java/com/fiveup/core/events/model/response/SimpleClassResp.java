package com.fiveup.core.events.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 钟承远
 * @date 2022/3/29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleClassResp {
    private String className;
    private Long classId;
}
