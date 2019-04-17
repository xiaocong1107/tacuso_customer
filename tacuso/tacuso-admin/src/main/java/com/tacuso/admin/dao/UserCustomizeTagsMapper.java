package com.tacuso.admin.dao;

import com.tacuso.admin.common.SuperMapper;
import com.tacuso.admin.entity.UserCustomizeTags;
import com.tacuso.admin.vo.UserCustomizeTagsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCustomizeTagsMapper extends SuperMapper<UserCustomizeTags>{

    Integer getUserCustomizeTagsCount(@Param("userCustomizeTagsVo") UserCustomizeTagsVo userCustomizeTagsVo);

    List<UserCustomizeTagsVo> getAllUserCustomizeTags(@Param("userCustomizeTagsVo") UserCustomizeTagsVo userCustomizeTagsVo, @Param("offset") int offset, @Param("pageSize") int pageSize);



}
