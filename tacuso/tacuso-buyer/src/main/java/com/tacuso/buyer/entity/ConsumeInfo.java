package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_consume_info")
public class ConsumeInfo extends SuperEntity {
    @TableId(type = IdType.AUTO)
    private Integer info_id;
    private Integer uid;
    private Integer wx_uid;
    private String tshirt_price;
    private String shirt_price;
    private String coat_price;
    private String trousers_price;
    private String favorite_brand;
    private String clothing_manner;
    private String shopping_way;

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

    public String getTshirt_price() {
        return tshirt_price;
    }

    public void setTshirt_price(String tshirt_price) {
        this.tshirt_price = tshirt_price;
    }

    public String getShirt_price() {
        return shirt_price;
    }

    public void setShirt_price(String shirt_price) {
        this.shirt_price = shirt_price;
    }

    public String getCoat_price() {
        return coat_price;
    }

    public void setCoat_price(String coat_price) {
        this.coat_price = coat_price;
    }

    public String getTrousers_price() {
        return trousers_price;
    }

    public void setTrousers_price(String trousers_price) {
        this.trousers_price = trousers_price;
    }

    public String getFavorite_brand() {
        return favorite_brand;
    }

    public void setFavorite_brand(String favorite_brand) {
        this.favorite_brand = favorite_brand;
    }

    public String getClothing_manner() {
        return clothing_manner;
    }

    public void setClothing_manner(String clothing_manner) {
        this.clothing_manner = clothing_manner;
    }

    public String getShopping_way() {
        return shopping_way;
    }

    public void setShopping_way(String shopping_way) {
        this.shopping_way = shopping_way;
    }
}
