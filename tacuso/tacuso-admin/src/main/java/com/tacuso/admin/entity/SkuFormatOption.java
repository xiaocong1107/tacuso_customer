package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_format_option")
public class SkuFormatOption extends SuperEntity {
    @TableId
    private Integer sku_format_options_id;
    private Integer sku_format_id;
    private String sku_format_value;

    public Integer getSku_format_options_id() {
        return sku_format_options_id;
    }

    public void setSku_format_options_id(Integer sku_format_options_id) {
        this.sku_format_options_id = sku_format_options_id;
    }

    public Integer getSku_format_id() {
        return sku_format_id;
    }

    public void setSku_format_id(Integer sku_format_id) {
        this.sku_format_id = sku_format_id;
    }

    public String getSku_format_value() {
        return sku_format_value;
    }

    public void setSku_format_value(String sku_format_value) {
        this.sku_format_value = sku_format_value;
    }
}
