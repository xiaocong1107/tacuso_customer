package com.tacuso.buyer.sms;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * 短信工具
 * @author administrator
 */
public class SmsUtil {
	
	private static final Logger logger= LoggerFactory.getLogger(SmsUtil.class);

	/**
	 * 默认编码的格式
	 */
	private static final String CHARSET="GBK";

	private static final String SignName="有衣宅送";

	/**
	 * 产品名称
	 */
	static final String product = "Dysmsapi";


	/**
	 * 请求的网关接口
	 */
	static final String domain = "dysmsapi.aliyuncs.com";

	/**
	 * accessToken
	 */
	static final String accessKeyId = "LTAI81rqVQQ9qsT2";

	static final String accessKeySecret = "LnxDw3v0hIIOSuVGNCMpeqoCTScPoR";


	private static SmsUtil smsUtil=new SmsUtil();
	
	private SmsUtil() {
		
	}
	
	public static SmsUtil getInstance() {
		if(smsUtil==null)
		{
			smsUtil=new SmsUtil();
		}
		return smsUtil;
	}
	
	/*
	 * 短信发送API
	 */
	public boolean sendSms(SmsMessage smsMessage,String environment) {

		boolean result=true;
		
		//测试环境就直接返回success
		if(!"product".equalsIgnoreCase(environment)) {
			return result;
		}

		//可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		//初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		}catch ( ClientException e){
			logger.error("{}",e);
		}

		IAcsClient acsClient = new DefaultAcsClient(profile);

		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号
		request.setPhoneNumbers(smsMessage.getAccount());
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(SignName);
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(smsMessage.getTemplate());
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(JSON.toJSONString(smsMessage.getParam()));

		logger.info(JSON.toJSONString(request));

		try {
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if(sendSmsResponse.getCode().equals("OK")){
				return true;
			}else{
				logger.info("短信发送失败 {}",sendSmsResponse.toString());
				return false;
			}

		} catch (ClientException e) {
			logger.error("{}",e);
		}

		return false;

	}

}