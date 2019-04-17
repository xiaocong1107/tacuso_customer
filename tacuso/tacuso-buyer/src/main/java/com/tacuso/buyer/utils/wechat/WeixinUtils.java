package com.tacuso.buyer.utils.wechat;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;


/**
* 微信交互工具类
* 
* @author x86
* 
*/
public class WeixinUtils {
	
	public static String access_token = null;	
	
	public static long expires_in = 0;
	
	// 临时二维码  
	private final static String QR_SCENE = "QR_SCENE";  
	// 永久二维码  
	private final static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";  
	// 永久二维码(字符串)  
	private final static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";  
	
	
	/**
	 * 获取access_token
	 * 
	 * @param access_token
	 *     
	 * @return
	 * @throws Exception 
	 */
	public static String getAtoken() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject =null;

//			String appid = Tools.readTxtFile(Const.WEIXINAPPID);
//			String appsecret = Tools.readTxtFile(Const.WEIXINAPPSERCRET);
			
//			String appid = "wxbf32df2c577e8810";
//			String appsecret = "fd0fefa78c3e38080aec63a1cf6edf94";
			String appid = "wx5b9d03b9aec25148";
			String appsecret = "db4954ba4fd5cec55965f19cd7e13db5";
			String requestUrl=WechatConsts.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			jsonObject= httpRequst(requestUrl, "GET", null);
		return jsonObject.getString("access_token");
	}
	
	/**
	 * 服务受理通知
	 * 
	 * @param code
	 *   
	 * @return
	 */
	public static JSONObject serviceAcceptance(String access_token,String openId,String name,String date) {
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject =null;
		try{
			String requestUrl=WechatConsts.send_industry_url.replace("ACCESS_TOKEN", access_token);
			String jsonStr = "{\"touser\":\""+openId+"\", \"template_id\": \""+MessageTemplate.service_acceptance+"\", \"url\": \"http://www.tacuso.com/box/mybox\", "
					+ "\"data\":{\"first\":{\"value\":\"亲爱的"+name+"，我们即将为你准备新的盒子！在此之前，你可提出本次的款式要求和建议，我们将参考你的反馈给你最佳的搭配推荐哦，点击看看～！\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\"单次服务/会员免押金服务\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\""+date+"\",\"color\":\"#173177\"},\"remark\":{\"value\":\"点击体验有衣宅送服务\",\"color\":\"#FF0000\"}}}";
			jsonObject = httpRequst(requestUrl, "POST", jsonStr);
		} catch(Exception e){
	
		}
			return jsonObject;
	}
	
	/**
	 * 会员加入提醒
	 * 
	 * @param code
	 *   
	 * @return
	 */
	public static JSONObject memberJoin(String access_token,String openId,String name,String date,String phone) {
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject =null;
		try{
			String requestUrl=WechatConsts.send_industry_url.replace("ACCESS_TOKEN", access_token);
			String jsonStr = "{\"touser\":\""+openId+"\", \"template_id\": \""+MessageTemplate.member_join+"\", \"url\": \"http://www.tacuso.com/usercenter/index\", "
					+ "\"data\":{\"first\":{\"value\":\"亲爱的"+name+"，已收到你的个人穿衣档案！现在开通会员，即可收取有衣盒子：4件精选衣服和为你定制的穿衣搭配建议。1年6次服务，新衣服定期送到家，先试后买，不满意可退 (来回包邮)。                  \",\"color\":\"#173177\"},\"keyword1\":{\"value\":\""+phone+"          \",\"color\":\"#173177\"},\"keyword2\":{\"value\":\""+date+"       \",\"color\":\"#173177\"},\"remark\":{\"value\":\"点击体验有衣宅送服务\",\"color\":\"#FF0000\"}}}";
			jsonObject = httpRequst(requestUrl, "POST", jsonStr);
		} catch(Exception e){
	
		}
			return jsonObject;
	}
	
	
	
	/**
	 * 资料完善通知
	 * 
	 * @param code
	 *   
	 * @return
	 */
	public static JSONObject memberInfoComplete(String access_token,String openId,String name,String date) {
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject =null;
		try{
			String requestUrl=WechatConsts.send_industry_url.replace("ACCESS_TOKEN", access_token);
			String jsonStr = "{\"touser\":\""+openId+"\", \"template_id\": \""+MessageTemplate.member_info_complete+"\", \"url\": \"http://www.tacuso.com/page/couponIndex\", "
					+ "\"data\":{\"first\":{\"value\":\"亲爱的有衣宅送会员，你的个人资料未完整，注册尚未完成。注册未完成的会员将不会收到有衣盒子，请点击继续填写个人资料并完成注册。\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\""+name+"\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\"注册未完成\",\"color\":\"#173177\"},\"remark\":{\"value\":\"点击完成注册\",\"color\":\"#FF0000\"}}}";
			jsonObject = httpRequst(requestUrl, "POST", jsonStr);
		} catch(Exception e){
	
		}
			return jsonObject;
	}
	
	
	/**
	 * 服务到期提醒
	 * 
	 * @param code
	 *   
	 * @return
	 */
	public static JSONObject serviceExpire(String access_token,String openId,String name,String orderNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject =null;
		try{
			String requestUrl=WechatConsts.send_industry_url.replace("ACCESS_TOKEN", access_token);
			String jsonStr = "{\"touser\":\""+openId+"\", \"template_id\": \""+MessageTemplate.service_expire+"\", \"url\": \"http://www.tacuso.com/box/mybox\", "
					+ "\"data\":{\"first\":{\"value\":\"亲爱的"+name+"，你的3天试穿时间即将到期，请尽快试穿盒子里的衣服，之后你可留下喜欢的并支付，其余衣服可免费预约寄回。请于归还日前预约取件，如已寄回请忽略。\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\""+orderNo+"\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\"有衣盒子\",\"color\":\"#173177\"},\"keyword3\":{\"value\":\"剩余1天\",\"color\":\"#173177\"},\"remark\":{\"value\":\"点击操作本次盒子\",\"color\":\"#FF0000\"}}}";
			jsonObject = httpRequst(requestUrl, "POST", jsonStr);
		} catch(Exception e){
	
		}
			return jsonObject;
	}
	
	
	/**
	 * 订单完成通知
	 * 
	 * @param code
	 *   
	 * @return
	 */
	public static JSONObject orderComplete(String access_token,String openId,String name,String date) {
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject =null;
		try{
			String requestUrl=WechatConsts.send_industry_url.replace("ACCESS_TOKEN", access_token);
			String jsonStr = "{\"touser\":\""+openId+"\", \"template_id\": \""+MessageTemplate.order_complete+"\", \"url\": \"http://www.tacuso.com/usercenter/index\", "
					+ "\"data\":{\"first\":{\"value\":\"亲爱的"+name+"，你本次的订单已完成！根据你这次的评价反馈，下次的推荐将更加准确，敬请期待下一个盒子！\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\"有衣盒子\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\""+date+"\",\"color\":\"#173177\"},\"remark\":{\"value\":\"点击查看下次盒子时间\",\"color\":\"#173177\"}}}";
			jsonObject = httpRequst(requestUrl, "POST", jsonStr);
		} catch(Exception e){
	
		}
			return jsonObject;
	}
	
	public static JSONObject httpRequst(String requestUrl,String requetMethod,String outputStr){
		JSONObject jsonobject=null;
		StringBuffer buffer=new StringBuffer();
		try
		{
			//创建SSLContext对象，并使用我们指定的新人管理器初始化
			TrustManager[] tm={new MyX509TrustManager()};
			SSLContext sslcontext=SSLContext.getInstance("SSL","SunJSSE");
			sslcontext.init(null, tm, new java.security.SecureRandom());
			//从上述SSLContext对象中得到SSLSocktFactory对象
			SSLSocketFactory ssf=sslcontext.getSocketFactory();
			
			URL url=new URL(requestUrl);
			HttpsURLConnection httpUrlConn=(HttpsURLConnection)url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			//设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requetMethod);
			
			if("GET".equalsIgnoreCase(requetMethod))
				httpUrlConn.connect();
			
			//当有数据需要提交时
			if(null!=outputStr)
			{
			OutputStream outputStream=httpUrlConn.getOutputStream();
			//注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
			}
			
			//将返回的输入流转换成字符串
			InputStream inputStream=httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			
			
			String str=null;
			while((str = bufferedReader.readLine()) !=null)
			{ 
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			//释放资源
			inputStream.close();
			inputStream=null;
			httpUrlConn.disconnect();
			jsonobject=JSONObject.fromObject(buffer.toString());
		}
		catch (ConnectException ce) {
			// TODO: handle exception
		}
		catch (Exception e) {  
		}
		return jsonobject;
	}
	
}