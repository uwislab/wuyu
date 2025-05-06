package com.fiveup.core.miniapp.service.impl;


import com.fiveup.core.miniapp.mapper.UserInfoMapper;
import com.fiveup.core.miniapp.model.UserMini;
import com.fiveup.core.miniapp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ParentServiceImpl
 * @Author: shilin
 * @Date: 2022/9/18 16:32
 */
@Service
public class UserInfoInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public int bindStudent(String stuNum,Long id) {return userInfoMapper.bindStudent(stuNum,id);}

    @Override
    public int addParent(UserMini userMini) {
        return userInfoMapper.addParent(userMini);
    }

    @Override
    public UserMini login(UserMini userMini) {
        return  userInfoMapper.getParent(userMini);
    }

    @Override
    public UserMini getParent(UserMini userMini) {
        return  userInfoMapper.getParent(userMini);
    }

    @Override
    public int updateParent(UserMini userMini) {
        return  userInfoMapper.updateParent(userMini);
    }

}
