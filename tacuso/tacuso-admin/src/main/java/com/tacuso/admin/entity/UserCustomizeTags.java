package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_user_customize_tags")
public class UserCustomizeTags extends SuperEntity{
    @TableId
    private Integer user_tag_id;
    private Integer tag_id;
    private String tag_value;
    private Integer uid;
    private Integer wx_uid;

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
}
