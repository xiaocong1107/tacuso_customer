package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_format_group")
public class SkuFormatGroup extends SuperEntity {

    @TableId
    private Integer sku_format_group_id;
    private String sku_format_group_name;
    private Integer position;

    public Integer getSku_format_group_id() {
        return sku_format_group_id;
    }

    public void setSku_format_group_id(Integer sku_format_group_id) {
        this.sku_format_group_id = sku_format_group_id;
    }

    public String getSku_format_group_name() {
        return sku_format_group_name;
    }

    public void setSku_format_group_name(String sku_format_group_name) {
        this.sku_format_group_name = sku_format_group_name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
