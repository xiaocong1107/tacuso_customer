package com.tacuso.buyer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.UserMapper;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.annotation.RegisterMapper;

@Service
public class RegisterServiceImpl  extends ServiceImpl<UserMapper,User> implements RegisterService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User userRegister(Integer uid, String bindphone,String area,String inviteCode) {

        Integer count = userMapper.registerUser(uid,bindphone,area,inviteCode);
        User user;
        if(count>0){
            user =  userMapper.getUserByBindphone(bindphone);
        }else{
            user=null;
        }

       return user;
    }

    @Override
    public User changeBindphone(Integer uid, String bindphone) {

        Integer count =  userMapper.changeBindphone(uid,bindphone);

        User user;
        if(count>0){
            user =  userMapper.getUserByBindphone(bindphone);
        }else{
            user=null;
        }

        return user;
    }


    @Override
    public Boolean isBindPhone(String bindphone) {
       User user =  userMapper.getUserByBindphone(bindphone);
       if(user!=null){
           return true;
       }else{
           return false;
       }
    }

}
