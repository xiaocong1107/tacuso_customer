package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;

@TableName("tacuso_sku_picture")
public class SkuPicture extends SuperEntity {
    @TableId
    private Integer sku_pic_id;
    private String  pic_url;
    private Integer sku_id;
    private Integer pic_position;
    private Integer is_main_pic;

    public Integer getSku_pic_id() {
        return sku_pic_id;
    }

    public void setSku_pic_id(Integer sku_pic_id) {
        this.sku_pic_id = sku_pic_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public Integer getSku_id() {
        return sku_id;
    }

    public void setSku_id(Integer sku_id) {
        this.sku_id = sku_id;
    }

    public Integer getPic_position() {
        return pic_position;
    }

    public void setPic_position(Integer pic_position) {
        this.pic_position = pic_position;
    }

    public Integer getIs_main_pic() {
        return is_main_pic;
    }

    public void setIs_main_pic(Integer is_main_pic) {
        this.is_main_pic = is_main_pic;
    }
}
