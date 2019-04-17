package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_sms_log")
public class SendSmsLog  extends SuperEntity {
   

	private String phone;
    private String name;
	private String remark;

	private Boolean isSendSuccess;
    private String createtime;
    
    public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
    public Boolean getIsSendSuccess() {
		return isSendSuccess;
	}
	public void setIsSendSuccess(Boolean isSendSuccess) {
		this.isSendSuccess = isSendSuccess;
	}
}
