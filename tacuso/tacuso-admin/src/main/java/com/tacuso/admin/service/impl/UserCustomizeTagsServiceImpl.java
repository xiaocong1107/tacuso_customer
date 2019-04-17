package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.UserCustomizeTagsMapper;
import com.tacuso.admin.entity.UserCustomizeTags;
import com.tacuso.admin.service.UserCustomizeTagsService;
import com.tacuso.admin.utils.PageUtil;
import com.tacuso.admin.vo.UserCustomizeTagsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCustomizeTagsServiceImpl extends ServiceImpl<UserCustomizeTagsMapper,UserCustomizeTags> implements UserCustomizeTagsService {

    @Autowired
    private UserCustomizeTagsMapper userCustomizeTagsMapper;


    @Override
    public PageUtil getAllUserCustomizeTags(UserCustomizeTagsVo userCustomizeTagsVo, int pageNum, int pageSize) {

        Integer totalNum = userCustomizeTagsMapper.getUserCustomizeTagsCount(userCustomizeTagsVo);

        int offset = 0;
        if(pageNum>1){
            offset = (pageNum-1) * pageSize;
        }

        List<UserCustomizeTagsVo> userCustomizeTagsVoList = userCustomizeTagsMapper.getAllUserCustomizeTags(userCustomizeTagsVo,pageNum,pageSize);

        PageUtil pageData = new PageUtil(pageSize,totalNum,pageNum);

        pageData.setData(userCustomizeTagsVoList);

        return pageData;
    }

    @Override
    public Boolean addUserCustomizeTags(UserCustomizeTags userCustomizeTags) {
        return null;
    }

    @Override
    public Boolean updateUserCustomizeTags(UserCustomizeTags userCustomizeTags) {
        return null;
    }

    @Override
    public Boolean deleteUserCustomizeTags(UserCustomizeTags userCustomizeTags) {
        return null;
    }
}
