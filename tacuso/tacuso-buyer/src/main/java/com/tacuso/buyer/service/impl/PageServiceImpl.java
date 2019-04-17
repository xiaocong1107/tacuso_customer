package com.tacuso.buyer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.PageMapper;
import com.tacuso.buyer.entity.Page;
import com.tacuso.buyer.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl extends ServiceImpl<PageMapper,Page> implements PageService {

    @Autowired
    private PageMapper pageMapper;

    @Override
    public List<Page> getAllPage() {
       return pageMapper.getAllPage();
    }
    
  
    @Override
    public Page getPage(int page_id) {
       return pageMapper.getPage(page_id);
    }
}
