package com.tacuso.buyer.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

import java.math.BigInteger;

@TableName("tacuso_address")
public class Address extends SuperEntity {
    @TableId
    private Integer address_id;

    private Integer uid;

    private String name;

    private String phone;

    private String detail;

    private String city;

    private String remark;

    private Integer is_main_address;

	private String createtime;

    private String updatetime;
//	private String addressInfo;
    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIs_main_address() {
        return is_main_address;
    }

    public void setIs_main_address(Integer is_main_address) {
        this.is_main_address = is_main_address;
    }
    
    public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
    
//    public String getAddressInfo() {
//		return addressInfo;
//	}
//
//	public void setAddressInfo(String addressInfo) {
//		this.addressInfo = addressInfo;
//	}
}
