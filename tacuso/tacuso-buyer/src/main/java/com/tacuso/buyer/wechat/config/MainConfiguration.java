package com.tacuso.buyer.wechat.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信公众号主配置
 * <p>
 * Created by bjliumingbo on 2017/5/12.
 *
 * @author FirenzesEagle
 * @author BinaryWang
 */
@Configuration
public class MainConfiguration {
	@Value("#{wxProperties.appId}")
	private String appId;

	@Value("#{wxProperties.appSecret}")
	private String appSecret;

	@Value("#{wxProperties.token}")
	private String token;

	@Value("#{wxProperties.aesKey}")
	private String aesKey;

	@Bean
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(this.appId);
		configStorage.setSecret(this.appSecret);
		configStorage.setToken(this.token);
		configStorage.setAesKey(this.aesKey);
		return configStorage;
	}

	@Bean
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
}
