package com.tacuso.buyer.controller.order;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.PayOrder;
import com.tacuso.buyer.entity.SendSmsLog;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.AddressService;
import com.tacuso.buyer.service.OrderService;
import com.tacuso.buyer.service.SmsMessageService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.sms.SmsMessage;
import com.tacuso.buyer.utils.DateUtil;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.utils.express.KdGoldAPI;
import com.tacuso.buyer.utils.express.KdniaoTrackQueryAPI;
import com.tacuso.buyer.vo.PageData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/express")
public class ExpressController extends BaseController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private AddressService addressService;
	 @Autowired
	 private SmsMessageService smsMessageService;
	/**
	 * 物流跟踪
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/yuyue")
	public String yuyue(HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam("orderNo") String orderNo,@RequestParam("uno") String uno) throws Exception {
//        uid = (Integer) request.getSession().getAttribute("uid");
    	User user = userService.getUserByUno(uno);
//		uid = Tools.getUserId(request);
    	Address address = addressService.getMainAddress(user.getUid());
    	if(address==null){
    		address = addressService.addAddressNoMain(user.getUid());
    	}
    	model.addAttribute("address", address);
    	model.addAttribute("orderNo", orderNo);
		return "box/yuyue";
	}

	/**
	 * 叫快递员
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @Transactional
    @PostMapping("/callReceiver")
    @ResponseBody
	public JsonResult callReceiver(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("endtime") String endtime,
			@RequestParam("starttime") String starttime,
			@RequestParam("name") String name,
			@RequestParam("tel") String tel,
			@RequestParam("address") String address,
			@RequestParam("orderNo") String orderNo
			) throws Exception  {

    	String ProvinceName = "", CityName = "", ExpAreaName = "", Address = "",takeProvinceName = "", takeCityName = "", takeExpAreaName = "", takeAddress = "";
    	
    	System.out.println("callReceiver--address===="+address);
		uid = Tools.getUserId(request);
		PageData expressPd = orderService.getOrderExpress(orderNo);
		if(expressPd!=null){
	     	return new JsonResult(JsonResultCode.FAILURE,"已经预约","");
		}
		
		KdGoldAPI kdGoldAPI = new KdGoldAPI();
		Long    TimeStsamp = DateUtil.getCurrentTimeStsamp();
		if(address.length()<7){
	    	return new JsonResult(JsonResultCode.FAILURE,"请填写正确地址","");
		}
		
		ProvinceName = address.substring(0,2);
		CityName = address.substring(2,4);
		ExpAreaName = address.substring(4,7);
		Address = address.substring(7);
		
		//获取用户发货地址
//		List<Map<String,String>> addressListMap = Tools.addressResolution(address);
//		for (Map<String, String> m : addressListMap)
//	    {
//		    ProvinceName =  m.get("province");
//		    CityName = m.get("city") ;
//		    ExpAreaName = m.get("county");
//		    Address = m.get("village");
//	    }
//    	System.out.println("callReceiver--ProvinceName===="+ProvinceName);
//      	System.out.println("callReceiver--Address===="+Address);
		
		//获取商家收件地址
		String shopTakeAddress = "";
		String taker = "";
		String shopPhone = "";
		PageData deliverAddress = addressService.getShopSeliverAddress(orderNo);
		if(deliverAddress!=null){
			shopTakeAddress = deliverAddress.get("take_address").toString();
			List<Map<String,String>> shopAddressListMap = Tools.addressResolution(shopTakeAddress);
			for (Map<String, String> m : shopAddressListMap)
		    {
				takeProvinceName =  m.get("province");
				takeCityName = m.get("city") ;
				takeExpAreaName = m.get("county");
				takeAddress = m.get("village");
		    }
//			takeProvinceName = shopTakeAddress.substring(0,2);
//			takeCityName = shopTakeAddress.substring(2,4);
//			takeExpAreaName = shopTakeAddress.substring(4,6);
//			takeAddress = shopTakeAddress.substring(6);
			taker = deliverAddress.get("taker").toString();
			shopPhone = deliverAddress.get("taker_phone").toString();
			
		}
		
		String result = "";
		result = kdGoldAPI.orderOnlineByJson(starttime,endtime, TimeStsamp, name, tel, ProvinceName, CityName, ExpAreaName, Address, takeProvinceName,taker,shopPhone, takeCityName, takeExpAreaName, takeAddress);
		JSONObject jsonObject = JSONObject.fromObject(result);
		String result_str= jsonObject.getString("Success");

		if(result_str.equals("false")){
			orderService.saveExpressLog(1,uid,starttime,endtime,DateUtil.getCurrentDateTimeStr(), TimeStsamp, name, tel,jsonObject.toString(),orderNo,address,taker,shopPhone,shopTakeAddress);
//			orderService.updateOrderExpressstatus(orderNo);
	    	return new JsonResult(JsonResultCode.FAILURE,"通知收件失败","");
		}
		else{
			orderService.saveExpressLog(0,uid,starttime,endtime,DateUtil.getCurrentDateTimeStr(), TimeStsamp, name, tel,jsonObject.toString(),orderNo,address,taker,shopPhone,shopTakeAddress);
			orderService.updateOrderExpressstatus(orderNo);
		
			Order order = orderService.getOrderByOrderNo(orderNo);
			User user = userService.getUserByUid(order.getUid());
		//短信通知-预约寄回成功
//		  SmsMessage smsMessage = new SmsMessage();
//	        Map<String,String> smsMessageParam = new HashedMap();
//	        smsMessage.setAccount(user.getBindphone());
//	        smsMessageParam.put("address",name+" "+tel);
//	        smsMessageParam.put("time",changeDate(starttime)+"~"+changeDate(endtime));
//	        smsMessage.setParam(smsMessageParam);
//		    Boolean isSendSuccess = smsMessageService.yuyue(smsMessage,"product");
		    //发送日志
		    SendSmsLog  sendSmsLog =new SendSmsLog();
		    sendSmsLog.setName(name);
		    sendSmsLog.setPhone(tel);
		    sendSmsLog.setRemark("预约寄回成功");
	        sendSmsLog.setIsSendSuccess(true);
	        sendSmsLog.setCreatetime(DateUtil.getCurrentDateTimeStr());
	        smsMessageService.createSendSmsLog(sendSmsLog) ;
			//return "box/yuyue";
	     	return new JsonResult(JsonResultCode.SUCCESS,"预约成功","");
		}
	}
	
	
	/**
	 * 物流跟踪
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/subGetStockChangeInfo")
	public String history(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("express_no") String expressNo) throws Exception {

		List<PageData> infolist = new LinkedList<PageData>();
		Order orderExpressPd = orderService.getOrderByOrderNo(expressNo);
		String shipperCode = "";
		String expressName = "";
		String logisticCode = "";
		if (orderExpressPd != null) {
			KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
			// 第一个参数是快递公司的简称编号（YD=韵达速递）
			// 第二个参数是订单号
			// 有疑问咨询本人QQ：70255403
			String orderCode = orderExpressPd.getDelivery_no();
			String wms_title = orderExpressPd.getDelivery_company_code();

			 String result = api.getOrderTracesByJson("HTKY", "70359225267567");
			//String result = api.getOrderTracesByJson(wms_title, orderCode);

			JSONObject jsonObject2 = JSONObject.fromObject(result);
			shipperCode = jsonObject2.getString("ShipperCode");
			expressName = orderExpressPd.getDelivery_company_name();
			logisticCode = jsonObject2.getString("LogisticCode");
			JSONArray Traces = jsonObject2.getJSONArray("Traces");

			for (int i = 0; i < Traces.size(); i++) {
				PageData infoPd = new PageData();
				JSONObject object = (JSONObject) Traces.get(i);
				String acceptTime = object.getString("AcceptTime");
				String acceptStation = object.getString("AcceptStation");
				infoPd.put("acceptTime", acceptTime);
				infoPd.put("acceptStation", acceptStation);
				infolist.add(infoPd);
			}
			
		} else {
			PageData infoPd = new PageData();
			infoPd.put("acceptTime", "");
			infoPd.put("acceptStation", "待发货");
			infolist.add(infoPd);
		}
		

		model.addAttribute("shipperCode", shipperCode);
		model.addAttribute("expressName", expressName);
		model.addAttribute("logisticCode", logisticCode);
		model.addAttribute("infoList", infolist);
		return "box/delivery";
	}
	
	String changeDate(String str) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=formatter.parse(str);
		SimpleDateFormat format=new SimpleDateFormat("HH:mm");
		String date = format.format(d);
		return date;
	}
	
	/**
	 * 预约成功后评价
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rate")
	public String rate(HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam("orderNo") String orderNo) throws Exception {
//        uid = (Integer) request.getSession().getAttribute("uid");
		uid = Tools.getUserId(request);
 
    	model.addAttribute("orderNo", orderNo);
		return "box/rate";
	}
	
	
	/**
	 * 评价成功
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/evaluate")
    @ResponseBody
	public JsonResult evaluate(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("service") int service,
			@RequestParam("style") int style,
			String content,
			String orderNo,
			String uno
			) throws Exception {
//	    uid = (Integer) request.getSession().getAttribute("uid");
		PageData info = orderService.getOrderEvaluate(orderNo);
		if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.SUCCESS,"参数为空","uno");
    	}

    	User user = userService.getUserByUno(uno);
		if(info==null){
			Order order = orderService.getOrderByOrderNo(orderNo);
			int back = orderService.saveOrderEvaluate(user.getUid(),service,style,content,orderNo,DateUtil.getCurrentDateTimeStr(),order.getShop_code());
 
			if(back==1){
				return new JsonResult(JsonResultCode.SUCCESS,"评价成功","");
			}
			else{
				return new JsonResult(JsonResultCode.FAILURE,"评价失败","");
			}
		}
		else{
			int back = orderService.editOrderEvaluate(user.getUid(),service,style,content,orderNo,DateUtil.getCurrentDateTimeStr());
			if(back==1){
				return new JsonResult(JsonResultCode.SUCCESS,"修改成功","");
			}
			else{
				return new JsonResult(JsonResultCode.FAILURE,"修改失败","");
			}
		}

	}
	
	
	/**
	 * 获取评价信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEvaluate")
    @ResponseBody
	public JsonResult getEvaluate(HttpServletRequest request, HttpServletResponse response, Model model,
			String orderNo
			) throws Exception {
//	    uid = (Integer) request.getSession().getAttribute("uid");
			PageData info = orderService.getOrderEvaluate(orderNo);
			if(info!=null){
		    	Map<String,Object> map = new HashMap<>();
		       	map.put("commentInfo", info);
				return new JsonResult(JsonResultCode.SUCCESS,"成功",map);
			}
			else
			{
				return new JsonResult(JsonResultCode.SUCCESS,"","");
			}
	}
	

	
	 /**
     * 取消预约
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/canelYuYue")
    @ResponseBody
    public JsonResult canelYuYue( @RequestParam("orderNo") String orderNo,String uno){
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
    	
    	
    	User user = userService.getUserByUno(uno);
		if(user==null){
			return new JsonResult(JsonResultCode.FAILURE,"用户不存在","");
		}
		Map<String,Object> result = new HashMap<>();
    	Order order = orderService.getOrderByOrderNo(orderNo);
		if(order==null){
			return new JsonResult(JsonResultCode.FAILURE,"没订单","");
		}
		order.setOrder_status(5);
		orderService.insertOrUpdate(order);
		//删除预约单
		orderService.delOrderExpress(orderNo);
		
//		result.put("order", order);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功","");
    }
	
}
