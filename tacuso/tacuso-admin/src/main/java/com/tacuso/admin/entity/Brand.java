package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_brand")
public class Brand extends SuperEntity {
    @TableId
    private Integer brand_id;
    private String brand_name;
    private String brand_code;
    private String brand_pic_url;
    private Integer sort;
    private Integer is_enable;


    public Integer getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_code() {
        return brand_code;
    }

    public void setBrand_code(String brand_code) {
        this.brand_code = brand_code;
    }

    public String getBrand_pic_url() {
        return brand_pic_url;
    }

    public void setBrand_pic_url(String brand_pic_url) {
        this.brand_pic_url = brand_pic_url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(Integer is_enable) {
        this.is_enable = is_enable;
    }


}
