package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_order_info")
public class OrderInfo extends SuperEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
    private Integer info_id;
    
    private Integer uid;
    
    private String orderNo;
    
    private String style;

	private String addtime;
	
    public Integer getInfo_id() {
		return info_id;
	}

	public void setInfo_id(Integer info_id) {
		this.info_id = info_id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
    
    public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

}
