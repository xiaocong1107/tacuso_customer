package com.tacuso.buyer.dao;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.WxUser;
import org.apache.ibatis.annotations.Param;

public interface WxUserMapper extends SuperMapper<WxUser>{

     WxUser getWxUserByOpenId(@Param("open_id") String open_id);


}
