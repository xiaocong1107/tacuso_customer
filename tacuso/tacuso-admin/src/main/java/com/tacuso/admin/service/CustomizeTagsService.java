package com.tacuso.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.admin.entity.CustomizeTags;
import com.tacuso.admin.entity.SkuAttributeCategory;
import com.tacuso.admin.utils.PageUtil;
import com.tacuso.admin.vo.CustomizeTagsVo;

import java.util.List;

public interface CustomizeTagsService extends IService<CustomizeTags> {

    public PageUtil getAllTags(CustomizeTagsVo customizeTagsVo , int pageNum ,int pageSize);
    public List<CustomizeTags> getTagsByName(String tagName);
}
