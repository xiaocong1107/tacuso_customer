package com.tacuso.admin.vo;

import java.io.Serializable;

public class UserCustomizeTagsVo implements Serializable{
    private Integer user_tag_id;
    private Integer tag_id;
    private Integer tag_category_id;
    private String tag_name;
    private String tag_key;
    private String category_name;
    private String tag_value;
    private Integer uid;
    private Integer wx_uid;
    private String nickname;
    private String email;

    public Integer getUser_tag_id() {
        return user_tag_id;
    }

    public void setUser_tag_id(Integer user_tag_id) {
        this.user_tag_id = user_tag_id;
    }

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public Integer getTag_category_id() {
        return tag_category_id;
    }

    public void setTag_category_id(Integer tag_category_id) {
        this.tag_category_id = tag_category_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_key() {
        return tag_key;
    }

    public void setTag_key(String tag_key) {
        this.tag_key = tag_key;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getTag_value() {
        return tag_value;
    }

    public void setTag_value(String tag_value) {
        this.tag_value = tag_value;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getWx_uid() {
        return wx_uid;
    }

    public void setWx_uid(Integer wx_uid) {
        this.wx_uid = wx_uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
