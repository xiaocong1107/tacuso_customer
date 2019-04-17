package com.tacuso.buyer.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_wallet")
public class Wallet extends SuperEntity{
    @TableId
    private Integer uid;
    private BigDecimal balance;
    private String bindphone;
   
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getBindphone() {
		return bindphone;
	}

	public void setBindphone(String bindphone) {
		this.bindphone = bindphone;
	}

}
