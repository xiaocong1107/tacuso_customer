package com.tacuso.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.admin.entity.UserCustomizeTags;
import com.tacuso.admin.utils.PageUtil;
import com.tacuso.admin.vo.UserCustomizeTagsVo;

public interface UserCustomizeTagsService extends IService<UserCustomizeTags> {

    public PageUtil getAllUserCustomizeTags(UserCustomizeTagsVo userCustomizeTagsVo , int pageNum , int pageSize);

    public Boolean addUserCustomizeTags(UserCustomizeTags userCustomizeTags);

    public Boolean updateUserCustomizeTags(UserCustomizeTags userCustomizeTags);

    public Boolean deleteUserCustomizeTags(UserCustomizeTags userCustomizeTags);

}
