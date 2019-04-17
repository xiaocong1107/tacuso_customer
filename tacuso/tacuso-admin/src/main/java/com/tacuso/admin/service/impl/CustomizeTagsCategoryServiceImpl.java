package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.CustomizeTagsCategoryMapper;
import com.tacuso.admin.entity.CustomizeTagsCategory;
import com.tacuso.admin.service.CustomizeTagsCategoryService;
import org.springframework.stereotype.Service;

@Service
public class CustomizeTagsCategoryServiceImpl extends ServiceImpl<CustomizeTagsCategoryMapper, CustomizeTagsCategory> implements CustomizeTagsCategoryService {
}
