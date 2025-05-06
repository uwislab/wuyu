package com.fiveup.core.demonstrate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class qianDuan {
    private String name;
    private int[] data = new int[5];
    private String type;
    private boolean smooth;
}

