package com.tacuso.buyer.dao;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper extends SuperMapper<UserInfo> {
    public List<UserInfo> selectUserInfoByUid(@Param("uid") Integer uid);
    public Integer createUserInfo(@Param("userInfo") UserInfo userInfo);
    public Integer updateUserInfo(@Param("userInfo") UserInfo userInfo);

    public Integer insertFullShot(@Param("fullbody_shot") String fullbody_shot, @Param("uid") Integer uid , @Param("wx_uid") Integer wx_uid);
    public Integer updateFullShot(@Param("fullbody_shot") String fullbody_shot, @Param("uid") Integer uid);
    public Integer updateUserData(@Param("uid") Integer uid);
}
