package com.tacuso.buyer.controller.wechat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.MemberPayOrder;
import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.OrderAccounts;
import com.tacuso.buyer.entity.PayOrder;
import com.tacuso.buyer.entity.SendSmsLog;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.OrderService;
import com.tacuso.buyer.service.SmsMessageService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.service.VoucherService;
import com.tacuso.buyer.sms.SmsMessage;
import com.tacuso.buyer.utils.DateUtil;
import com.tacuso.buyer.utils.HostUtils;
import com.tacuso.buyer.utils.HttpClientUtil;
import com.tacuso.buyer.utils.OrderIDGenerator;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.vo.PageData;
import com.tacuso.buyer.wechat.config.MainConfiguration;
import com.tacuso.buyer.wechat.util.WeixinUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wx_pay")
public class PayController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private WxPayConfig payConfig;
	
	@Autowired
	private WxPayService payService;

	private HttpServletRequest request;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private SmsMessageService smsMessageService;
	 
	@Autowired
	private MainConfiguration wxConfig;

	  
		/**
		 * 生成支付会员费订单
		 * @param openId
		 * @return
		 * @throws Exception 
		 */
	@RequestMapping("order")
	@ResponseBody
	public JsonResult order(HttpServletRequest request, HttpServletResponse response, @RequestParam String openId)
			throws WxPayException {

    	if(Tools.checkParams(new String[]{openId})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","openId");
    	}
    	
		this.request = request;
		uid = (Integer) request.getSession().getAttribute("uid");
		User user = userService.getUserByUid(uid);
		String orderSubject = "有衣宅送会员年卡";// 商品描述
		String merchantTradeNo = OrderIDGenerator.getOrderNumber();// 订单号
		Integer totalAmount = 9600;// 订单总金额，单位为分
		if (user.getIs_employee() == 1) {
			totalAmount = 1;
		}
		
		
		String goodsDesc = "有衣宅送会员年卡";
		String gooodsCode = "code" + merchantTradeNo;
//		String openId = (String) request.getSession().getAttribute("openId");

		WxPayMpOrderResult wxPayMpOrderResult = getPayData(merchantTradeNo, orderSubject, totalAmount, goodsDesc,
				gooodsCode, openId);
		MemberPayOrder order = new MemberPayOrder();
		order.setOrder_id(merchantTradeNo);
		order.setProduct("有衣宅送会员年卡");
		BigDecimal total_fee = new BigDecimal(totalAmount).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		order.setTotal_fee(total_fee);
		order.setUid(uid);
		order.setIs_pay(0);
		Integer count = orderService.saveMemberPayOrder(order);
		if (count != 1) {
			return new JsonResult(JsonResultCode.FAILURE, "下单失败", wxPayMpOrderResult);
		}
		
		return new JsonResult(JsonResultCode.SUCCESS, "下单成功", wxPayMpOrderResult);
	}

	
	/**
	 * 商品支付
	 * @param openId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("box")
	@ResponseBody
	public JsonResult box(@RequestParam String pay_order_no,@RequestParam String openId)
			throws WxPayException {
		this.request = request;
		PayOrder payOrder = orderService.getPayOrderByPayOrderNo(pay_order_no);
		Order order = orderService.getOrderByOrderNo(payOrder.getOrder_no());
		if(order.getOrder_status()>4){
			return new JsonResult(JsonResultCode.FAILURE, "订单已经支付", "");
		}
		
		String orderSubject = "商品支付";// 商品描述
		Integer totalAmount = payOrder.getPay_amount().multiply(new BigDecimal(100)).intValue();
//		String openId = (String) request.getSession().getAttribute("openId");

		WxPayMpOrderResult wxPayMpOrderResult = getPayData(pay_order_no, orderSubject, totalAmount, null,
				null, openId);
		return new JsonResult(JsonResultCode.SUCCESS, "下单成功", wxPayMpOrderResult);
	}
	
	
	/**
	 * 微信公众号支付接口，通过参数生成网页微信js支付参数，掉起支付界面必须参数
	 * 
	 * @param merchantTradeNo
	 *            商户订单号（必填）
	 * @param orderSubject
	 *            订单名称（必填）
	 * @param totalAmount
	 *            订单金额，单位分（必填）
	 * @param goodsDesc
	 *            商品描述（可空）
	 * @param gooodsCode
	 *            商品编码（可空） Date:2017年12月4日上午11:04:04
	 * @author 吉文剑
	 */
	private WxPayMpOrderResult getPayData(String merchantTradeNo, String orderSubject, Integer totalAmount,
			String goodsDesc, String gooodsCode, String openId) {
		WxPayMpOrderResult wxPayMpOrderResult;

		WxPayUnifiedOrderRequest prepayInfo = WxPayUnifiedOrderRequest.newBuilder().openid(openId)
				.outTradeNo(merchantTradeNo) // 设置订单号
				.totalFee(totalAmount) // 设置金额
				.body(orderSubject).tradeType(WeixinUtils.TRADE_TYPE).spbillCreateIp("113.111.80.13")
				.notifyUrl(HostUtils.getHost() + WeixinUtils.NOTIFY_URL).detail(goodsDesc).productId(gooodsCode).build();
		try {

			wxPayMpOrderResult = this.payService.createOrder(prepayInfo);
			return wxPayMpOrderResult;

		} catch (WxPayException e) {
			logger.error(e.getErrCodeDes());
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 *
	 * 会员订单回调地址
	 *
	 */
	@RequestMapping("/pay_notify")
	@ResponseBody
	public String payNotify(HttpServletRequest request, HttpServletResponse response) {

		try {
			synchronized (this) {
				String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
				WxPayOrderNotifyResult result = this.payService.parseOrderNotifyResult(xmlResult);
				// 结果正确
				String orderId = result.getOutTradeNo();
				String tradeNo = result.getTransactionId();
				String totalFee = String.valueOf(result.getTotalFee());
				String payTime = result.getTimeEnd(); //yyyy-MM-dd HH:mm:ss
				
				// 自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
				
				if (orderId.length() > 20) {
					doMemberPayOrderNotify(orderId,tradeNo,totalFee,payTime);
				} else {
					doPayOrderNotify(orderId,tradeNo,totalFee,payTime);
				}
				
				return WxPayNotifyResponse.success("处理成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WxPayNotifyResponse.fail(e.getMessage());
		}
	}
	

	/**
	 * 会员费支付订单回调处理逻辑
	 * @param orderId
	 * @param tradeNo
	 * @param totalFee
	 * @param payTime
	 * @return
	 * @throws Exception 
	 */
	private void doMemberPayOrderNotify(String orderId, String tradeNo, String totalFee, String payTime) throws Exception {
		MemberPayOrder memberPayOrder = orderService.getMemberPayOrderByOrderId(orderId);
		if (memberPayOrder == null) {
			throw new Exception("订单不存在");
		}

		BigDecimal total_fee = new BigDecimal(totalFee).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);;
		if (memberPayOrder.getTotal_fee().compareTo(total_fee) != 0) {
			throw new Exception("订单金额错误");
		}

		if (memberPayOrder.getIs_pay() == 1) {
			throw new Exception("订单已支付");
		}
		if(memberPayOrder.getIs_member()==1){ //会员：注册成功
			DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String pay_time = LocalDateTime.parse(payTime, df1).format(df2);
			memberPayOrder.setTrade_no(tradeNo);
			memberPayOrder.setIs_pay(1);
			memberPayOrder.setPay_time(pay_time);
			orderService.updateMemberPayOrder(memberPayOrder);
			Integer uid = memberPayOrder.getUid();
			User user = userService.getUserByUid(uid);
	        userService.setUserToMember(uid);//支付成功后修改成为会员
	        //注册成功后生成订单
			//获取最新订单信息，修改为'想要',并更新状态为新订单
			Order getMyBox = orderService.getMyBox(uid);
			if(getMyBox!=null){ //如何有最新订单则修改未可用
				getMyBox.setIf_want(1);
				orderService.insertOrUpdate(getMyBox);
			}
			//发送注册成功短信
	        SmsMessage smsMessage = new SmsMessage();
	        Map<String,String> smsMessageParam = new HashedMap();
	        smsMessage.setAccount(user.getBindphone());
		    Boolean isSendSuccess = smsMessageService.placeOrder(smsMessage,"product");
		    //发送日志
		    SendSmsLog  sendSmsLog =new SendSmsLog();
		    sendSmsLog.setName(user.getNickname());
		    sendSmsLog.setPhone(user.getBindphone());
		    sendSmsLog.setRemark("支付会员费");
	        sendSmsLog.setIsSendSuccess(isSendSuccess);
	        sendSmsLog.setCreatetime(DateUtil.getCurrentDateTimeStr());
	        smsMessageService.createSendSmsLog(sendSmsLog) ;
		}
		else{//非会员：生成订单
			Integer uid = memberPayOrder.getUid();
			//获取最新订单信息，修改为'想要',并更新状态为新订单
			Order getMyBox = orderService.getMyBox(uid);
			getMyBox.setIf_want(1);
			orderService.insertOrUpdate(getMyBox);
			
			DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String pay_time = LocalDateTime.parse(payTime, df1).format(df2);
			
			memberPayOrder.setTrade_no(tradeNo);
			memberPayOrder.setIs_pay(1);
			memberPayOrder.setPay_time(pay_time);
			memberPayOrder.setOrder_no(getMyBox.getOrder_no());
			orderService.updateMemberPayOrder(memberPayOrder);
			
		}
	}
	
	
	/**
	 * 盒子支付订单回调处理
	 * @param orderId
	 * @param tradeNo
	 * @param totalFee
	 * @param payTime
	 * @return
	 * @throws Exception 
	 */
	private void doPayOrderNotify(String orderId, String tradeNo, String totalFee, String payTime) throws Exception {
		PayOrder payOrder = orderService.getPayOrderByPayOrderNo(orderId);
		if (payOrder == null) {
			throw new Exception("订单不存在");
		}

		BigDecimal total_fee = new BigDecimal(totalFee).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		if (payOrder.getPay_amount().compareTo(total_fee) != 0) {
			throw new Exception("订单金额错误");
		}

		if (payOrder.getPay_status() == 1) {
			throw new Exception("订单已支付");
		}
		Order order = orderService.getOrderByOrderNo(payOrder.getOrder_no());
		//记录支付
		DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String pay_time = LocalDateTime.parse(payTime, df1).format(df2);
		payOrder.setTrade_no(tradeNo);
		payOrder.setPay_status(1);
		payOrder.setPay_time(pay_time);
		payOrder.setPay_type(0);//微信支付
		payOrder.setFreight(new BigDecimal(orderService.getFreightPrice(orderId)));
		//记录结算明细
		PageData  inviteUser =  userService.getUserByShopCode(order.getShop_code(),order.getUid());
		User user = userService.getUserByUid(payOrder.getUid());
		
		OrderAccounts orderAccounts = new OrderAccounts();
		if(inviteUser==null){//判断是否专属会员
			orderAccounts.setMembership(0);
			orderAccounts.setService_amount(new BigDecimal(19));
		}
		else{
			orderAccounts.setMembership(1);
			orderAccounts.setService_amount(new BigDecimal(0));
		}
		
		if(user.getIs_member()==0){
			orderAccounts.setType(0);
		}
		else{
			orderAccounts.setType(1);
		}
		
		orderAccounts.setUid(order.getUid());
		orderAccounts.setOrder_no(order.getOrder_no());
		orderAccounts.setTotal_amount(payOrder.getTotal_amount());
		orderAccounts.setPay_amount(payOrder.getPay_amount());
		orderAccounts.setDiscount_amount(payOrder.getDiscount_amount());
		orderAccounts.setPlatform_amount(payOrder.getPlatform_amount());
		orderAccounts.setFreight(payOrder.getFreight());
		orderAccounts.setShop_amount(payOrder.getShop_amount());
		orderAccounts.setPay_order_no(orderId);
		orderAccounts.setShop_code(order.getShop_code());
//		orderService.saveOrderAccounts(orderAccounts);
		//处理支付和结算事务
		orderService.handleOrder(payOrder,orderAccounts);

		//修改支付訂單狀態
		order.setOrder_status(5);
		orderService.insertOrUpdate(order);
		
		//商品支付成功通知
        SmsMessage smsMessage = new SmsMessage();
        Map<String,String> smsMessageParam = new HashedMap();
        smsMessage.setAccount(user.getBindphone());
	    Boolean isSendSuccess = smsMessageService.payBoxOrder(smsMessage,"product");
	    //发送日志
	    SendSmsLog  sendSmsLog =new SendSmsLog();
	    sendSmsLog.setName(user.getNickname());
	    sendSmsLog.setPhone(user.getBindphone());
	    sendSmsLog.setRemark("商品支付成功通知");
        sendSmsLog.setIsSendSuccess(isSendSuccess);
        sendSmsLog.setCreatetime(DateUtil.getCurrentDateTimeStr());
        smsMessageService.createSendSmsLog(sendSmsLog) ;
		
	}
	
	
	
	/**
	 * 退款流程
	 * @param orderId
	 * @param tradeNo
	 * @param totalFee
	 * @param payTime
	 * @return
	 * @throws Exception 
	 */
//	  @RequestMapping("/refund")
//	  public PageData refund(PageData pd,HttpServletRequest request, HttpServletResponse response) throws Exception {
//			String appid = wxConfig.getAppId();
//			String appsecret =wxConfig.getAppSecret();
//			String partner = payConfig.getMchId();
//			String partnerkey = payConfig.getMchKey();
//			String path = payConfig.getKeyPath();
//
//			String order_id = pd.getString("order_id");
//			String refund_id = DateUtil.getCurrentDateTimeStr();//pd.getString("refund_id");
//			BigDecimal order_total = (BigDecimal) pd.get("order_total");
//			BigDecimal refund_price = (BigDecimal) pd.get("refund_price");
//			String total_fee  = (order_total.multiply(new BigDecimal(100))).intValue()+"";
////			String refund_fee = (refund_price.multiply(new BigDecimal(100))).intValue()+"";
//			String refund_fee = "9";//退回0.9元
//
//			PageData data = new PageData();
//			String currTime = TenpayUtil.getCurrTime();
//			// 8位日期
//			String strTime = currTime.substring(8, currTime.length());
//			// 四位随机数
//			String strRandom = TenpayUtil.buildRandom(4) + "";
//			// 10位序列号,可以自行调整。
//			String nonce_str = strTime + strRandom;
//
//			/*-----  1.生成预支付订单需要的的package数据-----*/
//			SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
//			packageParams.put("appid", appid);
//			packageParams.put("mch_id", partner);
//			packageParams.put("nonce_str", nonce_str);
//			packageParams.put("op_user_id", partner);
//			packageParams.put("out_trade_no", order_id);
//			packageParams.put("out_refund_no", refund_id);
//			packageParams.put("total_fee", total_fee);
//			packageParams.put("refund_fee", refund_fee);
//			/*----2.根据package生成签名sign---- */
//			RequestHandler reqHandler = new RequestHandler(request, response);
//			reqHandler.init(appid, appsecret, partnerkey);
//			String sign = reqHandler.createSign(packageParams);
//
//			/*----3.拼装需要提交到微信的数据xml---- */
//			String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + partner + "</mch_id>" + "<nonce_str>"
//					+ nonce_str + "</nonce_str>" + "<op_user_id>" + partner + "</op_user_id>" + "<out_trade_no>" + order_id
//					+ "</out_trade_no>" + "<out_refund_no>" + refund_id + "</out_refund_no>" + "<refund_fee>" + refund_fee
//					+ "</refund_fee>" + "<total_fee>" + total_fee + "</total_fee>" + "<sign>" + sign + "</sign>"
//					+ "</xml>";
////			try {
//				/*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
//			
//				
//			
//			
//				KeyStore keyStore = KeyStore.getInstance("PKCS12");
//				FileInputStream instream = new FileInputStream(new File(path));
//				try {
//					keyStore.load(instream, partner.toCharArray());
//				} finally {
//					instream.close();
//				}
//				// Trust own CA and all self-signed certs
//				SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, partner.toCharArray()).build();
//				// Allow TLSv1 protocol only
//				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
//						null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//				CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//
//				/*----5.发送数据到微信的退款接口---- */
//				String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
//				
//				HttpPost httppost = new HttpPost(url);
//				httppost.setEntity(new StringEntity(xml, "UTF-8"));
//				HttpResponse weixinResponse = httpClient.execute(httppost);
//				String jsonStr = EntityUtils.toString(weixinResponse.getEntity(), "UTF-8");
////				log.info("weixin_tuikuan - > " + jsonStr);
//				Map map = XMLUtil.parseXmlStringToMap(jsonStr);
//				if ("success".equalsIgnoreCase((String) map.get("return_code"))) {
//					if("SUCCESS".equalsIgnoreCase((String) map.get("result_code"))){
//						data.put("result", 1);
//						data.put("message", "退款成功");
//					}else {
//						data.put("result", 0);
//						data.put("message",  map.get("err_code_des"));
//					}
//					
//				} else {
//					data.put("result", 0);
//					data.put("message", "退款失败");
//				}
////			} catch (Exception e) {
////				e.getStackTrace();
////				data.put("result", 0);
////				data.put("message", "退款失败");
////			}
//		  
//	    return data;
//	  }
	
	  
	  
		/**
		 * 生成预支付订单或者年费费用（非会员）
		 * @param openId
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("paybox")
		@ResponseBody
		public JsonResult paybox(int type,String openId,String uno)
				throws WxPayException {
			
	    	if(Tools.checkParams(new String[]{uno,openId})){
	    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno,openId");
	    	}
			
		  	User user = userService.getUserByUno(uno);
			
	    	if(Tools.checkParams(new String[]{openId})){
	    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","openId");
	    	}
			
			if(type==1){//服务费
				String orderSubject = "有衣宅送服务";// 商品描述
				String merchantTradeNo = OrderIDGenerator.getOrderNumber();// 订单号
				Integer totalAmount = 10900;// 订单总金额，单位为分,19元服务费，90元押金
//				Integer totalAmount = 109;// 订单总金额，单位为分,19元服务费，90元押金
				String goodsDesc = "有衣宅送服务";
				String gooodsCode = "code" + merchantTradeNo;
	System.out.println("paybox-----totalAmount===="+totalAmount);
				WxPayMpOrderResult wxPayMpOrderResult = getPayData(merchantTradeNo, orderSubject, totalAmount, goodsDesc,
						gooodsCode, openId);
	
				MemberPayOrder memberPayOrder = new MemberPayOrder();
				memberPayOrder.setOrder_id(merchantTradeNo);
				memberPayOrder.setProduct("有衣宅送服务");
				BigDecimal total_fee = new BigDecimal(totalAmount).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
				memberPayOrder.setTotal_fee(total_fee);
				memberPayOrder.setUid(user.getUid());
				memberPayOrder.setIs_pay(0);
				memberPayOrder.setIs_member(0);
				memberPayOrder.setType(1);
				memberPayOrder.setPay_openId(openId);
				Integer count = orderService.saveMemberPayOrder(memberPayOrder);
				if (count != 1) {
					return new JsonResult(JsonResultCode.FAILURE, "下单失败", wxPayMpOrderResult);
				}
				return new JsonResult(JsonResultCode.SUCCESS, "下单成功", wxPayMpOrderResult);
			}
			else if(type==2){//会员费
				String orderSubject = "有衣宅送服务年卡";// 商品描述
				String merchantTradeNo = OrderIDGenerator.getOrderNumber();// 订单号
				Integer totalAmount = 9600;// 订单总金额，单位为分,90元会员费
//				Integer totalAmount = 1;// 订单总金额，单位为分,90元会员费
				String goodsDesc = "有衣宅送服务年卡";
				String gooodsCode = "code" + merchantTradeNo;
	
				WxPayMpOrderResult wxPayMpOrderResult = getPayData(merchantTradeNo, orderSubject, totalAmount, goodsDesc,
						gooodsCode, openId);
	
				MemberPayOrder memberPayOrder = new MemberPayOrder();
				memberPayOrder.setOrder_id(merchantTradeNo);
				memberPayOrder.setProduct("有衣宅送服务年卡");
				BigDecimal total_fee = new BigDecimal(totalAmount).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
				memberPayOrder.setTotal_fee(total_fee);
				memberPayOrder.setUid(user.getUid());
				memberPayOrder.setIs_pay(0);
				memberPayOrder.setIs_member(1);
				memberPayOrder.setType(0);
				memberPayOrder.setPay_openId(openId);
				Integer count = orderService.saveMemberPayOrder(memberPayOrder);
				if (count != 1) {
					return new JsonResult(JsonResultCode.FAILURE, "下单失败", wxPayMpOrderResult);
				}
				return new JsonResult(JsonResultCode.SUCCESS, "下单成功", wxPayMpOrderResult);
			}
			else{
				return new JsonResult(JsonResultCode.FAILURE, "下单失败", "");
			}
		}
		
		
		/**
		 * 服务押金成为会员
		 * @param orderId
		 * @param tradeNo
		 * @param totalFee
		 * @param payTime
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("becomeMember")
		@ResponseBody
		private JsonResult becomeMember(String orderNo,String uno) throws Exception {
			
			MemberPayOrder memberPayOrder = orderService.getMemberPayOrderByOrderNo(orderNo);
			if (memberPayOrder == null) {
				return new JsonResult(JsonResultCode.FAILURE, "订单不存在或者还没支付", "");
			}

			if (memberPayOrder.getIs_pay() != 1) {
				return new JsonResult(JsonResultCode.FAILURE, "订单未支付", "");
			}
			
		  	User user = userService.getUserByUno(uno);
			if (user == null) {
				return new JsonResult(JsonResultCode.FAILURE, "会员不存在", "");
			}
			if (user.getIs_member() == 1) {
				return new JsonResult(JsonResultCode.FAILURE, "你已经是会员", "");
			}
			
			Order order = orderService.getOrderByOrderNo(orderNo);
			if (order==null) {
				return new JsonResult(JsonResultCode.FAILURE, "订单不存在", "");
			}
			
			//更新支付订单
			memberPayOrder.setType(2);
			memberPayOrder.setOrder_no(orderNo);
			int updatePayOrder =  orderService.updateMemberPayOrder(memberPayOrder);
			//成为会员
	        int becomeMember = userService.setUserToMember(memberPayOrder.getUid());//成功后修改成为会员
	        //修改订单状态
	        order.setDeposit_status(1);
	        boolean insertOrder = orderService.insertOrUpdate(order);
		    return new JsonResult(JsonResultCode.SUCCESS, "成功", "");
		}
		
		
		
		/**
		 * 获取小程序用户的openId
		 * @param orderId
		 * @param tradeNo
		 * @param totalFee
		 * @param payTime
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("getOpenId")
		@ResponseBody
		public JsonResult getOpenId(String code){
			
	    	if(Tools.checkParams(new String[]{code})){
	    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","code");
	    	}
			
		     //微信端登录code值
			  //接收从客户端获取的code
			  //向微信后台发起请求获取openid的url
			  String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
	
			//这三个参数就是之后要填上自己的值。
		      //小程序appid，appsecret，和code
			  	String APPID ="wx061302954efaf53c";
			  	String SECRET ="8771a6cfa32816f612015380ba306706";
		      	String requestUrl = WX_URL.replace("APPID",APPID).//填写自己的appid
		        replace("SECRET", SECRET).replace("JSCODE", code).//填写自己的appsecret，
		        replace("authorization_code", "authorization_code");
	        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
		      	 String  returnvalue=HttpClientUtil.GET(requestUrl);
		         JSONObject jsStr = JSONObject.fromObject(returnvalue); //将字符串{“id”：1}
		       return new JsonResult(JsonResultCode.SUCCESS, "成功", jsStr);
		}
	
		
}
