package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.Page;

import java.util.List;

public interface PageService extends IService<Page> {

    List<Page> getAllPage();
    
    Page getPage(int page_id);

}
