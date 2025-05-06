package com.fiveup.core.mainpage.service;


import com.fiveup.core.mainpage.domain.po.WebUser;
import com.fiveup.core.mainpage.domain.vo.WebUserVo;


public interface MainPageService {
    WebUserVo getWebUserById(String id);

    int updateWebUserById(int id, WebUser webUser);
}
