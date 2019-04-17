package com.tacuso.buyer.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_member_pay_order")
public class MemberPayOrder extends SuperEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @TableId
    private Integer id;
    
    private Integer uid;
    
    private String order_id;
    
	private String order_no;
    
    private String product;
    
    private BigDecimal total_fee;
    
    private String trade_no;
    
    private Integer is_pay;

    private String pay_time;
    
	private Integer is_member;
    
	private Integer type;
	
	private String pay_openId;
	
    public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPay_openId() {
		return pay_openId;
	}

	public void setPay_openId(String pay_openId) {
		this.pay_openId = pay_openId;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public BigDecimal getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public Integer getIs_pay() {
		return is_pay;
	}

	public void setIs_pay(Integer is_pay) {
		this.is_pay = is_pay;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	
    public Integer getIs_member() {
		return is_member;
	}

	public void setIs_member(Integer is_member) {
		this.is_member = is_member;
	}

}
