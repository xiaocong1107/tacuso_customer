package com.tacuso.buyer.entity;


import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_voucher")
public class Voucher extends SuperEntity {
    @TableId
    private Integer voucher_id;

    private Integer uid;

    private BigDecimal amount;

    private Integer status;

    private String start_time;
    
    private String end_time;
    
	public Integer getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(Integer voucher_id) {
		this.voucher_id = voucher_id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

}
