package com.tacuso.buyer.dao;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.Page;

import java.util.List;

public interface PageMapper extends SuperMapper<Page> {
    List<Page> getAllPage();
    Page getPage(int page_id);
}
