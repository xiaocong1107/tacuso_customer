package com.tacuso.buyer.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_order_detail")
public class OrderDetail extends SuperEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
    private Integer order_detail_id;
    
    private String order_no;
    
    private Integer serial_num;

    private Integer sku_id;
    
    private String sku_name;
    
    private String sku_pic;
    
    private BigDecimal sku_price;
    
    private Integer is_buy;
    
    private String comment;

	private String content;
	
	private String brand;
	public Integer getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(Integer order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public Integer getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(Integer serial_num) {
		this.serial_num = serial_num;
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

	public Integer getIs_buy() {
		return is_buy;
	}

	public void setIs_buy(Integer is_buy) {
		this.is_buy = is_buy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

}
