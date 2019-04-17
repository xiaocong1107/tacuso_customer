package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.UserMapper;
import com.tacuso.admin.entity.User;
import com.tacuso.admin.service.UserService;
import com.tacuso.admin.vo.UserCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    public UserMapper userMapper;


    @Override
    public Page<User> queryAllUser(UserCommonVo userCommonVo, int pageNum, int pageSize) {


        Page <User> page = new Page<>(pageNum,pageSize);
        List<User> userList =  userMapper.queryUserList(userCommonVo,page);
        return page.setRecords( userList);

    }

    @Override
    public List<User> getAllUser(UserCommonVo userCommonVo) {


        List<User> userList =  userMapper.getAllUser(userCommonVo);

        return userList;
    }

}
