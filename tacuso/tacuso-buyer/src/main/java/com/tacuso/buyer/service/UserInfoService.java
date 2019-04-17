package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {

    public Integer createUserInfo(UserInfo userInfo);

    public Integer uploadFullbodyShot(String fullbody_shot,Integer uid,Integer wx_uid);

    public UserInfo getUserInfoByUid(Integer uid);
    
    public Integer  updateUserData(Integer uid);


}
