package com.tacuso.buyer.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.MemberPayOrder;
import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.OrderAccounts;
import com.tacuso.buyer.entity.OrderDetail;
import com.tacuso.buyer.entity.OrderInfo;
import com.tacuso.buyer.entity.PayOrder;
import com.tacuso.buyer.entity.PayOrderDetail;
import com.tacuso.buyer.vo.PageData;
import com.tacuso.buyer.vo.PayOrderVo;

public interface OrderMapper extends SuperMapper<Order> {

	Order getLastOrder(@Param("uid") Integer uid);

	Order getOrderByOrderNo(@Param("order_no") String order_no);

	Integer saveMemberPayOrder(@Param("order") MemberPayOrder order);

	MemberPayOrder getMemberPayOrderByOrderId(@Param("order_id")String orderId);
	
	MemberPayOrder getMemberPayOrderByOrderNo(@Param("orderNo")String orderNo);

	Integer updateMemberPayOrder(@Param("order") MemberPayOrder order);

	List<OrderDetail> findOrderDetailListByOrderNo(@Param("order_no") String order_no);

	Integer updateOrderDetail(Map<String, Object> detailMap);

	BigDecimal getOrderTotalAmount(@Param("order_no") String order_no);

	Integer savePayOrder(@Param("order") PayOrder payOrder);

	Integer savePayOrderDetail(@Param("detail") PayOrderDetail detail);

	PayOrder getPayOrderByOrderNo(@Param("order_no") String order_no);
	
	PayOrder getPayOrderByPayOrderNo(@Param("pay_order_no") String pay_order_no);

	List<PayOrderDetail> findPayOrderDetailListByOrderNo(@Param("pay_order_no") String pay_order_no);

	Integer updatePayOrder(@Param("order") PayOrder order);

	List<PayOrderVo> getHistoryPayOrderListByUid(@Param("uid") Integer uid);

	OrderDetail findOrderDetailByOrderNoAndNum(@Param("order_no") String order_no, @Param("serial_num") int serial_num);

	Integer saveExpressLog(@Param("status") Integer status,@Param("uid") Integer uid,@Param("starttime") String starttime,@Param("endtime") String endtime,@Param("createtime") String createtime, @Param("TimeStsamp") Long TimeStsamp,@Param("username") String username,@Param("phone") String phone,@Param("Address") String Address,@Param("remark") String remark,@Param("orderNo") String orderNo,@Param("shopName") String shopName,@Param("shopPhone") String shopPhone,@Param("takeAddress") String takeAddress);
	
	Integer updateOrderExpressstatus(String orderNo);
	
	Integer saveOrderInfo(@Param("orderInfo") OrderInfo orderInfo);
	
	List<Order> findserviceExpireList();
	
	Integer saveOrderEvaluate(@Param("uid") Integer uid,@Param("service") Integer service,@Param("style") Integer style,@Param("content") String content,@Param("orderNo") String orderNo,@Param("createtime") String createtime,@Param("shop_code") String shop_code);
	
	Integer editOrderEvaluate(@Param("uid") Integer uid,@Param("service") Integer service,@Param("style") Integer style,@Param("content") String content,@Param("orderNo") String orderNo,@Param("createtime") String createtime);
	
	String getYuyueTime(@Param("uid") Integer uid);
	
	public int getOrderNo();
	
	Integer createOrder(@Param("order") Order order);
	
	OrderInfo getOrderInfo(String orderNo);
	
	Integer updateSignStatus(String orderNo);
	
	String getFreightPrice(String orderNo);
	
	String getAllFreightPrice();
	
	void callInsertShopAccounts();
	
	void callUpdateShopAccounts();
	
	List<PageData> getHistoryOrderList(@Param("uid") Integer uid,@Param("page") Integer page);
	
	Integer getHistoryOrderCount(@Param("uid") Integer uid);
	
	PageData getHistoryOrderInfo(PageData pd);
	
	List<PageData> getHistoryOrderDetailList(PageData pd);
	
	PageData getBeginOrderInfoByUid(@Param("uid") Integer uid,@Param("orderStatus") Integer orderStatus);
	
	PageData getPayOrderInfoByUid(@Param("uid") Integer uid,@Param("orderStatus") Integer orderStatus);
	
	PageData getExpressOrderInfoByUid(@Param("uid") Integer uid,@Param("orderStatus") Integer orderStatus);
	
	void updateOrderInfo(PageData pd);
	
	PageData getCollocationInfo(PageData pd);
	
	List<PageData> getOrderDetailInfo(PageData pd);
	
	void updateDeposit(PageData pd);
	
	PageData getOrderExpress(String orderNo);
	
	PageData getOrderEvaluate(String orderNo);
	
	int delOrderExpress(String orderNo);

	Integer saveOrderAccounts(@Param("order") OrderAccounts orderAccounts);

}
