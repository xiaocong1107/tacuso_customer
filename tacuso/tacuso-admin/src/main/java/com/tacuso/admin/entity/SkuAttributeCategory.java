package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_attribute_category")
public class SkuAttributeCategory extends SuperEntity {
    @TableId
    private Integer sku_attribute_category_id;
    private String sku_attribute_category_name;

    public Integer getSku_attribute_category_id() {
        return sku_attribute_category_id;
    }

    public void setSku_attribute_category_id(Integer sku_attribute_category_id) {
        this.sku_attribute_category_id = sku_attribute_category_id;
    }

    public String getSku_attribute_category_name() {
        return sku_attribute_category_name;
    }

    public void setSku_attribute_category_name(String sku_attribute_category_name) {
        this.sku_attribute_category_name = sku_attribute_category_name;
    }
}
