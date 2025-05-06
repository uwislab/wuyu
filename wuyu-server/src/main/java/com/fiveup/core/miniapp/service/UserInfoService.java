package com.fiveup.core.miniapp.service;

import com.fiveup.core.miniapp.model.UserMini;

public interface UserInfoService {

   int bindStudent(String stuNum,Long id);

    int addParent(UserMini userMini);

    UserMini login(UserMini userMini);

    UserMini getParent(UserMini userMini);

    int updateParent(UserMini userMini);

}
