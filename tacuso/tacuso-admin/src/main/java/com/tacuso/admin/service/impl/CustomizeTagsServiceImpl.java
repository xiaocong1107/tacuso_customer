package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.CustomizeTagsMapper;
import com.tacuso.admin.entity.CustomizeTags;
import com.tacuso.admin.service.CustomizeTagsService;
import com.tacuso.admin.utils.PageUtil;
import com.tacuso.admin.vo.CustomizeTagsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomizeTagsServiceImpl extends ServiceImpl<CustomizeTagsMapper,CustomizeTags> implements CustomizeTagsService {

    @Autowired
    private CustomizeTagsMapper customizeTagsMapper;


    @Override
    public PageUtil getAllTags(CustomizeTagsVo customizeTagsVo, int pageNum, int pageSize) {

            Integer total =  customizeTagsMapper.getCustomizeTagsPageCount(customizeTagsVo);
            int offset = 0;
            if (pageNum > 1) {
                offset = ( pageNum -1 ) * pageSize;
            }
            if(total<offset){
                offset = 0;
            }
            List<CustomizeTagsVo> customizeTagsVoList = customizeTagsMapper.queryCustomizeTagsList(customizeTagsVo,offset,pageSize);

            PageUtil paginator = new PageUtil(pageSize, total, pageNum);
            paginator.setData(customizeTagsVoList);
            return paginator;
    }

    @Override
    public List<CustomizeTags> getTagsByName(String tagsName) {
        return customizeTagsMapper.getTagsByName(tagsName);
    }
}
