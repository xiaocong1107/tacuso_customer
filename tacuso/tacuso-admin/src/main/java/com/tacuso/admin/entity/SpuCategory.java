package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;
@TableName("tacuso_spu_category")
public class SpuCategory extends SuperEntity {
    @TableId
    private Integer spu_category_id;
    private String category_name;
    private String category_code;
    private Integer sort;

    public Integer getSpu_category_id() {
        return spu_category_id;
    }

    public void setSpu_category_id(Integer spu_category_id) {
        this.spu_category_id = spu_category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
