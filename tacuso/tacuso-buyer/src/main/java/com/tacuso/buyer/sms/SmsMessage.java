package com.tacuso.buyer.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/***
 * 短信消息对象
 * 
 * @author administrator
 */
public class SmsMessage implements Serializable {
	/**
	 * 账号，目前就是手机号码，采用的是手机号码登陆
	 */
	private String account;

	/**
	 * 对应的短信模板
	 */
	private String template;

	/**
	 * 参数
	 */
	private Map<String,String> param;


	public SmsMessage() {
		super();
	}

	public SmsMessage(String account,  String template, String name , Map<String,String> param) {
		super();
		this.account = account;
		this.template = template;
		this.param = param;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "SmsMessage{" +
				"account='" + account + '\'' +
				", template='" + template + '\'' +
				", param=" + param +
				'}';
	}
}