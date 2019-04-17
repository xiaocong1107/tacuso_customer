package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_figure_info")
public class FigureInfo extends SuperEntity {
    @TableId
    private Integer info_id;
    private Integer uid;
    private Integer wx_uid;

    private String height;
    private String weight;
    private String tshirt_size;
    private String shirt_size;
    private String trousers_size;
    private String shoulder_size;
    private String sleeve_length;
    private String bust;
    private String waistline;
    private String hips;
    private String thigh_size;
    private String leg_size;
    private String pants_length;
    private String body_type;

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTshirt_size() {
        return tshirt_size;
    }

    public void setTshirt_size(String tshirt_size) {
        this.tshirt_size = tshirt_size;
    }

    public String getShirt_size() {
        return shirt_size;
    }

    public void setShirt_size(String shirt_size) {
        this.shirt_size = shirt_size;
    }

    public String getTrousers_size() {
        return trousers_size;
    }

    public void setTrousers_size(String trousers_size) {
        this.trousers_size = trousers_size;
    }

    public String getShoulder_size() {
        return shoulder_size;
    }

    public void setShoulder_size(String shoulder_size) {
        this.shoulder_size = shoulder_size;
    }

    public String getSleeve_length() {
        return sleeve_length;
    }

    public void setSleeve_length(String sleeve_length) {
        this.sleeve_length = sleeve_length;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHips() {
        return hips;
    }

    public void setHips(String hips) {
        this.hips = hips;
    }

    public String getThigh_size() {
        return thigh_size;
    }

    public void setThigh_size(String thigh_size) {
        this.thigh_size = thigh_size;
    }

    public String getLeg_size() {
        return leg_size;
    }

    public void setLeg_size(String leg_size) {
        this.leg_size = leg_size;
    }

    public String getPants_length() {
        return pants_length;
    }

    public void setPants_length(String pants_length) {
        this.pants_length = pants_length;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }
}
