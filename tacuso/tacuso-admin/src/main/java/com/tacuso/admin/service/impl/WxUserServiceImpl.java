package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.WxUserMapper;
import com.tacuso.admin.entity.WxUser;
import com.tacuso.admin.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxUserServiceImpl  extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public Page<WxUser> queryAllWxUser(WxUser wxUser, int pageNum, int pageSize) {

        Page<WxUser> wxUserPage = new Page<WxUser>(pageNum,pageSize);
        List<WxUser> wxUserList = wxUserMapper.queryWxUserList(  wxUser , wxUserPage);
        return wxUserPage.setRecords(wxUserList);
    }
}
