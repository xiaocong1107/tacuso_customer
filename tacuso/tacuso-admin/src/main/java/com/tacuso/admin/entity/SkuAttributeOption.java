package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_attribute_option")
public class SkuAttributeOption extends SuperEntity {
    @TableId
    private Integer sku_attribute_option_id;
    private Integer sku_attribute_id;
    private String  sku_attribute_value;

    public Integer getSku_attribute_option_id() {
        return sku_attribute_option_id;
    }

    public void setSku_attribute_option_id(Integer sku_attribute_option_id) {
        this.sku_attribute_option_id = sku_attribute_option_id;
    }

    public Integer getSku_attribute_id() {
        return sku_attribute_id;
    }

    public void setSku_attribute_id(Integer sku_attribute_id) {
        this.sku_attribute_id = sku_attribute_id;
    }

    public String getSku_attribute_value() {
        return sku_attribute_value;
    }

    public void setSku_attribute_value(String sku_attribute_value) {
        this.sku_attribute_value = sku_attribute_value;
    }
}
