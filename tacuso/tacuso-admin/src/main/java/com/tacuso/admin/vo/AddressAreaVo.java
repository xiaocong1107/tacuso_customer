package com.tacuso.admin.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class AddressAreaVo implements Serializable {
    private Integer address_id;

    private Integer uid;

    private String nickname;

    private String name;

    private Integer phone;

    private String detail;

    private BigInteger area_code;

    private Integer area_id;

    private String remark;

    private String merger_name;

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

    public String getMerger_name() {
        return merger_name;
    }

    public void setMerger_name(String merger_name) {
        this.merger_name = merger_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }
}
