package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_unit")
public class SkuUnit extends SuperEntity{
    @TableId
    private Integer unit_id;
    private String unit_code;
    private String unit_name;
    private Integer unit_status;

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Integer unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public Integer getUnit_status() {
        return unit_status;
    }

    public void setUnit_status(Integer unit_status) {
        this.unit_status = unit_status;
    }
}
