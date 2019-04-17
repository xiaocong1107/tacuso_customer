package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_spu")
public class Spu extends SuperEntity {
    @TableId
    private Integer spu_id;
    private Integer spu_category_id;
    private Integer brand_id;
    private String  spu_name;
    private String  spu_code;
    private String  sales_title;
    private Integer price;
    private Integer month_sale;
    private Integer link_type;
    private String introduction;
    private String link_url;
    private String keywords;
    private Integer on_sale;
    private Integer sort;

    public Integer getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(Integer spu_id) {
        this.spu_id = spu_id;
    }

    public Integer getSpu_category_id() {
        return spu_category_id;
    }

    public void setSpu_category_id(Integer spu_category_id) {
        this.spu_category_id = spu_category_id;
    }

    public Integer getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    public String getSpu_name() {
        return spu_name;
    }

    public void setSpu_name(String spu_name) {
        this.spu_name = spu_name;
    }

    public String getSpu_code() {
        return spu_code;
    }

    public void setSpu_code(String spu_code) {
        this.spu_code = spu_code;
    }

    public String getSales_title() {
        return sales_title;
    }

    public void setSales_title(String sales_title) {
        this.sales_title = sales_title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMonth_sale() {
        return month_sale;
    }

    public void setMonth_sale(Integer month_sale) {
        this.month_sale = month_sale;
    }

    public Integer getLink_type() {
        return link_type;
    }

    public void setLink_type(Integer link_type) {
        this.link_type = link_type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(Integer on_sale) {
        this.on_sale = on_sale;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
