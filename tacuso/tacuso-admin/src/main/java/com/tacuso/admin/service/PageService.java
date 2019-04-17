package com.tacuso.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.admin.entity.Page;

import java.util.List;

public interface PageService extends IService<Page> {
    public List<Page> getAllPage(Page page);
    public Page getPageById(Integer page_id);
    public Integer insertPage(Page page);
    public Integer editPage(Integer page_id , Page page);
    public Integer deletePage(Integer page_id);
}
