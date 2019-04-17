package com.tacuso.buyer.vo;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;
import com.tacuso.buyer.entity.PayOrderDetail;

@TableName("tacuso_pay_order")
public class PayOrderVo extends SuperEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
    private Integer pay_order_id;
    
    private Integer uid;
    
    private String pay_order_no;
    
    private String order_no;
    
    private BigDecimal total_amount;
    
    private BigDecimal discount_amount;
    
    private BigDecimal voucher_amount;
    
    private Integer voucher_id;
    
    private BigDecimal pay_amount;
    
    private Integer pay_status;    
    
    private String trade_no;
    
    private String pay_time;
    
    private String buyer_msg;
    
    private String consignee;
    
    private String phone;
    
    private String city;
    
    private String address;
    
    private String remark;
    
    private String express_no;

    private List<PayOrderDetail> detailList;
    
	public Integer getPay_order_id() {
		return pay_order_id;
	}

	public void setPay_order_id(Integer pay_order_id) {
		this.pay_order_id = pay_order_id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getPay_order_no() {
		return pay_order_no;
	}

	public void setPay_order_no(String pay_order_no) {
		this.pay_order_no = pay_order_no;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public BigDecimal getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(BigDecimal discount_amount) {
		this.discount_amount = discount_amount;
	}

	public BigDecimal getVoucher_amount() {
		return voucher_amount;
	}

	public void setVoucher_amount(BigDecimal voucher_amount) {
		this.voucher_amount = voucher_amount;
	}

	public Integer getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(Integer voucher_id) {
		this.voucher_id = voucher_id;
	}

	public BigDecimal getPay_amount() {
		return pay_amount;
	}

	public void setPay_amount(BigDecimal pay_amount) {
		this.pay_amount = pay_amount;
	}

	public Integer getPay_status() {
		return pay_status;
	}

	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getBuyer_msg() {
		return buyer_msg;
	}

	public void setBuyer_msg(String buyer_msg) {
		this.buyer_msg = buyer_msg;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExpress_no() {
		return express_no;
	}

	public void setExpress_no(String express_no) {
		this.express_no = express_no;
	}

	public List<PayOrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PayOrderDetail> detailList) {
		this.detailList = detailList;
	}
	
}
