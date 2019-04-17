package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.SpuMapper;
import com.tacuso.admin.entity.Spu;
import com.tacuso.admin.service.SpuService;
import org.springframework.stereotype.Service;

@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

}