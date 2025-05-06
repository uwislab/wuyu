package com.fiveup.core.events.model;

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
public class PictureActivity {
    private Long id;
    private Long activityId;
    private String pictureUrl;
}
