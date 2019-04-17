package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_order")
public class Order extends SuperEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
    private Integer order_id;
    
    private Integer uid;
    
    private String order_no;
    
    private Integer order_status;    
    
    private String buyer_msg;
    
    private Integer address_id;
    
    private String consignee;
    
    private String phone;
    
    private String city;
    
    private String address;
    
    private String remark;
    
    private String delivery_no;

	private String delivery_company_code;
    
    private String delivery_company_name;
    
	private Integer if_want;
	private Integer freight_id;
    
	private String take_order_no;
    private String shop_code;
    private String begintime;
    private String sendordertime;
    private String getordertime;
    private String handledtime;
    private String sendtime;
    private String signtime;
    private String paytime;
    private String expresstime;
    private String finishtime;

	private Integer deposit_status;//押金状态
    
	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}


	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

	public String getBuyer_msg() {
		return buyer_msg;
	}

	public void setBuyer_msg(String buyer_msg) {
		this.buyer_msg = buyer_msg;
	}

	public Integer getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
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

	public String getDelivery_no() {
		return delivery_no;
	}

	public void setDelivery_no(String delivery_no) {
		this.delivery_no = delivery_no;
	}
	
    public String getDelivery_company_code() {
		return delivery_company_code;
	}

	public void setDelivery_company_code(String delivery_company_code) {
		this.delivery_company_code = delivery_company_code;
	}

	public String getDelivery_company_name() {
		return delivery_company_name;
	}

	public void setDelivery_company_name(String delivery_company_name) {
		this.delivery_company_name = delivery_company_name;
	}
	
	 public String getTake_order_no() {
			return take_order_no;
		}

		public void setTake_order_no(String take_order_no) {
			this.take_order_no = take_order_no;
		}

		public String getShop_code() {
			return shop_code;
		}

		public void setShop_code(String shop_code) {
			this.shop_code = shop_code;
		}

		public String getBegintime() {
			return begintime;
		}

		public void setBegintime(String begintime) {
			this.begintime = begintime;
		}

		public String getSendordertime() {
			return sendordertime;
		}

		public void setSendordertime(String sendordertime) {
			this.sendordertime = sendordertime;
		}

		public String getGetordertime() {
			return getordertime;
		}

		public void setGetordertime(String getordertime) {
			this.getordertime = getordertime;
		}

		public String getHandledtime() {
			return handledtime;
		}

		public void setHandledtime(String handledtime) {
			this.handledtime = handledtime;
		}

		public String getSendtime() {
			return sendtime;
		}

		public void setSendtime(String sendtime) {
			this.sendtime = sendtime;
		}

		public String getSigntime() {
			return signtime;
		}

		public void setSigntime(String signtime) {
			this.signtime = signtime;
		}

		public String getPaytime() {
			return paytime;
		}

		public void setPaytime(String paytime) {
			this.paytime = paytime;
		}

		public String getExpresstime() {
			return expresstime;
		}

		public void setExpresstime(String expresstime) {
			this.expresstime = expresstime;
		}

		public String getFinishtime() {
			return finishtime;
		}

		public void setFinishtime(String finishtime) {
			this.finishtime = finishtime;
		}
		
	    public Integer getIf_want() {
			return if_want;
		}

		public void setIf_want(Integer if_want) {
			this.if_want = if_want;
		}

		
		public Integer getFreight_id() {
			return freight_id;
		}

		public void setFreight_id(Integer freight_id) {
			this.freight_id = freight_id;
		}

	    
		public Integer getDeposit_status() {
			return deposit_status;
		}

		public void setDeposit_status(Integer deposit_status) {
			this.deposit_status = deposit_status;
		}

}
