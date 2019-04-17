package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_customize_tags_category")
public class CustomizeTagsCategory extends SuperEntity {
    @TableId
    private Integer tag_category_id;
    private String category_name;
    private Integer seq;

    public Integer getTag_category_id() {
        return tag_category_id;
    }

    public void setTag_category_id(Integer tag_category_id) {
        this.tag_category_id = tag_category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
