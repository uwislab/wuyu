// NoticeBookletWordService.java
package com.fiveup.core.noticeBooklet.service;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;

import java.io.IOException;

public interface NoticeBookletWordService {
    /**
     * 根据通知册内容生成Word文件
     * @param noticeBooklet 通知册内容
     * @throws IOException 文件操作异常
     */
    void generateWord(NoticeBooklet noticeBooklet) throws IOException;
}