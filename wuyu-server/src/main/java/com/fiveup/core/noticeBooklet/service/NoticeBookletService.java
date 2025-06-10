package com.fiveup.core.noticeBooklet.service;

import com.fiveup.core.noticeBooklet.mapper.ScoreMapper;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface NoticeBookletService {

    public void exportMultipleWords(HttpServletResponse response, List<Long> ids);
}
