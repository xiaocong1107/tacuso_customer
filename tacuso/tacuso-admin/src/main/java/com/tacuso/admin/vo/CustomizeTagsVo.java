package com.tacuso.admin.vo;

import java.io.Serializable;

public class CustomizeTagsVo implements Serializable{
    private Integer tag_id;
    private Integer tag_category_id;
    private String tag_name;
    private String tag_key;
    private String category_name;

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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
