package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_user_info")
public class UserInfo extends SuperEntity {
    @TableId
    private Integer info_id;
    private Integer uid;
    private Integer wx_uid;

    private String nickname;
    private String industy;
    private String position;
    private String birthday;
    private String fullbody_shot;

    public String getFullbody_shot() {
        return fullbody_shot;
    }

    public void setFullbody_shot(String fullbody_shot) {
        this.fullbody_shot = fullbody_shot;
    }

    public Integer getInfo_id() {
        return info_id;
    }

    public void setInfo_id(Integer info_id) {
        this.info_id = info_id;
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

    public String getIndusty() {
        return industy;
    }

    public void setIndusty(String industy) {
        this.industy = industy;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
