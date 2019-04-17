package com.tacuso.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.tacuso.admin.entity.WxUser;

public interface WxUserService extends IService<WxUser> {

    public Page<WxUser> queryAllWxUser(WxUser wxUser, int pageNum , int  pageSize);

}
