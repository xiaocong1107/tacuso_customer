package com.tacuso.admin.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tacuso.admin.common.SuperMapper;
import com.tacuso.admin.entity.WxUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxUserMapper extends SuperMapper<WxUser> {

    Integer getWxUserCount(@Param("wxUser") WxUser wxUser);

    List<WxUser> queryWxUserList(@Param("wxUser") WxUser wxUser , @Param("page") Page page);


}
