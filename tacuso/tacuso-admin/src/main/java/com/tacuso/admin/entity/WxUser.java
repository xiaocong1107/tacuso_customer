package com.tacuso.admin.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_wx_user")
public class WxUser extends SuperEntity {
    @TableId
    private Integer wx_uid;

    private String wx_openid;

    private String wx_nickname;

    private String wx_sex;

    private String wx_city;

    private String wx_province;

    private String wx_country;

    private String wx_headimgurl;

    private String wx_unionid;

    public Integer getWx_uid() {
        return wx_uid;
    }

    public void setWx_uid(Integer wx_uid) {
        this.wx_uid = wx_uid;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
    }

    public String getWx_nickname() {
        return wx_nickname;
    }

    public void setWx_nickname(String wx_nickname) {
        this.wx_nickname = wx_nickname;
    }

    public String getWx_sex() {
        return wx_sex;
    }

    public void setWx_sex(String wx_sex) {
        this.wx_sex = wx_sex;
    }

    public String getWx_city() {
        return wx_city;
    }

    public void setWx_city(String wx_city) {
        this.wx_city = wx_city;
    }

    public String getWx_province() {
        return wx_province;
    }

    public void setWx_province(String wx_province) {
        this.wx_province = wx_province;
    }

    public String getWx_country() {
        return wx_country;
    }

    public void setWx_country(String wx_country) {
        this.wx_country = wx_country;
    }

    public String getWx_headimgurl() {
        return wx_headimgurl;
    }

    public void setWx_headimgurl(String wx_headimgurl) {
        this.wx_headimgurl = wx_headimgurl;
    }

    public String getWx_unionid() {
        return wx_unionid;
    }

    public void setWx_unionid(String wx_unionid) {
        this.wx_unionid = wx_unionid;
    }

}
