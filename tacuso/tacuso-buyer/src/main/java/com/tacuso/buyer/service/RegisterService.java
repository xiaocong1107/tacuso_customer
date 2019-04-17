package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.User;

public interface RegisterService extends IService<User> {

    User userRegister(Integer uid,String bindphone, String area,String inviteCode);

    User changeBindphone(Integer uid,String bindphone);

    Boolean isBindPhone(String bindphone);



}
