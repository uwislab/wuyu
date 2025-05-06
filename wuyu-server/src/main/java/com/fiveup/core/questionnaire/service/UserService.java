package com.fiveup.core.questionnaire.service;

import com.fiveup.core.questionnaire.dto.Identity;
import com.fiveup.core.questionnaire.dto.ModifyPwdData;
import com.fiveup.core.questionnaire.dto.ModifyUserInfo;
import com.fiveup.core.questionnaire.dto.User;
import com.fiveup.core.questionnaire.vo.ResponseVO;
import com.fiveup.core.questionnaire.vo.UserVO;

import java.util.List;

/**
 * @ClassName: UserService
 * @Date: 2021/4/26 13:42
 * @author zhaomin
 */
public interface UserService {

    ResponseVO addUser(UserVO userVO);

    User login(User user);

    String loginAndSignJwtToken(User user);

    List<Identity> getIdentity();

    int modifyPwd(ModifyPwdData modifyPwdData);

    int modifyInfo(ModifyUserInfo modifyUserInfo);
}
