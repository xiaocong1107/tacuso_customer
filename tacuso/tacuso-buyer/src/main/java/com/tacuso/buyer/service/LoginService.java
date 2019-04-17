package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.User;

public interface LoginService extends IService<User> {
     User userLogin(String bindphone);
}
