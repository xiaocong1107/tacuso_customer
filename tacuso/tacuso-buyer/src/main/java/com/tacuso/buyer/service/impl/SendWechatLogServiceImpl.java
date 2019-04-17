package com.tacuso.buyer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.SendWechatLogMapper;
import com.tacuso.buyer.entity.Quartz;
import com.tacuso.buyer.entity.SendWechatLog;
import com.tacuso.buyer.service.SendWechatLogService;


@Service
public class SendWechatLogServiceImpl extends ServiceImpl<SendWechatLogMapper,SendWechatLog> implements SendWechatLogService {

    @Autowired
    private SendWechatLogMapper sendWechatLogMapper;

	@Override
	public Integer createSendWechatLog(SendWechatLog sendWechatLog) {
        Integer count = sendWechatLogMapper.createSendWechatLog(sendWechatLog);
        return  count;
	}

	
	@Override
	public SendWechatLog findSendWechatLog(SendWechatLog sendWechatLog) {
		// TODO Auto-generated method stub
		return sendWechatLogMapper.findSendWechatLog(sendWechatLog);
	}

	@Override
	public Quartz getQuartz(Quartz quartz) {
		// TODO Auto-generated method stub
		return sendWechatLogMapper.getQuartz(quartz);
	}
}
