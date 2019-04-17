package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.BrandMapper;
import com.tacuso.admin.entity.Brand;
import com.tacuso.admin.service.BrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper,Brand> implements BrandService {


}