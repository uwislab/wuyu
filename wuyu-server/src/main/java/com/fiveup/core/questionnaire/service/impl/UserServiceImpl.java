package com.fiveup.core.questionnaire.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fiveup.core.questionnaire.dto.Identity;
import com.fiveup.core.questionnaire.dto.ModifyPwdData;
import com.fiveup.core.questionnaire.dto.ModifyUserInfo;
import com.fiveup.core.questionnaire.dto.User;
import com.fiveup.core.questionnaire.mapper.UserMapper;
import com.fiveup.core.questionnaire.service.UserService;
import com.fiveup.core.questionnaire.vo.ResponseVO;
import com.fiveup.core.questionnaire.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: UserServiceImpl
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 13:42
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Service
public class UserServiceImpl implements UserService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseVO addUser(UserVO userVO) {
        try {
            userMapper.addUser(userVO);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
    }

    @Override
    public User login(User user) {
        return userMapper.getUserByID(user);
    }

    @Override
    public String loginAndSignJwtToken(User user) {
        User queried = userMapper.getUserByID(user);
        if (queried != null &&
            !queried.getUsername().equals(user.getUsername())) return null;

        return JWT.create()
                .withAudience(user.getUsername())
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    @Override
    public List<Identity> getIdentity() {
        List<Identity> identityList;
        identityList = userMapper.getIdentity();
        return identityList;
    }

    @Override
    public int modifyPwd(ModifyPwdData modifyPwdData) {
        int i = userMapper.modifyPwd(modifyPwdData.getNewpass(), modifyPwdData.getUsername());
        return i;
    }

    @Override
    public int modifyInfo(ModifyUserInfo modifyUserInfo) {
        int i = userMapper.modifyInfo(modifyUserInfo.getUsername(), modifyUserInfo.getPhone(), modifyUserInfo.getGender(), modifyUserInfo.getNickname());
        return i;
    }
}
