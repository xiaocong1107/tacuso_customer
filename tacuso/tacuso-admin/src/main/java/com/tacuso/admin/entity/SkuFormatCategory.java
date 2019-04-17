package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_format_category")
public class SkuFormatCategory extends SuperEntity {

    @TableId
    private Integer sku_format_category_id;
    private String sku_format_category_name;

    public Integer getSku_format_category_id() {
        return sku_format_category_id;
    }

    public void setSku_format_category_id(Integer sku_format_category_id) {
        this.sku_format_category_id = sku_format_category_id;
    }

    public String getSku_format_category_name() {
        return sku_format_category_name;
    }

    public void setSku_format_category_name(String sku_format_category_name) {
        this.sku_format_category_name = sku_format_category_name;
    }
}
