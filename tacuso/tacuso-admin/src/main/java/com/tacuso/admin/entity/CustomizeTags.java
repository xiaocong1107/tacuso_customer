package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_customize_tags")
public class CustomizeTags extends SuperEntity {
    @TableId(type = IdType.AUTO)
    private Integer tag_id;
    private Integer tag_category_id;
    private String tag_name;
    private String tag_key;

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public Integer getTag_category_id() {
        return tag_category_id;
    }

    public void setTag_category_id(Integer tag_category_id) {
        this.tag_category_id = tag_category_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_key() {
        return tag_key;
    }

    public void setTag_key(String tag_key) {
        this.tag_key = tag_key;
    }
}
