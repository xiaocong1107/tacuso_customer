package com.tacuso.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;
@TableName("tacuso_spu_tag")
public class SpuTag extends SuperEntity {
    @TableId
    private Integer tag_id;
    private Integer spu_id;
    private String tag_name;
    private String tag_code;
    private Integer is_main;

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public Integer getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(Integer spu_id) {
        this.spu_id = spu_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_code() {
        return tag_code;
    }

    public void setTag_code(String tag_code) {
        this.tag_code = tag_code;
    }

    public Integer getIs_main() {
        return is_main;
    }

    public void setIs_main(Integer is_main) {
        this.is_main = is_main;
    }
}
