package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.SkuFormatMapper;
import com.tacuso.admin.entity.SkuFormat;
import com.tacuso.admin.service.SkuFormatService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SkuFormatServiceImpl extends ServiceImpl<SkuFormatMapper, SkuFormat> implements SkuFormatService {

    public List<SkuFormat> getAllSkuFormat(){
        return null;
    }



}