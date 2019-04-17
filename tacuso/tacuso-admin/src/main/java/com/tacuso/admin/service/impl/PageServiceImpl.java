package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.CustomizeTagsMapper;
import com.tacuso.admin.dao.PageMapper;
import com.tacuso.admin.entity.CustomizeTags;
import com.tacuso.admin.entity.Page;
import com.tacuso.admin.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper,Page> implements PageService {
    @Autowired
    private PageMapper pageMapper;

    /**
     * 获取所有的页面
     * @param page
     * @return
     */
    public List<Page> getAllPage(Page page){

        List<Page> pageList = pageMapper.selectAllPage(page);

        return pageList;
    }

    /**
     * 根据页面ID获取页面
     * @param page_id
     * @return
     */
    @Override
    public Page getPageById(Integer page_id){

        Page page = pageMapper.getPageById(page_id);

        return page;
    }

    @Override
    public Integer insertPage(Page page) {
        Integer rownum = pageMapper.createPage(page);

        return rownum;
    }



    /**
     * 编辑页面
     * @param page_id
     * @param page
     * @return
     */
    @Override
    public Integer editPage(Integer page_id , Page page){

        Integer rownum = pageMapper.updatePageInfo(page_id,page);
        return  rownum;

    }

    /**
     * 删除页面
     * @param page_id
     * @return
     */
    @Override
    public Integer deletePage(Integer page_id ){

        Integer rownum = pageMapper.deletePage(page_id);
        return  rownum;
    }


}
