package com.tacuso.admin.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_clothing_info")
public class ClothingInfo extends SuperEntity {
    @TableId
    private Integer info_id;
    private Integer uid;
    private Integer wx_uid;
    private String work_style;
    private String life_style;
    private String shirt_style;
    private String trousers_style;
    private String favorite_color;
    private String try_color;
    private String disgust_color;
    private String disgust_style;

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

    public String getWork_style() {
        return work_style;
    }

    public void setWork_style(String work_style) {
        this.work_style = work_style;
    }

    public String getLife_style() {
        return life_style;
    }

    public void setLife_style(String life_style) {
        this.life_style = life_style;
    }

    public String getShirt_style() {
        return shirt_style;
    }

    public void setShirt_style(String shirt_style) {
        this.shirt_style = shirt_style;
    }

    public String getTrousers_style() {
        return trousers_style;
    }

    public void setTrousers_style(String trousers_style) {
        this.trousers_style = trousers_style;
    }

    public String getFavorite_color() {
        return favorite_color;
    }

    public void setFavorite_color(String favorite_color) {
        this.favorite_color = favorite_color;
    }

    public String getTry_color() {
        return try_color;
    }

    public void setTry_color(String try_color) {
        this.try_color = try_color;
    }

    public String getDisgust_color() {
        return disgust_color;
    }

    public void setDisgust_color(String disgust_color) {
        this.disgust_color = disgust_color;
    }

    public String getDisgust_style() {
        return disgust_style;
    }

    public void setDisgust_style(String disgust_style) {
        this.disgust_style = disgust_style;
    }
}
