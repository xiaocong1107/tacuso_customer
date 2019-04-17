package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku")
public class Sku extends SuperEntity {
    @TableId
    private Integer sku_id;
    private Integer spu_id;
    private String  sku_name;
    private Integer stock_num;
    private Integer cost_price;
    private Integer market_price;
    private Integer sale_price;
    private Integer onsale;

    public Integer getSku_id() {
        return sku_id;
    }

    public void setSku_id(Integer sku_id) {
        this.sku_id = sku_id;
    }

    public Integer getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(Integer spu_id) {
        this.spu_id = spu_id;
    }

    public String getSku_name() {
        return sku_name;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
    }

    public Integer getStock_num() {
        return stock_num;
    }

    public void setStock_num(Integer stock_num) {
        this.stock_num = stock_num;
    }

    public Integer getCost_price() {
        return cost_price;
    }

    public void setCost_price(Integer cost_price) {
        this.cost_price = cost_price;
    }

    public Integer getMarket_price() {
        return market_price;
    }

    public void setMarket_price(Integer market_price) {
        this.market_price = market_price;
    }

    public Integer getSale_price() {
        return sale_price;
    }

    public void setSale_price(Integer sale_price) {
        this.sale_price = sale_price;
    }

    public Integer getOnsale() {
        return onsale;
    }

    public void setOnsale(Integer onsale) {
        this.onsale = onsale;
    }
}
