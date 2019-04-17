package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_format")
public class SkuFormat extends SuperEntity{

    @TableId
    private Integer sku_format_id;
    private String sku_format_name;
    private Integer sku_format_category_id;
    private Integer sku_format_group_id;

    public Integer getSku_format_id() {
        return sku_format_id;
    }

    public void setSku_format_id(Integer sku_format_id) {
        this.sku_format_id = sku_format_id;
    }

    public String getSku_format_name() {
        return sku_format_name;
    }

    public void setSku_format_name(String sku_format_name) {
        this.sku_format_name = sku_format_name;
    }

    public Integer getSku_format_category_id() {
        return sku_format_category_id;
    }

    public void setSku_format_category_id(Integer sku_format_category_id) {
        this.sku_format_category_id = sku_format_category_id;
    }

    public Integer getSku_format_group_id() {
        return sku_format_group_id;
    }

    public void setSku_format_group_id(Integer sku_format_group_id) {
        this.sku_format_group_id = sku_format_group_id;
    }
}
