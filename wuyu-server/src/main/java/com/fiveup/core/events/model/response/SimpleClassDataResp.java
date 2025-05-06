package com.fiveup.core.events.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleClassDataResp {
    private List<SimpleClassResp> classList;
}
