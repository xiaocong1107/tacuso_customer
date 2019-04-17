package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_attribute_related")
public class SkuAttributeRelated extends SuperEntity {

    @TableId
    private Integer sku_attribute_related_id;
    private Integer spu_id;
    private Integer sku_id;
    private Integer sku_attribute_option_id;

    public Integer getSku_attribute_related_id() {
        return sku_attribute_related_id;
    }

    public void setSku_attribute_related_id(Integer sku_attribute_related_id) {
        this.sku_attribute_related_id = sku_attribute_related_id;
    }

    public Integer getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(Integer spu_id) {
        this.spu_id = spu_id;
    }

    public Integer getSku_id() {
        return sku_id;
    }

    public void setSku_id(Integer sku_id) {
        this.sku_id = sku_id;
    }

    public Integer getSku_attribute_option_id() {
        return sku_attribute_option_id;
    }

    public void setSku_attribute_option_id(Integer sku_attribute_option_id) {
        this.sku_attribute_option_id = sku_attribute_option_id;
    }
}
