package com.fiveup.core.login.service;

import com.fiveup.core.login.entity.User;
import com.fiveup.core.login.mapper.LoginuserMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private LoginuserMapper userMapper;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
//            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
