package com.tacuso.buyer.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.OrderMapper;
import com.tacuso.buyer.dao.UserMapper;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.entity.MemberPayOrder;
import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.OrderAccounts;
import com.tacuso.buyer.entity.OrderDetail;
import com.tacuso.buyer.entity.OrderInfo;
import com.tacuso.buyer.entity.PayOrder;
import com.tacuso.buyer.entity.PayOrderDetail;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.service.OrderService;
import com.tacuso.buyer.utils.DateUtil;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.vo.PageData;
import com.tacuso.buyer.vo.PayOrderVo;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private UserMapper userMapper;

	@Override
	public Order getMyBox(Integer uid) {
		return orderMapper.getLastOrder(uid);
	}

	@Override
	public Order getOrderByOrderNo(String order_no) {
		return orderMapper.getOrderByOrderNo(order_no);
	}

	@Override
	public Integer saveMemberPayOrder(MemberPayOrder order) {
		Long rt = new Date().getTime() / 1000;
		order.setRt(new BigInteger(rt.toString()));
		return orderMapper.saveMemberPayOrder(order);
	}

	@Override
	public MemberPayOrder getMemberPayOrderByOrderId(String orderId) {
		return orderMapper.getMemberPayOrderByOrderId(orderId);
	}
	
	@Override
	public MemberPayOrder getMemberPayOrderByOrderNo(String orderNo) {
		return orderMapper.getMemberPayOrderByOrderNo(orderNo);
	}


	@Override
	public Integer updateMemberPayOrder(MemberPayOrder order) {
		return orderMapper.updateMemberPayOrder(order);
	}

	@Override
	public List<PayOrderVo> getHistoryPayOrderListByUid(Integer uid) {
		return orderMapper.getHistoryPayOrderListByUid(uid);
	}

	@Override
	public List<OrderDetail> findOrderDetailListByOrderNo(String order_no) {
		return orderMapper.findOrderDetailListByOrderNo(order_no);
	}

	@Override
	public Integer updateOrderDetail(Map<String, Object> detailMap) {
		return orderMapper.updateOrderDetail(detailMap);
	}

	@Override
	public BigDecimal getOrderTotalAmount(String order_no) {
		return orderMapper.getOrderTotalAmount(order_no);
	}

	@Override
	public Integer savePayOrder(PayOrder payOrder) {
		Long rt = new Date().getTime() / 1000;
		payOrder.setRt(new BigInteger(rt.toString()));
		return orderMapper.savePayOrder(payOrder);
	}

	@Override
	public Integer savePayOrderDetail(PayOrderDetail payOrderDetail) {
		return orderMapper.savePayOrderDetail(payOrderDetail);
	}

	@Override
	public PayOrder getPayOrderByOrderNo(String order_no) {
		return orderMapper.getPayOrderByOrderNo(order_no);
	}
	
	@Override
	public PayOrder getPayOrderByPayOrderNo(String pay_order_no) {
		return orderMapper.getPayOrderByPayOrderNo(pay_order_no);
	}
	
	@Override
	public List<PayOrderDetail> findPayOrderDetailListByOrderNo(String pay_order_no) {
		return orderMapper.findPayOrderDetailListByOrderNo(pay_order_no);
	}

	@Override
	public Integer updatePayOrder(PayOrder order) {
		return orderMapper.updatePayOrder(order);
	}

	@Override
	public OrderDetail findOrderDetailByOrderNoAndNum(String order_no, int serial_num) {
		return orderMapper.findOrderDetailByOrderNoAndNum(order_no,serial_num);
	}

	@Override
	public Integer saveExpressLog(int status,int uid,String starttime,String endtime,String createtime,Long TimeStsamp,String username,String phone,String remark,String orderNo,String deliverAddress,String shopName,String shopPhone,String takeAddress){
		return orderMapper.saveExpressLog(status,uid,starttime,endtime,createtime,TimeStsamp,username,phone,deliverAddress,remark,orderNo,shopName,shopPhone,takeAddress);
	}
	
	@Override
	public Integer updateOrderExpressstatus(String orderNo) {
		return orderMapper.updateOrderExpressstatus(orderNo);
	}
	
	@Override
	public Integer saveOrderInfo(OrderInfo orderInfo) {
		Long rt = new Date().getTime() / 1000;
		orderInfo.setRt(new BigInteger(rt.toString()));
		return orderMapper.saveOrderInfo(orderInfo);
	}
	
	@Override
	public List<Order> findserviceExpireList(){
		return orderMapper.findserviceExpireList();
	}
	
	@Override
	public Integer saveOrderEvaluate(int uid,int service,int style,String content,String orderNo,String createtime,String shop_code){
		return orderMapper.saveOrderEvaluate(uid,service,style,content,orderNo,createtime,shop_code);
	}
	
	@Override
	public Integer editOrderEvaluate(int uid,int service,int style,String content,String orderNo,String createtime){
		return orderMapper.editOrderEvaluate(uid,service,style,content,orderNo,createtime);
	}
	
	public String getYuyueTime(int uid) {
		return orderMapper.getYuyueTime(uid);
	}
	
	@Override
	public Integer createOrder(int uid,String answerList,Address address,String buyer_msg) {
	        Date d = new Date();   
	        User user =  userMapper.getUserByUid(uid);
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
	        String dateNowStr = sdf.format(d);  
	       Order order = new Order();
	       order.setBegintime(DateUtil.getCurrentDateTimeStr());
	       order.setUid(uid);
	       order.setOrder_no(dateNowStr+this.orderMapper.getOrderNo()+Tools.getRandomNum());//流水号
	       order.setOrder_status(0);
	       //插入地址信息
	       order.setAddress_id(address.getAddress_id());
	       order.setConsignee(address.getName());
	       order.setCity(address.getCity());
	       order.setAddress(address.getDetail());
	       order.setPhone(address.getPhone());
	       order.setBuyer_msg(buyer_msg);
	       order.setFreight_id(1);
	       if(user.getIs_member()==0){
	    	   order.setIf_want(0);//非会员的默认为不要，支付后修改为要
	       }
	       else{
	    	   order.setIf_want(1);//会员默认为要
	       }
	       //插入订单明细
	   		OrderInfo newOrderInfo = new OrderInfo();
	   		newOrderInfo.setStyle(answerList);
	   		newOrderInfo.setOrderNo(order.getOrder_no());
	   		newOrderInfo.setUid(uid);
	   		newOrderInfo.setAddtime(DateUtil.getCurrentDateTimeStr());
	   		Long rt = new Date().getTime() / 1000;
	   		newOrderInfo.setRt(new BigInteger(rt.toString()));
			orderMapper.saveOrderInfo(newOrderInfo);
			
		   int count = this.orderMapper.createOrder(order);
		return count;
	}
	
	@Override
	public OrderInfo getOrderInfo(String order_no) {
		return orderMapper.getOrderInfo(order_no);
	}
	
	@Override
	public Integer updateSignStatus(String order_no) {
		return orderMapper.updateSignStatus(order_no);
	}
	
	@Override
	public String getFreightPrice(String order_no) {
		return orderMapper.getFreightPrice(order_no);
	}
	
	@Override
	public String getAllFreightPrice() {
		return orderMapper.getAllFreightPrice();
	}
	
	@Override
	public void callInsertShopAccounts() {
		orderMapper.callInsertShopAccounts();
	}
	
	@Override
	public void callUpdateShopAccounts() {
		orderMapper.callUpdateShopAccounts();
	}
	
	
	@Override
	public List<PageData> getHistoryOrderList(int uid,int page) {
		page = (page-1)*10;
		return (List<PageData>)orderMapper.getHistoryOrderList(uid,page);
	}
	
	@Override
	public int getHistoryOrderCount(int uid) {
		return orderMapper.getHistoryOrderCount(uid);
	}
	
	@Override
	public PageData getHistoryOrderInfo(int uid,String orderNo) {
		PageData pd = new PageData();
		pd.put("orderNo", orderNo);
		pd.put("uid", uid);
		return orderMapper.getHistoryOrderInfo(pd);
	}
	
	@Override
	public List<PageData> getHistoryOrderDetailList(int uid,String orderNo) {
		PageData pd = new PageData();
		pd.put("orderNo", orderNo);
		return (List<PageData>)orderMapper.getHistoryOrderDetailList(pd);
	}
	
	@Override
	public PageData getOrderInfoByUid(int uid) {
		Order order = orderMapper.getLastOrder(uid);
		PageData orderInfo =null;
		if(order!=null){
			if(order.getOrder_status()==0||order.getOrder_status()==1){//已下单
				orderInfo = orderMapper.getBeginOrderInfoByUid(uid,order.getOrder_status());
			}
			else if(order.getOrder_status()==2||order.getOrder_status()==21){//搭配中
				orderInfo = orderMapper.getBeginOrderInfoByUid(uid,order.getOrder_status());
			}
			else if(order.getOrder_status()==3){//已发货
				orderInfo = orderMapper.getBeginOrderInfoByUid(uid,order.getOrder_status());
			}
			else if(order.getOrder_status()==4){//已签收
				orderInfo = orderMapper.getBeginOrderInfoByUid(uid,order.getOrder_status());
			}
			else if(order.getOrder_status()==5){//等待商品寄回
				orderInfo = orderMapper.getPayOrderInfoByUid(uid,order.getOrder_status());
			}
			else if(order.getOrder_status()==6){//商品寄回中
				orderInfo = orderMapper.getExpressOrderInfoByUid(uid,order.getOrder_status());
			}	
		}
		return orderInfo;
	}
	
	@Override
	public void updateOrderInfo(PageData pd) {
		orderMapper.updateOrderInfo(pd);
	}
	
	@Override
	public PageData getCollocationInfo(String orderNo,String type) {
		PageData pd = new PageData();
		pd.put("orderNo", orderNo);
		pd.put("type", type);
		
		return orderMapper.getCollocationInfo(pd);
	}
	
	@Override
	public List<PageData> getOrderDetailInfo(PageData pd) {
		return (List<PageData>)orderMapper.getOrderDetailInfo(pd);
	}
	
	@Override
	public void updateDeposit(PageData pd) {
		orderMapper.updateDeposit(pd);
	}
	
	@Override
	public PageData getOrderBy3(int uid) {
		PageData orderInfo = orderMapper.getBeginOrderInfoByUid(uid,3);
		return orderInfo;
	}
	
	@Override
	public PageData getOrderExpress(String orderNo) {
		return (PageData)orderMapper.getOrderExpress(orderNo);
	}
	
	@Override
	public PageData getOrderEvaluate(String orderNo) {
		return (PageData)orderMapper.getOrderEvaluate(orderNo);
	}
	
	@Override
	public int delOrderExpress(String orderNo) {
		return (int)orderMapper.delOrderExpress(orderNo);
	}

	@Override
	public Integer saveOrderAccounts(OrderAccounts orderAccounts) {
		return orderMapper.saveOrderAccounts(orderAccounts);
	}
	
	@Override
	public Integer handleOrder(PayOrder payOrder,OrderAccounts orderAccounts) {
		orderMapper.updatePayOrder(payOrder);
		return orderMapper.saveOrderAccounts(orderAccounts);
	}
	
	@Override
	public PageData getBeginOrderInfoByUid(int uid) {
		return orderMapper.getBeginOrderInfoByUid(uid,0);
	}
	
}
