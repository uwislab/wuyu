package com.fiveup.core.remark.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
@Data
public class FeedBackQueryRequest {
       Integer pageNum;
       Integer pageSize;
       String search;
}

