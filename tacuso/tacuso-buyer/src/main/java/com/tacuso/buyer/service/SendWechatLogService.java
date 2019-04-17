package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.Quartz;
import com.tacuso.buyer.entity.SendWechatLog;

public interface SendWechatLogService extends IService<SendWechatLog> {


	Integer createSendWechatLog( SendWechatLog sendWechatLog);
	SendWechatLog findSendWechatLog( SendWechatLog sendWechatLog);
	Quartz getQuartz( Quartz quartz);
}
