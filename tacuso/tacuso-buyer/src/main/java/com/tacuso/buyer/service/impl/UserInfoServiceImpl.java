package com.tacuso.buyer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.UserInfoMapper;
import com.tacuso.buyer.entity.UserInfo;
import com.tacuso.buyer.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper,UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Integer createUserInfo(UserInfo userInfo) {

        List<UserInfo> userInfoList = userInfoMapper.selectUserInfoByUid(userInfo.getUid());
        if(userInfoList.isEmpty()){
            return userInfoMapper.createUserInfo(userInfo);
        }else{
            return userInfoMapper.updateUserInfo(userInfo);
        }
        
    }

    /**
     * 更新用户全身图
     * @param fullbody_shot
     * @param uid
     * @param wx_uid
     * @return
     */
    @Override
    public Integer uploadFullbodyShot(String fullbody_shot, Integer uid ,Integer wx_uid) {

        List<UserInfo> userInfoList = userInfoMapper.selectUserInfoByUid(uid);
        if(userInfoList.isEmpty()){
            return userInfoMapper.insertFullShot(fullbody_shot,uid,wx_uid);
        }else{
            return userInfoMapper.updateFullShot(fullbody_shot,uid);
        }

    }

    @Override
    public UserInfo getUserInfoByUid(Integer uid) {

        List<UserInfo> userInfoList =  userInfoMapper.selectUserInfoByUid(uid);

        if(!userInfoList.isEmpty()){
            return userInfoList.get(0);
        }else{
            return null;
        }

    }
    
 
    @Override
    public Integer updateUserData(Integer uid){
       return (int)userInfoMapper.updateUserData(uid);
    }
}
