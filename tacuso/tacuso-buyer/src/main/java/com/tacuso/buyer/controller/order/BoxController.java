package com.tacuso.buyer.controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.entity.Answer;
import com.tacuso.buyer.entity.FigureInfo;
import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.OrderDetail;
import com.tacuso.buyer.entity.PayOrder;
import com.tacuso.buyer.entity.PayOrderDetail;
import com.tacuso.buyer.entity.Percentage;
import com.tacuso.buyer.entity.Question;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.entity.Voucher;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.AddressService;
import com.tacuso.buyer.service.FigureInfoService;
import com.tacuso.buyer.service.OrderService;
import com.tacuso.buyer.service.PercentageService;
import com.tacuso.buyer.service.QuestionService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.service.VoucherService;
import com.tacuso.buyer.utils.DateUtil;
import com.tacuso.buyer.utils.OrderCodeUtil;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.vo.AnswerTagVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/box")
public class BoxController  extends BaseController {
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private FigureInfoService figureInfoService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private PercentageService percentageService;
    
    
	/**
     * 我的有衣盒子
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException 
     */
    @RequestMapping("mybox")
    public String mybox(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException{
    	uid = Tools.getUserId(request);
        User user = userService.getUserByUid(uid);
        String reqUri = request.getServletPath();

        if(user.getIs_verify()==1){
        	
            if(user.getIs_question_finish()==0){
                if(user.getIs_member()==1){
                	 if(!reqUri.equals("/register/member_pay")) {
 	                	return "redirect:/register/member_pay";
 	                }
                }
	        }else{
	        			return "redirect:/start";
	        }
        	
        }else{
        	if(!reqUri.equals("/register/index") && !reqUri.equals("/index")){
                //未绑定手机号码
//            	return "redirect:/register/index";
        		return "redirect:/index";
            }
            
        }
  
//        Order order = orderService.getMyBox(uid);
//        if(order==null)
//        {
//    		model.addAttribute("order_null", 1);
//        }
//        else{
//    		Integer status = order.getOrder_status();
//    		Integer ifWant= order.getIf_want();
//    		Address address = null;
//    		if(!StringUtils.isEmpty(order.getConsignee())) {    
//    			address = new Address();
//    			address.setAddress_id(order.getAddress_id());
//    			address.setName(order.getConsignee());
//    			address.setPhone(order.getPhone());
//    			address.setCity(order.getCity());
//    			address.setDetail(order.getAddress());
//    		}
//    		
//    		if(status==5){
//    			model.addAttribute("words", "预约寄回盒子");
//    		}
//    		if(status==6){
//    			String  yuyueTime = orderService.getYuyueTime(uid);
//    			model.addAttribute("yuyueTime", yuyueTime);
//    			model.addAttribute("words", "重新预约");
//    		}
//    		model.addAttribute("order_status", status);
//    		model.addAttribute("address", address);
//        	model.addAttribute("order", order);
//        	model.addAttribute("order_no", order.getOrder_no());
//        	model.addAttribute("express_phone","15017507788");
//        	model.addAttribute("express_no",order.getDelivery_no());
//        }
//
//        	model.addAttribute("time", DateUtil.getCurrentDateTimeStr());

    		return "box/mybox";
    }

    /**
     * 获取下一次盒子时间
     * @param request
     * @param response
     * @param model
     * @return
     */
//    @RequestMapping("next_box_info")
//    @ResponseBody
//    public JsonResult nextBoxInfo(HttpSession session){
////    	Integer uid = (Integer) session.getAttribute("uid");
//    	uid = Tools.getUserId(session);
////    	Group group = userService.getCurrentyGroup(uid);
//    	
//    	DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
////    	LocalDate boxDate = LocalDate.parse(group.getBox_time(), df);
//    	String result = "";
////    	if (group.getGroup_status() == 0) {
//////    		result = "下次盒子时间 / " + boxDate.getMonthValue() + "月" + boxDate.getDayOfMonth() + "日";
////    	} else {
////    		LocalDate today = LocalDate.now();
////    		if (today.isBefore(boxDate)) {
//    			result = "盒子已开启";
////    		} else {
////    			result = "盒子已送出，留意签收";
////    		}
////    	}
//
//    	return new JsonResult(JsonResultCode.SUCCESS,result,result);
//    }
    

    /**
     * 获取盒子送达时间
     * @param request
     * @param response
     * @param model
     * @return
     */
//    @RequestMapping("deliver_date")
//    @ResponseBody
//    public JsonResult delivery_date(HttpServletRequest request, HttpServletResponse response , Model model){
//
//        return null;
//
//    }
    
    
    /**
     * 修改订单个人信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String weight, @RequestParam String buyer_msg,String answerList,String add_id,String uno){

    	Map<String,Object> map = new HashMap<>();
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
	  	User user = userService.getUserByUno(uno);
//        if(answerList.contains(",")){
//        	answerList = answerList.substring(0,answerList.length() - 1);
//        }
    	if (!"没有变化".equals(weight)) {
    		FigureInfo figureInfo = figureInfoService.getFigureInfoByUid(user.getUid());
    		figureInfo.setWeight("[\"" + weight + "\"]");
    		figureInfoService.createOrUpdateFigureInfo(figureInfo);
    		String height = figureInfo.getHeight().replace("[\"", "").replace("\"]", "");
     	   //保存所有的用户提交记录
    		String jsonList ="[{\"question_type\":\"3\",\"tag_type\":\"1\",\"answer_value\":\""+weight+"\",\"answer_tag_id\":\"0\",\"answer_key\":\"weight\",\"tag_table\":\"tacuso_figure_info\",\"question_id\":\"25\"},{\"question_type\":\"3\",\"tag_type\":\"1\",\"answer_value\":\""+height+"\",\"answer_tag_id\":\"0\",\"answer_key\":\"height\",\"tag_table\":\"tacuso_figure_info\",\"question_id\":\"25\"}]";
    		
            List<AnswerTagVo> answerTagVoList = JSON.parseArray(jsonList, AnswerTagVo.class);
            questionService.saveAnswerTag( user.getUid(), user.getWx_uid() ,answerTagVoList);
    	}
        Order order = orderService.getMyBox(user.getUid());
        //创建新订单
           Address address = addressService.selectById(add_id);
           if(address!=null){
        	   orderService.createOrder(user.getUid(),answerList,address,buyer_msg);
           }

    	userService.updateUserData(user.getUid());
    	map.put("is_member", user.getIs_member());
    	return new JsonResult(JsonResultCode.SUCCESS,"",map);
    }
    
    /**
     * 修改订单地址
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("update/address")
    @ResponseBody
    public JsonResult updateOrderAddress(HttpServletRequest request, HttpServletResponse response, Model model,
    		@RequestParam Integer address_id, @RequestParam String order_no,String uno){
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
	  	User user = userService.getUserByUno(uno);
    	Address address = addressService.selectById(address_id);
    	model.addAttribute("address", address);
    	return new JsonResult(JsonResultCode.SUCCESS,"","");
    }

    /**
     * 支付成功回调（页面回调）
     * @param request
     * @param response
     * @param model
     * @return
     */
//    @RequestMapping("/pay_success")
//    @ResponseBody
//    public JsonResult boxPaySuccess(HttpServletRequest request, HttpServletResponse response,
//    		@RequestParam String order_no){
//    	PayOrder payOrder = orderService.getPayOrderByOrderNo(order_no);
//    	Order order = orderService.getOrderByOrderNo(payOrder.getOrder_no());
//    	order.setOrder_status(5);
//    	order.setPaytime(DateUtil.getCurrentDateTimeStr());
//    	orderService.insertOrUpdate(order);
//
//    	return new JsonResult(JsonResultCode.SUCCESS,"","");
//    }
    
    /**
     * 提交订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping("/pay")
    @ResponseBody
    public JsonResult orderSubmit(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String order_no, @RequestParam String skus){
    	//uid = (Integer) request.getSession().getAttribute("uid");

    	uid = Tools.getUserId(request);
    	String pay_order_no = OrderCodeUtil.generateOrderCode(redisTemplate);
    	Long rtime = new Date().getTime() / 1000;
    	BigInteger rt = new BigInteger(rtime.toString());
    	Order order = orderService.getOrderByOrderNo(order_no);
    	JSONArray jsonArray = JSONArray.fromObject(skus);
    	
    	int skuNum = jsonArray.size();
    	int buyNum = 0;
    	for (int i = 0; i < skuNum; i++) {
    		JSONObject jsonObject = jsonArray.getJSONObject(i);
    		Map<String,Object> detailMap = new HashMap<>();
    		int serial_num = jsonObject.getInt("serial_num");
    		detailMap.put("order_no", order_no);
    		detailMap.put("serial_num", serial_num);
    		
    		Integer is_buy = jsonObject.getInt("is_buy");
    		if (is_buy == 1) {
    			OrderDetail orderDetail = orderService.findOrderDetailByOrderNoAndNum(order_no,serial_num);
    			PayOrderDetail detail = new PayOrderDetail();
    			detail.setPay_order_no(pay_order_no);
    			detail.setSerial_num(orderDetail.getSerial_num());
//    			detail.setSpu_id(orderDetail.getSpu_id());
    			detail.setSku_id(orderDetail.getSku_id());
    			detail.setSku_name(orderDetail.getSku_name());
    			detail.setSku_pic(orderDetail.getSku_pic());
    			detail.setSku_price(orderDetail.getSku_price());
    			detail.setRt(rt);
    			orderService.savePayOrderDetail(detail);
    			buyNum++;
    		}
    		detailMap.put("is_buy", is_buy);
    		detailMap.put("comment", jsonObject.getString("comment"));
    		detailMap.put("content", jsonObject.getString("content"));
    		orderService.updateOrderDetail(detailMap);
    	}
//    	生成支付订单
    	PayOrder payOrder = new PayOrder();
    	payOrder.setPay_order_no(pay_order_no);
    	payOrder.setOrder_no(order_no);
    	payOrder.setUid(uid);
    	payOrder.setRt(rt);
//    	结算金额
    	double discount = 1;
    	if (skuNum == buyNum) {
    		discount = 0.9;
    	} else if (buyNum >= 3) {
    		discount = 0.95;
    	}

    	BigDecimal total_amount = new BigDecimal(0);
    	BigDecimal discount_amount = new BigDecimal(0);
    	BigDecimal voucher_amount = new BigDecimal(0);
    	
    	if (buyNum == 0) {
    		payOrder.setPay_status(1);
    		payOrder.setPay_time(DateUtil.getCurrentDateTimeStr());
    		payOrder.setPay_type(10);
    		payOrder.setFreight(new BigDecimal(orderService.getAllFreightPrice()));
    	} else {    
    		total_amount = orderService.getOrderTotalAmount(order_no);
    		discount_amount = total_amount.multiply(new BigDecimal(1-discount)).setScale(2, BigDecimal.ROUND_HALF_UP);
    		Voucher voucher = voucherService.getAvailableVoucherByUid(uid);
    		if (voucher != null) {
    			voucher_amount = voucher.getAmount();
    			payOrder.setVoucher_id(voucher.getVoucher_id());
    		}
    		payOrder.setPay_status(0);
    	}
    	Percentage percentage = percentageService.selectById(1);

    	BigDecimal pay_amount = total_amount.subtract(discount_amount).subtract(voucher_amount);
    	double platform_amount = total_amount.multiply(percentage.getPercent()).doubleValue();
    	double shop_amount = total_amount.doubleValue()-platform_amount;
    	payOrder.setTotal_amount(total_amount);
    	payOrder.setPlatform_amount(new BigDecimal(platform_amount));
    	payOrder.setShop_amount(new BigDecimal(shop_amount));
    	payOrder.setDiscount_amount(discount_amount);
    	payOrder.setVoucher_amount(voucher_amount);
    	payOrder.setPay_amount(pay_amount);
    	payOrder.setBuyer_msg(order.getBuyer_msg());
    	payOrder.setConsignee(order.getConsignee());
    	payOrder.setPhone(order.getPhone());
    	payOrder.setCity(order.getCity());
    	payOrder.setAddress(order.getAddress());
    	payOrder.setShop_code(order.getShop_code());
    	payOrder.setFreight_id(1);
    	orderService.savePayOrder(payOrder);
    	
    	Map<String,Object> map = new HashMap<>();
    	map.put("order_no", order_no);
    	map.put("pay_order_no", pay_order_no);
    	map.put("buy_num", buyNum);
    	
    	return new JsonResult(JsonResultCode.SUCCESS,"",map);
    }
    
    /**
     * 我要盒子(会员)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("confirmBox")
    @ResponseBody
    public JsonResult confirmBox(String uno){
    	Map<String,Object> map = new HashMap<>();
//    	uid = Tools.getUserId(request);
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
//    	uid = Tools.getUserId(request);
	  	User user = userService.getUserByUno(uno);
    	
		Address address = addressService.getMainAddress(user.getUid());
			if(address!=null) {    
				map.put("address", address);
			}

		   List<Question> questionList = questionService.getAllQuestionByPageId(7);
     
	        Collection<Object> questionIdList = new ArrayList<>();
	        for(Question question:questionList){
	            questionIdList.add(String.valueOf(question.getQuestion_id()));
	        }

	        List<Answer> clothesList = questionService.getAllAnswerByQuestionId(37);
	        List<Answer> trousersList = questionService.getAllAnswerByQuestionId(38);
	       
//		model.addAttribute("address", address);
//		model.addAttribute("clothesList", clothesList);
//		model.addAttribute("trousersList", trousersList);
    	map.put("address", address);
    	map.put("clothesList", clothesList);
    	map.put("trousersList", trousersList);
    	return new JsonResult(JsonResultCode.SUCCESS,"",map);
//    	return "box/confirm-box";
    }
    
    
    
    /**
     * 我要盒子(非会员)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("buyBox")
    public String buyBox(HttpServletRequest request, HttpServletResponse response , Model model){

    	uid = Tools.getUserId(request);
  
		Address address = addressService.getMainAddress(uid);
			if(address!=null) {    
				model.addAttribute("address", address);
			}

		   List<Question> questionList = questionService.getAllQuestionByPageId(7);
     
	        Collection<Object> questionIdList = new ArrayList<>();
	        for(Question question:questionList){
	            questionIdList.add(String.valueOf(question.getQuestion_id()));
	        }

	        List<Answer> clothesList = questionService.getAllAnswerByQuestionId(37);
	        List<Answer> trousersList = questionService.getAllAnswerByQuestionId(38);
	       
		model.addAttribute("address", address);
		model.addAttribute("clothesList", clothesList);
		model.addAttribute("trousersList", trousersList);
    	return "box/buy-box";
    }
    
    
    /**
     * 修改取消订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("update/cancel")
    @ResponseBody
    public JsonResult updateCancelOrder(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String order_no){

    	Order order = orderService.getOrderByOrderNo(order_no);
    	order.setOrder_status(11);
    	orderService.insertOrUpdate(order);

    	return new JsonResult(JsonResultCode.SUCCESS,"","");
    }
    
    
//    @RequestMapping("waitBox")
//    public String waitBox(HttpServletRequest request, HttpServletResponse response , Model model){
//     	uid = Tools.getUserId(request);
//     	
//		LocalDate boxDate = userService.getNextBoxDate(uid);
//		model.addAttribute("time", boxDate.getMonthValue() + "/" + boxDate.getDayOfMonth());
//		return "box/wait-box";
//	}
    
    
    /**
     * 提交订单(小程序)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Transactional
    @PostMapping("/payOrder")
    @ResponseBody
    public JsonResult payOrder(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String order_no, @RequestParam String skus, @RequestParam String uno){
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
//    	uid = Tools.getUserId(request);
	  	User user = userService.getUserByUno(uno);
	  	uid = user.getUid();
	  	
    	String pay_order_no = OrderCodeUtil.generateOrderCode(redisTemplate);
    	Long rtime = new Date().getTime() / 1000;
    	BigInteger rt = new BigInteger(rtime.toString());
    	Order order = orderService.getOrderByOrderNo(order_no);
    	JSONArray jsonArray = JSONArray.fromObject(skus);
    	
    	int skuNum = jsonArray.size();
    	int buyNum = 0;
    	for (int i = 0; i < skuNum; i++) {
    		JSONObject jsonObject = jsonArray.getJSONObject(i);
    		Map<String,Object> detailMap = new HashMap<>();
    		int serial_num = jsonObject.getInt("serial_num");
    		detailMap.put("order_no", order_no);
    		detailMap.put("serial_num", serial_num);
    		
    		Integer is_buy = jsonObject.getInt("is_buy");
    		if (is_buy == 1) {
    			OrderDetail orderDetail = orderService.findOrderDetailByOrderNoAndNum(order_no,serial_num);
    			PayOrderDetail detail = new PayOrderDetail();
    			detail.setPay_order_no(pay_order_no);
    			detail.setSerial_num(orderDetail.getSerial_num());
//    			detail.setSpu_id(orderDetail.getSpu_id());
    			detail.setSku_id(orderDetail.getSku_id());
    			detail.setSku_name(orderDetail.getSku_name());
    			detail.setSku_pic(orderDetail.getSku_pic());
    			detail.setSku_price(orderDetail.getSku_price());
    			detail.setRt(rt);
    			orderService.savePayOrderDetail(detail);
    			buyNum++;
    		}
    		detailMap.put("is_buy", is_buy);
    		detailMap.put("comment", jsonObject.getString("comment"));
    		detailMap.put("content", jsonObject.getString("content"));
    		orderService.updateOrderDetail(detailMap);
    	}
//    	生成支付订单
    	PayOrder payOrder = new PayOrder();
    	payOrder.setPay_order_no(pay_order_no);
    	payOrder.setOrder_no(order_no);
    	payOrder.setUid(uid);
    	payOrder.setRt(rt);
//    	结算金额
    	double discount = 1;
    	if (skuNum == buyNum) {
    		discount = 0.9;
    	} else if (buyNum >= 3) {
    		discount = 0.95;
    	}

    	BigDecimal total_amount = new BigDecimal(0);
    	BigDecimal discount_amount = new BigDecimal(0);
    	BigDecimal voucher_amount = new BigDecimal(0);
       	BigDecimal service_amount = new BigDecimal(0);
    	if (buyNum == 0) {//不用支付金额
    		payOrder.setPay_status(1);
    		payOrder.setPay_time(DateUtil.getCurrentDateTimeStr());
    		payOrder.setPay_type(10);
    		payOrder.setFreight(new BigDecimal(orderService.getAllFreightPrice()));
    		//修改订单状态
    		order.setOrder_status(5);
    		orderService.insertOrUpdate(order);
    	} else {    
    		total_amount = orderService.getOrderTotalAmount(order_no);
    		discount_amount = total_amount.multiply(new BigDecimal(1-discount)).setScale(2, BigDecimal.ROUND_HALF_UP);
    		Voucher voucher = voucherService.getAvailableVoucherByUid(uid);
    		if (voucher != null) {
    			voucher_amount = voucher.getAmount();
    			payOrder.setVoucher_id(voucher.getVoucher_id());
    		}
    		payOrder.setPay_status(0);
    		if(user.getIs_member()!=1){//非会员抵扣服务费
//    			service_amount = new BigDecimal(0.19);
      			service_amount = new BigDecimal(19);
    		}
    		
    	}
    	Percentage percentage = percentageService.selectById(1);
    	
    	BigDecimal pay_amount = total_amount.subtract(discount_amount).subtract(voucher_amount).subtract(service_amount);
    	double platform_amount = total_amount.multiply(percentage.getPercent()).doubleValue();
    	double shop_amount = total_amount.doubleValue()-platform_amount;
    	
    	
    	payOrder.setTotal_amount(total_amount);
    	payOrder.setPlatform_amount(new BigDecimal(platform_amount));
    	payOrder.setShop_amount(new BigDecimal(shop_amount));
    	payOrder.setDiscount_amount(discount_amount);
    	payOrder.setVoucher_amount(voucher_amount);
    	payOrder.setPay_amount(pay_amount);
    	payOrder.setBuyer_msg(order.getBuyer_msg());
    	payOrder.setConsignee(order.getConsignee());
    	payOrder.setPhone(order.getPhone());
    	payOrder.setCity(order.getCity());
    	payOrder.setAddress(order.getAddress());
    	payOrder.setShop_code(order.getShop_code());
    	payOrder.setFreight_id(1);
	    if(user.getIs_member()==0){
	    	payOrder.setService_amount(new BigDecimal(19));
//	    	payOrder.setService_amount(new BigDecimal(0.19));
	    }else{
	    	payOrder.setService_amount(new BigDecimal(0));
	    }

    	orderService.savePayOrder(payOrder);
    	
    	Map<String,Object> map = new HashMap<>();
    	map.put("order_no", order_no);
    	map.put("pay_order_no", pay_order_no);
    	map.put("buy_num", buyNum);
    	return new JsonResult(JsonResultCode.SUCCESS,"",map);
    }
    
    
}
