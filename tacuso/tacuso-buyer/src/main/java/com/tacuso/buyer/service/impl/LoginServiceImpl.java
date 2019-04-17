package com.tacuso.buyer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.UserMapper;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl  extends ServiceImpl<UserMapper,User> implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User userLogin(String bindphone) {

        return userMapper.userLogin(bindphone);

    }
}
