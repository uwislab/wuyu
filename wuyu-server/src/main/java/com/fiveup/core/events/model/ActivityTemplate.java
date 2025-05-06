package com.fiveup.core.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 钟承远
 * @date 2022/4/28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTemplate {

    private Long id;
    private String name;
    private String regulation;
    private Integer deleted;
}
