package com.tacuso.buyer.controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.OrderDetail;
import com.tacuso.buyer.entity.PayOrder;
import com.tacuso.buyer.entity.PayOrderDetail;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.AddressService;
import com.tacuso.buyer.service.OrderService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.service.VoucherService;
import com.tacuso.buyer.utils.DateUtil;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.vo.PageData;
import com.tacuso.buyer.vo.PayOrderVo;


@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private VoucherService voucherService;
	
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private UserService userService;
    /**
     * 历史订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/history")
    public String history(HttpServletRequest request, HttpServletResponse response , Model model){
        //uid = (Integer) request.getSession().getAttribute("uid");
        uid = Tools.getUserId(request);
        List<PayOrderVo> orderList = orderService.getHistoryPayOrderListByUid(uid);
        model.addAttribute("orderList", orderList);
    	return "order/history";
    }


    @RequestMapping("/bill")
    public String bill(HttpServletRequest request, HttpServletResponse response , Model model){
//    	uid = (Integer) request.getSession().getAttribute("uid");
    	uid = Tools.getUserId(request);
    	Order order = orderService.getMyBox(uid);
    	String orderNo = order.getOrder_no();
    	List<OrderDetail> skuList = orderService.findOrderDetailListByOrderNo(order.getOrder_no());
    	
    	ObjectMapper mapper = new ObjectMapper();
    	int skuNum = skuList.size();
    	int buyNum = 0;
    	for (OrderDetail sku : skuList) {
    		if (sku.getIs_buy() == 1) {
    			buyNum++;
    		}
    		
    		if (StringUtils.isNotEmpty(sku.getComment())) {
    			String json = null;
    			String commentJson = sku.getComment();
    			Map<String,String> commentMap = new HashMap<>();
    			List<Map<String, String>> commentList = new ArrayList<>();
				try {
					commentList = mapper.readValue(commentJson,new TypeReference<List<Map<String,String>>>() { });
					for (Map<String,String> map : commentList) {
						commentMap.put(map.get("about"), map.get("reason"));
					}
					json = mapper.writeValueAsString(commentMap);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sku.setComment(json);
    		}
    	}
    	
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
//    	Voucher voucher = voucherService.getAvailableVoucherByUid(uid);
//    	if (voucher != null) {
//    		voucher_amount = voucher.getAmount();
//    	}
    	
    	if (buyNum > 0) {    
    		total_amount = orderService.getOrderTotalAmount(orderNo);
    		discount_amount = total_amount.multiply(new BigDecimal(1-discount)).setScale(2, BigDecimal.ROUND_HALF_UP);
    	}
    	//更新签收状态
    	int callback = orderService.updateSignStatus(orderNo);
    	
    	BigDecimal pay_amount = total_amount.subtract(discount_amount).subtract(voucher_amount);
    	model.addAttribute("skuList", skuList);
    	model.addAttribute("total_amount", total_amount);
    	model.addAttribute("voucher_amount", voucher_amount);
    	model.addAttribute("discount_amount", discount_amount);
    	model.addAttribute("pay_amount", pay_amount);
    	model.addAttribute("order_no", orderNo);
    	return "order/bill";
    }


    @RequestMapping("/pay")
    @ResponseBody
    public JsonResult pay(HttpServletRequest request, HttpServletResponse response , Model model, @RequestParam("pay_order_no") String pay_order_no,@RequestParam("uno") String uno){
//    	uid = (Integer) request.getSession().getAttribute("uid");
//    	uid = Tools.getUserId(request);
//    	User user = userService.getUserByUid(uid);

		Map<String,Object> result = new HashMap<>();
	  	User user = userService.getUserByUno(uno);
    	PayOrder payOrder = orderService.getPayOrderByPayOrderNo(pay_order_no);
    	List<PayOrderDetail> orderDetailList = orderService.findPayOrderDetailListByOrderNo(pay_order_no);    	
    	
    	result.put("order", payOrder);
    	result.put("uno", user.getUno());
    	result.put("detailList", orderDetailList);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
    }
    
    
    /**
     * 历史订单(小程序)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/historyOrder")
    @ResponseBody
    public JsonResult historyOrder(HttpServletRequest request,
    		HttpServletResponse response ,
    		String uno,
    		int page){
//     	uid = Tools.getUserId(session);
    	User user = userService.getUserByUno(uno);
		Map<String,Object> result = new HashMap<>();
		List<PageData> hOrderList = orderService.getHistoryOrderList(user.getUid(),page);
		int pageCount = orderService.getHistoryOrderCount(user.getUid());
		result.put("hOrderList", hOrderList);
		result.put("pageCount", pageCount);
		result.put("pageNum", (pageCount-1)/10+1);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
    }
    
    
    /**
     * 历史订单详情(小程序)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/historyOrderInfo")
    @ResponseBody
    public JsonResult historyOrderInfo(HttpServletRequest request, 
    		HttpServletResponse response ,
    		String uno,
    		String orderNo){
//     	uid = Tools.getUserId(session);
    	User user = userService.getUserByUno(uno);
		Map<String,Object> result = new HashMap<>();
		PageData hOrderInfo = orderService.getHistoryOrderInfo(user.getUid(),orderNo);
		if(hOrderInfo!=null){
			List<PageData> hOrderDetailList = orderService.getHistoryOrderDetailList(user.getUid(),orderNo);
			hOrderInfo.put("hOrderDetailList", hOrderDetailList);
			result.put("hOrderInfo", hOrderInfo);
		}
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
    }
    
    
    /**
     * 获取下单信息(小程序)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/getOrderInfo")
    @ResponseBody
    public JsonResult getOrderInfo(String uno){
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
    	
    	User user = userService.getUserByUno(uno);
		Map<String,Object> result = new HashMap<>();
		PageData orderInfo = orderService.getOrderInfoByUid(user.getUid());
		if(orderInfo!=null){
			if(orderInfo.get("order_status").toString().equals("6")){
				//获取快递信息
				PageData expressInfo =  orderService.getOrderExpress(orderInfo.get("orderNo").toString());
				result.put("expressInfo", expressInfo);
			}
		}
		else{
			return new JsonResult(JsonResultCode.NULL,"暂时没订单",null);
		}
		result.put("orderInfo", orderInfo);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
    }
    
    /**
     * 更新订单地址(小程序)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping("/updateOrderAddress")
    @ResponseBody
    public JsonResult updateOrderAddress(
    		Address address,String uno){
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
    	
		Map<String,Object> result = new HashMap<>();
	  	  User user = userService.getUserByUno(uno);
		  address = addressService.getAddressById(address.getAddress_id());
		  address.setUid(user.getUid());
		  address.setIs_main_address(1);
		  addressService.setMainAddress(address);
	PageData order = orderService.getBeginOrderInfoByUid(user.getUid());
	System.out.println("updateOrderAddress---order==="+order);
		if(order!=null){
	      PageData pd =  new PageData(); 
	      pd.put("orderNo", order.get("orderNo").toString());
	      pd.put("consignee", address.getName());
	      pd.put("phone", address.getPhone());
	      pd.put("city", address.getCity());
	      pd.put("address", address.getDetail());
	      orderService.updateOrderInfo(pd);
		}
		
	  return new JsonResult(JsonResultCode.SUCCESS,"地址更新成功",null);
    }
    
    
    /**
     * 获取订单搭配信息(小程序)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping("/getCollocationInfo")
    @ResponseBody
    public JsonResult getCollocationInfo(String uno){
    	
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
    	
		Map<String,Object> result = new HashMap<>();
	  	User user = userService.getUserByUno(uno);
	  	Order order = orderService.getMyBox(user.getUid());
		PageData collocationA = orderService.getCollocationInfo(order.getOrder_no(),"A");
		if(collocationA!=null){
			List<PageData> goodsA = orderService.getOrderDetailInfo(collocationA);
			collocationA.put("goodsA", goodsA);
		}
		PageData collocationB = orderService.getCollocationInfo(order.getOrder_no(),"B");
		if(collocationB!=null){
			List<PageData> goodsB = orderService.getOrderDetailInfo(collocationB);
			collocationB.put("goodsB", goodsB);
		}
		result.put("nickName", user.getNickname());
		result.put("collocationA", collocationA);
		result.put("collocationB", collocationB);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
    }
    
    
    
    /**
     * 获取订单搭配信息(小程序)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping("/getCollInfoByOrderNo")
    @ResponseBody
    public JsonResult getCollInfoByOrderNo(String uno,String orderNo){
    	
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
    	
		Map<String,Object> result = new HashMap<>();
	  	User user = userService.getUserByUno(uno);
		PageData collocationA = orderService.getCollocationInfo(orderNo,"A");
		if(collocationA!=null){
			List<PageData> goodsA = orderService.getOrderDetailInfo(collocationA);
			collocationA.put("goodsA", goodsA);
		}
		PageData collocationB = orderService.getCollocationInfo(orderNo,"B");
		if(collocationB!=null){
			List<PageData> goodsB = orderService.getOrderDetailInfo(collocationB);
			collocationB.put("goodsB", goodsB);
		}
		result.put("nickName", user.getNickname());
		result.put("collocationA", collocationA);
		result.put("collocationB", collocationB);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
    }
    
    
    //选购商品信息
    @RequestMapping("/getBill")
    @ResponseBody
    public JsonResult getBill(String uno){
    	
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
    	
		Map<String,Object> result = new HashMap<>();
	  	User user = userService.getUserByUno(uno);
    	Order order = orderService.getMyBox(user.getUid());
    	String orderNo = order.getOrder_no();
    	List<OrderDetail> skuList = orderService.findOrderDetailListByOrderNo(order.getOrder_no());
    	
    	ObjectMapper mapper = new ObjectMapper();
    	int skuNum = skuList.size();
    	int buyNum = 0;
    	for (OrderDetail sku : skuList) {
    		if (sku.getIs_buy() == 1) {
    			buyNum++;
    		}
    		
    		if (StringUtils.isNotEmpty(sku.getComment())) {
    			String json = null;
    			String commentJson = sku.getComment();
    			Map<String,String> commentMap = new HashMap<>();
    			List<Map<String, String>> commentList = new ArrayList<>();
				try {
					commentList = mapper.readValue(commentJson,new TypeReference<List<Map<String,String>>>() { });
					for (Map<String,String> map : commentList) {
						commentMap.put(map.get("about"), map.get("reason"));
					}
					json = mapper.writeValueAsString(commentMap);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sku.setComment(json);
    		}
    	}
    	
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
//    	Voucher voucher = voucherService.getAvailableVoucherByUid(uid);
//    	if (voucher != null) {
//    		voucher_amount = voucher.getAmount();
//    	}
    	
    	if (buyNum > 0) {    
    		total_amount = orderService.getOrderTotalAmount(orderNo);
    		discount_amount = total_amount.multiply(new BigDecimal(1-discount)).setScale(2, BigDecimal.ROUND_HALF_UP);
    	}
    	if(user.getIs_member()==0){
    		service_amount =new BigDecimal(19);
//    		service_amount =new BigDecimal(0.19);
    	}
    	
    	//更新签收状态
    	int callback = orderService.updateSignStatus(orderNo);
    	
    	BigDecimal pay_amount = total_amount.subtract(discount_amount).subtract(voucher_amount).subtract(service_amount);
    	int r=pay_amount.compareTo(BigDecimal.ZERO); //和0，Zero比较
    	if(r==-1) //小于
    	{
    		pay_amount = new BigDecimal(0);
    	}
    	result.put("skuList", skuList);
    	result.put("total_amount", total_amount);
    	result.put("voucher_amount", voucher_amount);
    	result.put("discount_amount", discount_amount);
    	result.put("service_amount", service_amount);
    	result.put("pay_amount", pay_amount);
    	result.put("order_no", orderNo);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
    }
    
    
    /**
     * 退回押金更新(小程序)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping("/updateDeposit")
    @ResponseBody
    public JsonResult updateDeposit(String orderNo){
		Map<String,Object> result = new HashMap<>();
	  Order order = orderService.getOrderByOrderNo(orderNo);
	  order.setDeposit_status(2);
	  orderService.insertOrUpdate(order);
     
	  return new JsonResult(JsonResultCode.SUCCESS,"更新成功",null);
    }
    
    /**
     * 用户签收
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/signup")
    @ResponseBody
    public JsonResult signup( @RequestParam("orderNo") String orderNo,String uno){
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
    	
    	User user = userService.getUserByUno(uno);
		if(user==null){
			return new JsonResult(JsonResultCode.FAILURE,"用户不存在","");
		}
		
		PageData orderPd = orderService.getOrderBy3(user.getUid());
		if(orderPd==null){
			return new JsonResult(JsonResultCode.FAILURE,"订单不存在","");
		}
		
		Map<String,Object> result = new HashMap<>();
    	Order order = orderService.getOrderByOrderNo(orderNo);
		if(order==null){
			return new JsonResult(JsonResultCode.FAILURE,"没订单","");
		}
		order.setOrder_status(4);
		order.setSigntime(DateUtil.getCurrentDateTimeStr());
		orderService.insertOrUpdate(order);
//		result.put("order", order);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功","");
    }
	
    
}
