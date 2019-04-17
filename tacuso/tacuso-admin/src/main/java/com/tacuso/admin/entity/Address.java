package com.tacuso.admin.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

import java.math.BigInteger;

@TableName("tacuso_address")
public class Address extends SuperEntity {
    @TableId
    private Integer address_id;

    private Integer uid;

    private String name;

    private Integer phone;

    private String detail;

    private BigInteger area_code;

    private String remark;


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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigInteger getArea_code() {
        return area_code;
    }

    public void setArea_code(BigInteger area_code) {
        this.area_code = area_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
