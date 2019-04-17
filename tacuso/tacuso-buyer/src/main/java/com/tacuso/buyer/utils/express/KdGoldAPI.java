package com.tacuso.buyer.utils.express;

import java.io.BufferedReader;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.tacuso.buyer.vo.PageData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat; 

/**
*
* 快递鸟在线下单接口
*
* @技术QQ: 4009633321
* @技术QQ群: 200121393
* @see: http://www.kdniao.com/api-order
* @copyright: 深圳市快金数据技术服务有限公司
* 
* ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
*/

public class KdGoldAPI {
	
	//电商ID * 是自己申请的商户ID和api的秘钥
	private String EBusinessID="1334901";
	//电商加密私钥，快递鸟提供，注意保管，不要泄漏
	private String AppKey="d8cf37e1-dfb6-4d1b-a5da-65501e108e24";
	//请求url 测试
//	private String ReqURL="http://testapi.kdniao.com:8081/api/EOrderService";	
	//下单正式请求url
	private String ReqURL = "http://api.kdniao.com/api/OOrderService";
	//取消正式请求url
	private String CanelURL = "https://api.kdniao.com/api/EOrderService";	
	

//	public static void main(String[] args) throws Exception {
//		KdGoldAPI api = new KdGoldAPI();
//		String requestData ="";
//		Long TimeStsamp =12121212l;
//		String username ="志聪";
//		String phone ="15800030967";
//		String ProvinceName ="广东";
//		String CityName ="广州";
//		String ExpAreaName ="番禺区";
//		String Address ="市桥";
//		
//		String result = api.orderOnlineByJson(TimeStsamp, username, phone,ProvinceName,CityName,ExpAreaName, Address);
//		System.out.println("result==="+result);
//	}
	
	/**
     * Json方式 在线下单
	 * @throws Exception 
     */
	public String orderOnlineByJson(
			String StartDate,
			String EndDate, 
			Long TimeStsamp,
			String username,
			String phone,
			String ProvinceName,
			String CityName,
			String ExpAreaName,
			String Address,
			String shopName,
			String shopPhone,
			String takeProvinceName,
			String takeCityName,
			String takeExpAreaName,
			String takeAddress) throws Exception{
		
		
//		String requestData= "{'OrderCode': "+String.valueOf(TimeStsamp)+"," +   //订单编码 （必填）
//                                  "'ShipperCode':'SF'," +  //快递公司编码
//                                  "'PayType':2," + //邮费方式：1现付 2到付 3月结 4第三方支付
//                                  "'ExpType':1," +//快递类型 1标准快递
////                                  "'StartDate':'2018-08-30 00:00:00'," +//取件时间
////                                  "'EndDate':'2018-08-30 00:00:00'," +//取件时间
////                                  "'Remark':'上门前请联系、带包装袋'" +
//                                  "'Sender':" +  //发件人
//                                  "{" +
//                                  "'Name':"+username+",'Mobile':"+phone+",'ProvinceName':"+ProvinceName+",'CityName':"+CityName+",'ExpAreaName':"+ExpAreaName+",'Address':"+Address+"}," +
//                                  "'Receiver':" +   //收件人
//                                  "{" +
//                                  "'Name':'有衣宅送','Mobile':'15017507788','ProvinceName':'广东省','CityName':'广州市','ExpAreaName':'番禺区','Address':'石楼镇南环路132号尚上名筑C区2栋1104'}," +
//                                  "'Commodity':" +
//                                  "[{" +
//                                  "'GoodsName':'衣服','Goodsquantity':3,'GoodsWeight':2.0}]" +
////                                  "'Remark':'上门前请联系、带包装袋'" +
//                                  "}";

		
		String requestData= "{'OrderCode': '"+TimeStsamp+"'," +   //订单编码 （必填）
                "'ShipperCode':'SF'," +  //快递公司编码
                "'PayType':2," + //邮费方式：1现付 2到付 3月结 4第三方支付
                "'ExpType':1," +//快递类型 1标准快递
	            "'StartDate':'"+StartDate+"'," +//取件时间
	            "'EndDate':'"+EndDate+"'," +//取件时间
	            "'Remark':'上门前请联系、带包装袋'," +
                "'Sender':" +  //发件人
                "{" +
                "'Name':'"+username+"','Mobile':'"+phone+"','ProvinceName':'"+ProvinceName+"','CityName':'"+CityName+"','ExpAreaName':'"+ExpAreaName+"','Address':'"+Address+"'}," +
                "'Receiver':" +   //收件人
                "{" +
                "'Name':'"+shopName+"','Mobile':'"+shopPhone+"','ProvinceName':'"+takeProvinceName+"','CityName':'"+takeCityName+"','ExpAreaName':'"+takeExpAreaName+"','Address':'"+takeAddress+"'}," +
                "'Commodity':" +
                "[{" +
                "'GoodsName':'衣服','Goodsquantity':3,'GoodsWeight':2.0}]" +
                "}";
//                "'Commodity':" +
//                "[{" +
//                "'GoodsName':'鞋子'," +
//                "'Goodsquantity':1," +
//                "'GoodsWeight':1.0}]" +
//                "}";

		System.out.println("orderOnlineByJson---requestData====="+requestData);
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1001");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result=sendPost(ReqURL, params);	
		
		//根据公司业务处理返回的信息......
		
		return result;
	}
			
	
	/**
     * Json方式 取消订单
	 * @throws Exception 
     */
	public String cancelOrder(String StartDate,String EndDate, Long TimeStsamp,String username,String phone,String ProvinceName,String CityName,String ExpAreaName,String Address) throws Exception{
		
		
//		String requestData= "{'OrderCode': "+String.valueOf(TimeStsamp)+"," +   //订单编码 （必填）
//                                  "'ShipperCode':'SF'," +  //快递公司编码
//                                  "'PayType':2," + //邮费方式：1现付 2到付 3月结 4第三方支付
//                                  "'ExpType':1," +//快递类型 1标准快递
////                                  "'StartDate':'2018-08-30 00:00:00'," +//取件时间
////                                  "'EndDate':'2018-08-30 00:00:00'," +//取件时间
////                                  "'Remark':'上门前请联系、带包装袋'" +
//                                  "'Sender':" +  //发件人
//                                  "{" +
//                                  "'Name':"+username+",'Mobile':"+phone+",'ProvinceName':"+ProvinceName+",'CityName':"+CityName+",'ExpAreaName':"+ExpAreaName+",'Address':"+Address+"}," +
//                                  "'Receiver':" +   //收件人
//                                  "{" +
//                                  "'Name':'有衣宅送','Mobile':'15017507788','ProvinceName':'广东省','CityName':'广州市','ExpAreaName':'番禺区','Address':'石楼镇南环路132号尚上名筑C区2栋1104'}," +
//                                  "'Commodity':" +
//                                  "[{" +
//                                  "'GoodsName':'衣服','Goodsquantity':3,'GoodsWeight':2.0}]" +
////                                  "'Remark':'上门前请联系、带包装袋'" +
//                                  "}";

		
		String requestData= "{'OrderCode': '"+TimeStsamp+"'," +   //订单编码 （必填）
                "'ShipperCode':'SF'," +  //快递公司编码
                "'PayType':2," + //邮费方式：1现付 2到付 3月结 4第三方支付
                "'ExpType':1," +//快递类型 1标准快递
	            "'StartDate':'"+StartDate+"'," +//取件时间
	            "'EndDate':'"+EndDate+"'," +//取件时间
	            "'Remark':'上门前请联系、带包装袋'," +
                "'Sender':" +  //发件人
                "{" +
                "'Name':'"+username+"','Mobile':'"+phone+"','ProvinceName':'"+ProvinceName+"','CityName':'"+CityName+"','ExpAreaName':'"+ExpAreaName+"','Address':'"+Address+"'}," +
                "'Receiver':" +   //收件人
                "{" +
                "'Name':'有衣宅送','Mobile':'15017507788','ProvinceName':'广东省','CityName':'广州市','ExpAreaName':'番禺区','Address':'广州市番禺区大龙街富怡路东方白云花园雅晴居3号楼首层10号之三铺'}," +
                "'Commodity':" +
                "[{" +
                "'GoodsName':'衣服','Goodsquantity':3,'GoodsWeight':2.0}]" +
                "}";
//                "'Commodity':" +
//                "[{" +
//                "'GoodsName':'鞋子'," +
//                "'Goodsquantity':1," +
//                "'GoodsWeight':1.0}]" +
//                "}";

		
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1001");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result=sendPost(CanelURL, params);	
		
		//根据公司业务处理返回的信息......
		
		return result;
	}
	
	/**
     * MD5加密
     * @param str 内容       
     * @param charset 编码方式
	 * @throws Exception 
     */
	@SuppressWarnings("unused")
	private String MD5(String str, String charset) throws Exception {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(str.getBytes(charset));
	    byte[] result = md.digest();
	    StringBuffer sb = new StringBuffer(32);
	    for (int i = 0; i < result.length; i++) {
	        int val = result[i] & 0xff;
	        if (val <= 0xf) {
	            sb.append("0");
	        }
	        sb.append(Integer.toHexString(val));
	    }
	    return sb.toString().toLowerCase();
	}
	
	/**
     * base64编码
     * @param str 内容       
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException 
     */
	private String base64(String str, String charset) throws UnsupportedEncodingException{
		String encoded = Base64.encode(str.getBytes(charset));
		return encoded;    
	}	
	
	@SuppressWarnings("unused")
	private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
		String result = URLEncoder.encode(str, charset);
		return result;
	}
	
	/**
     * 电商Sign签名生成
     * @param content 内容   
     * @param keyValue Appkey  
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException ,Exception
	 * @return DataSign签名
     */
	@SuppressWarnings("unused")
	private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
	{
		if (keyValue != null)
		{
			return base64(MD5(content + keyValue, charset), charset);
		}
		return base64(MD5(content, charset), charset);
	}
	
	 /**
     * 向指定 URL 发送POST方法的请求     
     * @param url 发送请求的 URL    
     * @param params 请求的参数集合     
     * @return 远程资源的响应结果
     */
	@SuppressWarnings("unused")
	private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;        
        StringBuilder result = new StringBuilder(); 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数            
            if (params != null) {
		          StringBuilder param = new StringBuilder(); 
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }	        	  
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());		        	  
		        	  System.out.println(entry.getKey()+":"+entry.getValue());
		          }
		          System.out.println("param:"+param.toString());
		          out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
