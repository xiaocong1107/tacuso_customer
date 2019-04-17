package com.tacuso.buyer.dao;

import org.apache.ibatis.annotations.Param;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.SendSmsLog;

public interface SendSmsLogMapper extends SuperMapper<SendSmsLog> {

    public Integer createSendSmsLog(@Param("sendSmstLog") SendSmsLog sendSmsLog);

    public SendSmsLog findSendSmsLog(@Param("sendSmstLog") SendSmsLog sendSmsLog);
    
}