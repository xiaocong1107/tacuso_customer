package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_quartz")
public class Quartz  extends SuperEntity {
   

	private Integer status;
	private String type;
    private String createtime;
    

	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
