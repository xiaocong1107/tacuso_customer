package com.tacuso.buyer.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_order_accounts")
public class OrderAccounts extends SuperEntity{
	
	private static final long serialVersionUID = 1L;

	@TableId
    private Integer id;
	
	private Integer uid;
    
    private String pay_order_no;
    
    private String order_no;
    
    private String shop_code;
    
    private Integer membership;
    
    private BigDecimal freight;

	private BigDecimal total_amount;
    
    private BigDecimal pay_amount;
    
    private BigDecimal discount_amount;
    
    private BigDecimal platform_amount;
    
    private BigDecimal shop_amount;

	private BigDecimal service_amount;

	private Integer type;
    
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

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}

	public Integer getMembership() {
		return membership;
	}

	public void setMembership(Integer membership) {
		this.membership = membership;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getPay_amount() {
		return pay_amount;
	}

	public void setPay_amount(BigDecimal pay_amount) {
		this.pay_amount = pay_amount;
	}

	public BigDecimal getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(BigDecimal discount_amount) {
		this.discount_amount = discount_amount;
	}

	public BigDecimal getPlatform_amount() {
		return platform_amount;
	}

	public void setPlatform_amount(BigDecimal platform_amount) {
		this.platform_amount = platform_amount;
	}

	public BigDecimal getShop_amount() {
		return shop_amount;
	}

	public void setShop_amount(BigDecimal shop_amount) {
		this.shop_amount = shop_amount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
    
    public BigDecimal getService_amount() {
		return service_amount;
	}

	public void setService_amount(BigDecimal service_amount) {
		this.service_amount = service_amount;
	}

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
