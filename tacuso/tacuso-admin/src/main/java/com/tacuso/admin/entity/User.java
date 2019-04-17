package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_user")
public class User extends SuperEntity{
    @TableId
    private Integer uid;
    private Integer wx_uid;
    private String bindphone;
    private String nickname;
    private String email;
    private String username;
    private String password;
    private String salt;
    private Integer is_employee;
    private Integer is_member;
    private Integer growth;
    private Integer score;
    private Integer level_id;
    private Integer credit_level_id;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getIs_employee() {
        return is_employee;
    }

    public void setIs_employee(Integer is_employee) {
        this.is_employee = is_employee;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLevel_id() {
        return level_id;
    }

    public void setLevel_id(Integer level_id) {
        this.level_id = level_id;
    }

    public Integer getCredit_level_id() {
        return credit_level_id;
    }

    public void setCredit_level_id(Integer credit_level_id) {
        this.credit_level_id = credit_level_id;
    }

    public Integer getIs_member() {
        return is_member;
    }

    public void setIs_member(Integer is_member) {
        this.is_member = is_member;
    }
}
