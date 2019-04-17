package com.tacuso.buyer.dao;

import org.apache.ibatis.annotations.Param;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.Quartz;
import com.tacuso.buyer.entity.SendWechatLog;

public interface SendWechatLogMapper extends SuperMapper<SendWechatLog> {

    public Integer createSendWechatLog(@Param("SendWechatLog") SendWechatLog SendWechatLog);

    public SendWechatLog findSendWechatLog(@Param("SendWechatLog") SendWechatLog SendWechatLog);
    
    public Quartz getQuartz(@Param("quartz") Quartz quartz);
}