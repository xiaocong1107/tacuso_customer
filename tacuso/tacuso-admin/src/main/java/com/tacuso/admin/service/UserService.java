package com.tacuso.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tacuso.admin.entity.User;
import com.tacuso.admin.vo.UserCommonVo;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    
    public Page<User> queryAllUser(UserCommonVo userCommonVo, int pageNum , int pageSize);

    public List<User> getAllUser(UserCommonVo userCommonVo);
}
