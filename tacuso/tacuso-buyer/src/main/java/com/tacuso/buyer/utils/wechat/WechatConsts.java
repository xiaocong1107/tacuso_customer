package com.tacuso.buyer.utils.wechat;

/**
 * 项目名称：
 * @author:qqs
 * 
*/
public class WechatConsts {
	//================================================get_access_token==============================================================
	public final static String access_token_url="https://api.weixin.qq.com/cgi-bin/token?" +
			"grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//================================================get memberInfo==============================================================
	public final static String user_info_url="https://api.weixin.qq.com/cgi-bin/user/info?" +
			"access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//================================================get attention list==============================================================
	public final static String gz_url="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";
	//================================================make QRcode==============================================================
	public final static String tp_url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
	
	
	//================================================页面授权：first：get code=============================================================
	public final static String authorize_code_url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URL&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	//================================================页面授权：second：get openId=============================================================
	public final static String authorize_code_access_token="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		
	
	//================================================if attention==============================================================
	public final static String user_status_url="https://api.weixin.qq.com/cgi-bin/user/info?" +
			"access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	//================================================send message==============================================================
	public final static String send_industry_url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	//================================================under industry==============================================================
	public final static String set_industry_url="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
		
	//================================================set industry info==============================================================
	public final static String get_industry_url="https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
	
	//================================================get mould ID==============================================================
	public final static String add_industry_url="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	
	

	//==============================================================================================================
	public final static String get_member_point_update="serverName/qqsDataSync/member/updateAwardPoint";
	
}