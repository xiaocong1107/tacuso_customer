package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_page")
public class Page extends SuperEntity {

    @TableId
    private Integer page_id;
    private String page_name;
    private Integer page_type;
	private Integer page_index;
	private Integer next_index;
    private Integer sort;

    public Integer getPage_id() {
        return page_id;
    }

    public void setPage_id(Integer page_id) {
        this.page_id = page_id;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPage_type() {
        return page_type;
    }

    public void setPage_type(Integer page_type) {
        this.page_type = page_type;
    }
    
    public Integer getPage_index() {
		return page_index;
	}

	public void setPage_index(Integer page_index) {
		this.page_index = page_index;
	}

    public Integer getNext_index() {
		return next_index;
	}

	public void setNext_index(Integer next_index) {
		this.next_index = next_index;
	}

}
