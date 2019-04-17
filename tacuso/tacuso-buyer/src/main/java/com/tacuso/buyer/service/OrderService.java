package com.tacuso.buyer.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.entity.MemberPayOrder;
import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.OrderAccounts;
import com.tacuso.buyer.entity.OrderDetail;
import com.tacuso.buyer.entity.OrderInfo;
import com.tacuso.buyer.entity.PayOrder;
import com.tacuso.buyer.entity.PayOrderDetail;
import com.tacuso.buyer.vo.PageData;
import com.tacuso.buyer.vo.PayOrderVo;

public interface OrderService extends IService<Order> {

	Order getMyBox(Integer uid);

	Order getOrderByOrderNo(String order_no);

	Integer saveMemberPayOrder(MemberPayOrder order);

	MemberPayOrder getMemberPayOrderByOrderId(String orderId);
	
	MemberPayOrder getMemberPayOrderByOrderNo(String orderNo);

	Integer updateMemberPayOrder(MemberPayOrder order);

	List<PayOrderVo> getHistoryPayOrderListByUid(Integer uid);

	List<OrderDetail> findOrderDetailListByOrderNo(String order_no);

	Integer updateOrderDetail(Map<String, Object> detailMap);

	BigDecimal getOrderTotalAmount(String order_no);

	Integer savePayOrder(PayOrder payOrder);

	Integer savePayOrderDetail(PayOrderDetail payOrderDetail);

	PayOrder getPayOrderByOrderNo(String order_no);
	
	PayOrder getPayOrderByPayOrderNo(String pay_order_no);

	List<PayOrderDetail> findPayOrderDetailListByOrderNo(String pay_order_no);

	Integer updatePayOrder(PayOrder order);

	OrderDetail findOrderDetailByOrderNoAndNum(String order_no, int serial_num);
	
	Integer saveExpressLog(int status,int uid,String starttime,String endtime,String createtime,Long TimeStsamp,String username,String phone,String remark,String orderNo,String deliverAddress,String shopName,String shopPhone,String takeAddress);
	
	Integer updateOrderExpressstatus(String orderNo);
	
	Integer saveOrderInfo(OrderInfo orderInfo);
	
	List<Order> findserviceExpireList();
	
	Integer saveOrderEvaluate(int uid,int service,int style,String content,String orderNo,String createtime,String shop_code);
	
	Integer editOrderEvaluate(int uid,int service,int style,String content,String orderNo,String createtime);
	
	String getYuyueTime(int uid);
	
	Integer createOrder(int uid,String answerList,Address address,String buyer_msg);
	
	OrderInfo getOrderInfo(String orderNo);
	
	Integer updateSignStatus(String orderNo);
	
	String getFreightPrice(String order_no);
	String getAllFreightPrice();
	
	void callInsertShopAccounts();
	
	void callUpdateShopAccounts();

	List<PageData> getHistoryOrderList(int uid,int page);
	
	int getHistoryOrderCount(int uid);
	
	PageData getHistoryOrderInfo(int uid,String orderNo);
	
	List<PageData> getHistoryOrderDetailList(int uid,String orderNo);
	
	PageData getOrderInfoByUid(int uid);
	
	void updateOrderInfo(PageData pd);
	
	PageData getCollocationInfo(String orderNo,String type);

	List<PageData> getOrderDetailInfo(PageData pd);
	
	void updateDeposit(PageData pd);
	
	PageData getOrderBy3(int uid);
	
	PageData getOrderExpress(String orderNo);
	
	PageData getOrderEvaluate(String orderNo);
	
	int delOrderExpress(String orderNo);
	
	Integer saveOrderAccounts(OrderAccounts orderAccounts);
	
	Integer handleOrder(PayOrder payOrder,OrderAccounts orderAccounts);
	
	PageData  getBeginOrderInfoByUid(int uid);
}
