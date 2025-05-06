package com.fiveup.core.events.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureUrlListResp {
    private List<String> urlList;
}
