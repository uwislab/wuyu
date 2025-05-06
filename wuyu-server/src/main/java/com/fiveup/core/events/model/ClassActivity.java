package com.fiveup.core.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 钟承远
 * @date 2022/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassActivity {
    private Long id;
    private Long activityId;
    private Long classId;
}
