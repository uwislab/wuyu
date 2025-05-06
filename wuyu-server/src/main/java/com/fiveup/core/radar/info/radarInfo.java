package com.fiveup.core.radar.info;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class radarInfo {
    private String itemContent;
    private int itemLevel;
    private int itemScore;
    private String preItem;
}
