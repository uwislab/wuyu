package com.fiveup.core.classManage.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassPageReq {
    private String grade;
    private String realName;
    private Integer pageNum;
    private Integer pageSize;
}
