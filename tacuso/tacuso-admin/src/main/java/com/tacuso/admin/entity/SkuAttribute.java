package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_attribute")
public class SkuAttribute extends SuperEntity{
    @TableId
    private Integer  sku_attribute_id;
    private String   sku_attribute_name;
    private Integer  sku_attribute_category_id;

    public Integer getSku_attribute_id() {
        return sku_attribute_id;
    }

    public void setSku_attribute_id(Integer sku_attribute_id) {
        this.sku_attribute_id = sku_attribute_id;
    }

    public String getSku_attribute_name() {
        return sku_attribute_name;
    }

    public void setSku_attribute_name(String sku_attribute_name) {
        this.sku_attribute_name = sku_attribute_name;
    }

    public Integer getSku_attribute_category_id() {
        return sku_attribute_category_id;
    }

    public void setSku_attribute_category_id(Integer sku_attribute_category_id) {
        this.sku_attribute_category_id = sku_attribute_category_id;
    }
}
