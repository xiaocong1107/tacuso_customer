package com.tacuso.buyer.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_pay_order_detail")
public class PayOrderDetail extends SuperEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
    private Integer pay_order_detail_id;
    
    private String pay_order_no;
    
    private Integer serial_num;
    
    private Integer spu_id;
    
    private Integer sku_id;
    
    private String sku_name;
    
    private String sku_pic;
    
    private BigDecimal sku_price;
    
    private String comment;

	private String order_no;
    
    public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	
	public Integer getPay_order_detail_id() {
		return pay_order_detail_id;
	}

	public void setPay_order_detail_id(Integer pay_order_detail_id) {
		this.pay_order_detail_id = pay_order_detail_id;
	}

	public String getPay_order_no() {
		return pay_order_no;
	}

	public void setPay_order_no(String pay_order_no) {
		this.pay_order_no = pay_order_no;
	}

	public Integer getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(Integer serial_num) {
		this.serial_num = serial_num;
	}

	public Integer getSpu_id() {
		return spu_id;
	}

	public void setSpu_id(Integer spu_id) {
		this.spu_id = spu_id;
	}

	public Integer getSku_id() {
		return sku_id;
	}

	public void setSku_id(Integer sku_id) {
		this.sku_id = sku_id;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	public String getSku_pic() {
		return sku_pic;
	}

	public void setSku_pic(String sku_pic) {
		this.sku_pic = sku_pic;
	}

	public BigDecimal getSku_price() {
		return sku_price;
	}

	public void setSku_price(BigDecimal sku_price) {
		this.sku_price = sku_price;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
