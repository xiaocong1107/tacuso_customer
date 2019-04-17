package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.entity.WxUser;
import com.tacuso.buyer.vo.WxUserVo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.servlet.http.HttpSession;

public interface WxUserService extends IService<WxUser> {


    User RegisterWxUser(HttpSession session, WxMpUser wxMpUser);

}
