package com.tacuso.admin.vo;

import java.io.Serializable;

public class UserCommonVo implements Serializable {

    private Integer uid;
    private Integer wx_uid;
    private String bindphone;
    private String nickname;
    private String email;
    private String username;


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

    public String getBindphone() {
        return bindphone;
    }

    public void setBindphone(String bindphone) {
        this.bindphone = bindphone;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
